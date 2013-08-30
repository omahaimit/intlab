package com.zju.webapp.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.zju.webapp.dao.PatientDao;
import com.zju.webapp.dao.PatientInfoDao;
import com.zju.webapp.dao.TestResultDao;
import com.zju.webapp.model.Patient;
import com.zju.webapp.model.PatientInfo;
import com.zju.webapp.model.PushData;
import com.zju.webapp.model.TestDescribe;
import com.zju.webapp.model.TestResult;

public class GetDataManager {

	private final Log log = LogFactory.getLog(GetDataManager.class);
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
	private final int MEASURE_COUNT = 12;	//依据measureTime查找较为花时，故设为间隔查找，此为间隔次数
	private int currentCount = 0;

	private PatientInfoDao patientInfoDao;
	private TestResultDao testResultDao;
	private PatientDao patientDao;
	private Map<String, String> testDescribeMap = null;
	
	private static GetDataManager instance = null;
	
	private GetDataManager() {}
	
	public static GetDataManager getInstance(PatientInfoDao patientInfoDao, TestResultDao testResultDao, PatientDao patientDao) {
		if (instance == null) {
			synchronized (GetDataManager.class) {
				instance = new GetDataManager();
				instance.patientInfoDao = patientInfoDao;
				instance.testResultDao = testResultDao;
				instance.patientDao = patientDao;
				instance.testDescribeMap = new HashMap<String, String>();
				List<TestDescribe> describes = testResultDao.getAllDescribe();
				for (TestDescribe des : describes) {
					instance.testDescribeMap.put(des.getTestId(), des.getName());
				}
			}
		}
		return instance;
	}

	private List<PatientInfo> getNewPatientInfo() {
		
		List<PatientInfo> infoList = patientInfoDao.getPatientInfoFromZ1(new Date());
		log.info("PatientInfo Count : " + infoList.size());

		if (currentCount % MEASURE_COUNT == 0) {
			List<PatientInfo> mInfoList = patientInfoDao.getPatientInfoByMeasureTime(new Date());
			log.info("PatientInfo MeasureTime Count : " + mInfoList.size());
			infoList.addAll(mInfoList);
		}
		return infoList;
	}

	private List<TestResult> getNewTestResult() {
		
		List<TestResult> newResultList = testResultDao.getTestResultFromZ1(new Date());
		List<TestResult> resultList = new ArrayList<TestResult>();
		List<TestResult> removeResultList = new ArrayList<TestResult>();
		
		log.info("TestResult Count : " + newResultList.size());
		if (currentCount % MEASURE_COUNT == 0) {
			List<TestResult> mResultList = testResultDao.getTestResultByMeasureTime(new Date());
			log.info("TestResult MeasureTime Count : " + mResultList.size());
			resultList.addAll(mResultList);
		}
		for (TestResult tr : newResultList) {
			String testResult = tr.getTestResult();
	    	if (testResult != null && testResult.contains(" ")) {
	    		tr.setTestResult(testResult.replaceAll(" ", ""));
	    	}
	    	if (testDescribeMap.containsKey(tr.getTestId())) {
	    		resultList.add(tr);
	    		String res = tr.getTestResult();
	    		if (!normalChar(res)) {
	    			res = removeUnNormalChar(res);
	    			log.error(res);
	    			tr.setTestResult(res);
	    			log.info("Filter-> " + tr.getSampleNo() + ":" + tr.getTestId());
	    		}
	    	} else {
	    		removeResultList.add(tr);
	    		//log.info("Filter-> " + tr.getSampleNo() + ":" + tr.getTestId());
	    	}
		}
		testResultDao.updateClodMark(removeResultList);
		log.info("过滤" + removeResultList.size() + "条");
		return resultList;
	}
	
	/**
	 *  检测res中是否包含了小于32的非法字符
	 * @param res
	 * @return
	 */
	private boolean normalChar(String res) {
		
		if (res != null) {
			char[] chars = res.toCharArray();
			for (char c : chars) {
				if ((int)c < 32) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 *  去除res中是否包含了小于32的非法字符
	 * @param res
	 * @return
	 */
	private String removeUnNormalChar(String res) {
		
		char[] chars = res.toCharArray();
		char[] result = new char[chars.length];
		int i = 0;
		for (char c : chars) {
			if ((int)c > 31) {
				result[i] = c;
				i++;
			}
		}
		return String.valueOf(result).trim();
	}

	public PushData getPushData() {

		currentCount++;
		log.info("循环开始，第" + currentCount + "次，" + sdf.format(new Date()));
		PushData pushData = new PushData();
		List<PatientInfo> infos = getNewPatientInfo();	//获取新通过的样本
		
		List<Patient> newPatients = getNewPatients(infos);	//获取新进患者数据
		log.info("新的患者数据 " + newPatients.size() + " 条");
		if (newPatients.size() != 0) {
			//patientDao.batchInsert(newPatients);
		}
		pushData.setPatients(newPatients);
		pushData.setInfos(infos);
		pushData.setResults(getNewTestResult());
		log.info("循环结束，第" + currentCount + "次，" + sdf.format(new Date()));
		return pushData;
	}
	
	private List<Patient> getNewPatients(List<PatientInfo> infos) {
		
		List<String> inSqlList = getInSQL(infos, null);
		List<String> exsitPatients = new ArrayList<String>();
		List<Patient> newPatients = new ArrayList<Patient>();
		log.info("&&&&&&&&& " + inSqlList.size());
		for (String inSql : inSqlList) {
			log.info(inSql);
			exsitPatients.addAll(patientDao.exsitPatients(inSql));
		}
		
		inSqlList = getInSQL(infos, exsitPatients);
		log.info("&&&&&&&&& " + inSqlList.size());
		for (String inSql : inSqlList) {
			log.info(inSql);
			newPatients.addAll(patientDao.getPatients(inSql));
		}
		
		return newPatients;
	}
	
	public void updateMark(int flag, PushData pushData) {
		if (flag == 1) {
			List<PatientInfo> infoList = pushData.getInfos();
			List<TestResult> resultList = pushData.getResults();
			patientInfoDao.updateClodMark(infoList);
			testResultDao.updateClodMark(resultList);
			log.info("Update Mark, info size : " + infoList.size() + ", result size : " + resultList.size());
		}
	}

	/**
	 *  构建in的sql语句
	 * @param infos
	 * @param exsitPatient
	 * @return
	 */
	private List<String> getInSQL(List<PatientInfo> infos, List<String> exsitPatient) {
		
		Set<String> patientIdSet = new HashSet<String>();
		if (exsitPatient != null && exsitPatient.size() > 0) {
			patientIdSet.addAll(exsitPatient);
		}
		
		List<String> resultList = new ArrayList<String>();
		int count = 0;
		int infoSize = infos.size();
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < infoSize; i++) {
			
			PatientInfo info = infos.get(i);
			String id = info.getPatientId();
			if (patientIdSet.contains(id)) {
				if(i != infoSize - 1) {
					continue;
				}
			} else {
				patientIdSet.add(id);
				builder.append("'");
				builder.append(id);
				builder.append("'");
				builder.append(',');
			}
			count++;
			if (count % 1000 == 0 || i == infoSize - 1) {
				String idList = builder.toString();
				resultList.add('(' + idList.substring(0, idList.length() - 1) + ')');
				builder = new StringBuilder();
			}
		}

		return resultList;
	}
}
