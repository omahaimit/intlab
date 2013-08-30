package com.zju.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.zju.dao.MessageMapper;
import com.zju.dao.PatientInfoMapper;
import com.zju.dao.PatientMapper;
import com.zju.dao.TestResultMapper;
import com.zju.model.Message;
import com.zju.model.Patient;
import com.zju.model.PatientInfo;
import com.zju.model.PushData;
import com.zju.model.TestResult;

public class DataUtil {

	private static final String sinaShortUrl = "http://api.t.sina.com.cn/short_url/shorten.json?source=2702428363&url_long=";
	private static final String smsUrl = "http://gbk.sms.webchinese.cn";
	private static final String smsPwd = "b58bee5a7f931e3b6d18";
	private static final String smsUser = "winstar";
	private static final String smsCode = "application/x-www-form-urlencoded;charset=gbk";
	private static final SimpleDateFormat smsDf = new SimpleDateFormat("MM月dd日HH:mm");
	private static Log log = LogFactory.getLog(DataUtil.class);
	private static final int MAX_BATCH = 100;	//批量插入时分割大小
	
	/**
	 *  统计记录
	 */
	public static int sampleCount = 0;
	public static int messageCount = 0;
	public static Set<String> patientSet = new HashSet<String>();
	private static int calDate = -1;
	
	/*
	 * 0:未发送过短信的患者
	 * 1:已发送过短信但未做反应
	 * 2:登陆过但未定制短信服务
	 * 3:定制了短信服务
	 * 4:安装app后，开启了消息推送功能
	 */
	
	private PatientInfoMapper patientInfoMapper;
	private TestResultMapper testResultMapper;
	private PatientMapper patientMapper;
	private MessageMapper messageMapper;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static DataUtil instance = null;

	public static DataUtil getInstance(PatientInfoMapper infoMapper,TestResultMapper resultMapper,PatientMapper patientMapper,MessageMapper messageMapper) {
		if (instance == null) {
			instance = new DataUtil(infoMapper, resultMapper, patientMapper, messageMapper);
		}
		return instance;
	}
	
	private DataUtil(PatientInfoMapper infoMapper,TestResultMapper resultMapper,PatientMapper patientMapper,MessageMapper messageMapper) {
		this.patientInfoMapper = infoMapper;
		this.testResultMapper = resultMapper;
		this.patientMapper = patientMapper;
		this.messageMapper = messageMapper;
	}
	
	private void resetCount() {
		Calendar cal = Calendar.getInstance();
		int date = cal.get(Calendar.DATE);
		if (calDate != -1 && calDate != date) {
			messageCount = 0;
			sampleCount = 0;
			patientSet.clear();
		}
		calDate = date;
	}
	
	private void infoDevideBatchInsert(List<PatientInfo> infoList) {
		
		int count = 0;
		int total = infoList.size();
		List<PatientInfo> batchList = new ArrayList<PatientInfo>();
		for (PatientInfo info : infoList) {
			count++;
			batchList.add(info);
			if (count % MAX_BATCH == 0 || count == total) {
				try {
	        		patientInfoMapper.batchInsert(batchList);
	        	} catch (Exception e) {
	        		log.error("--------------------------" + e.getMessage());
	        		patientInfoMapper.batchDelete(batchList);
	        		patientInfoMapper.batchInsert(batchList);
	        	}
				batchList = new ArrayList<PatientInfo>();
			}
			sampleCount++;
			String patientId = info.getPatientId();
			if (!patientSet.contains(patientId)) {
				patientSet.add(patientId);
			}
		}
	}
	
	private void resultDevideBatchInsert(List<TestResult> resultList) {
		
		int count = 0;
		int total = resultList.size();
		List<TestResult> batchList = new ArrayList<TestResult>();
		for (TestResult result : resultList) {
			count++;
			batchList.add(result);
			if (count % MAX_BATCH == 0 || count == total) {
				try {
					testResultMapper.batchInsert(batchList);
	        	} catch (Exception e) {
	        		log.error("--------------------------" + e.getMessage());
	        		testResultMapper.batchDelete(batchList);
	        		testResultMapper.batchInsert(batchList);
	        	}
				batchList = new ArrayList<TestResult>();
			}
		}
	}

	private void patientDevideBatchInsert(List<Patient> patientList) {
		
		int count = 0;
		int total = patientList.size();
		List<Patient> batchList = new ArrayList<Patient>();
		for (Patient patient : patientList) {
			count++;
			batchList.add(patient);
			if (count % MAX_BATCH == 0 || count == total) {
				try {
	        		patientMapper.batchInsert(batchList);
	        	} catch (Exception e) {
	        		log.error("--------------------------" + e.getMessage());
	        		patientMapper.batchDelete(batchList);
	        		patientMapper.batchInsert(batchList);
	        	}
				batchList = new ArrayList<Patient>();
			}
		}
	}

	public void startReceiveData(PushData pushData) throws Exception {
		
		resetCount();
		log.info("Receive PushData at " + sdf.format(new Date()));
        List<PatientInfo> infoList = pushData.getInfos();
        List<TestResult> resultList = pushData.getResults();
        List<Patient> patientList = pushData.getPatients();
        if (patientList != null) {
        	log.info("Patient Size : " + patientList.size());
        	patientDevideBatchInsert(patientList);
        }
		if (resultList != null) {
			log.info("Result Size : " + resultList.size());
			resultDevideBatchInsert(resultList);
		}
		if (infoList != null) {
			log.info("PatientInfo Size : " + infoList.size());
			infoDevideBatchInsert(infoList);
			Map<String, Patient> patientMap = buildPatientMap(infoList, patientList);
			log.info("Patient map Size : " + patientMap.size());
	        if (Config.smsStatus.get()) {	//根据smsFlag判定是否发送短信
				try {
					startSendSms(infoList, patientMap);
				} catch (Exception e) {
					log.error(e);
				}
			}
		}
	}

	/**
	 * 	构建一个infoList需要的patient的Map
	 */
	private Map<String, Patient> buildPatientMap(List<PatientInfo> infoList, List<Patient> patientList) {
		
		Map<String, Patient> patientMap = new HashMap<String, Patient>();
		if (patientList != null) {
			for (Patient p : patientList) {
				String patientId = p.getJZKH();
				patientMap.put(patientId, p);
			}
		}
		
		List<String> lackPatient = new ArrayList<String>();
		for (PatientInfo info : infoList) {
			if (!patientMap.containsKey(info.getPatientId())) {
				lackPatient.add(info.getPatientId());
			}
		}

		if (lackPatient.size() != 0) {	//不执行查询
			List<Patient> oldPatientList = patientMapper.getPatients(lackPatient);
			for (Patient p : oldPatientList) {
				patientMap.put(p.getJZKH(), p);
			}
		}
		return patientMap;
	}
	
	/**
	 *  短信发送过滤器
	 * @param info
	 * @param patient
	 * @return
	 */
	private boolean smsFilter(PatientInfo info, Patient patient) {
		
		if (Config.smsStatus.get() == false) {	//短信发送被中断
			return false;	
		}
		
		// 无患者信息或联系方式不对
		if (patient == null || patient.getLXDH() == null || patient.getLXDH().length() != 11) {
			return false;
		}
			
		int status = patient.getSTATUS();
		//if (status != 0 && status != 2) {	//配置向哪些状态的患者发送短信
		if (!Config.enbleStatus.contains(String.valueOf(status))) {
			return false;
		}
		
		int resultStatus = info.getResultStatus();
		if (resultStatus != 5) {	//化验单已打印的就不发短信
			return false;
		}
		
		int stayMode = info.getStayHospitalMode();
		if (stayMode != 1) {	//1为门诊
			return false;
		}
		
		int age = patient.getAge();
		if (age < Config.fromAge.get() || age > Config.toAge.get()) {
			return false;
		}
		
		int sex = Config.sex.get();
		if (sex != 0 && patient.getXB() != sex) {
			return false;
		}
		
		return true;
	}
	
	/**
	 *  发送短信，发送成功的储存记录
	 * @param infoList
	 * @param patientMap
	 * @throws Exception
	 */
	private void startSendSms(List<PatientInfo> infoList, Map<String, Patient> patientMap) throws Exception {
			
		for (PatientInfo info : infoList) {
			Patient patient = patientMap.get(info.getPatientId());
			if (smsFilter(info, patient)) {	//过滤
				try {
					int maxCount = Config.maxCount.get();
					if (maxCount != -1 && messageCount >= maxCount) {
						continue;
					}
					String shortUrl = buildShortUrl(patient);	//构建短链接
					log.info(patient.getJZKH() + ":" + shortUrl);
					int statusCode = 0; 
					long startTime = System.currentTimeMillis();
					statusCode = sendMessage(info, patient, shortUrl);	//短信发送
					if (statusCode == HttpStatus.SC_OK) {	//正常返回
						patient.setLASTSAMPLENO(info.getSampleNo());
						patient.setSTATUS(Patient.STATUS_HASSENDSMS);
						patientMapper.update(patient);
						Message msg = new Message(info.getSampleNo(), info.getPatientId(), patient.getLXDH());
						messageMapper.insert(msg);
						messageCount++;
					} else {
						log.error("异常statusCode:" + statusCode);
					}
					long endTime = System.currentTimeMillis();
					log.info("cost: " + (endTime - startTime) + "毫秒");
				} catch (Exception e) {
					log.error(e);
				}
			}
		}
	}

	private String buildShortUrl(Patient patient) throws Exception {
		
		HttpClient client = new HttpClient(new HttpClientParams(),new SimpleHttpConnectionManager(true) );
		String shortUrl = null;
		GetMethod get = new GetMethod(sinaShortUrl + Config.nativeUrl + "login?id=" + patient.getJZKH());
		if  (client.executeMethod(get) == HttpStatus.SC_OK) {
			String result = get.getResponseBodyAsString();
			JSONArray array = new JSONArray(result);
			JSONObject object = array.getJSONObject(0);
			shortUrl = object.get("url_short").toString();
			shortUrl = shortUrl.substring(7);
		}
		get.releaseConnection();
		return shortUrl;
	}

	private int sendMessage(PatientInfo info, Patient patient, String userUrl) throws Exception {
		
		HttpClient client = new HttpClient(new HttpClientParams(),new SimpleHttpConnectionManager(true));
		String time = smsDf.format(info.getReceivetime());
		String phone = patient.getLXDH();
		String exam = info.getExaminaim();
		exam = exam.split("[+]")[0].split("（")[0].trim();
		
		PostMethod post = new PostMethod(smsUrl);
		post.addRequestHeader("Content-Type", smsCode);// 在头文件中设置转码
		NameValuePair[] data = { new NameValuePair("Uid", smsUser), new NameValuePair("Key", smsPwd),
				new NameValuePair("smsMob", phone),
				new NameValuePair("smsText", "您于" + time + "做的" + exam 
						+ "的检验报告已出，您可以登录 " + userUrl + " 查阅，本条为测试短信。") };
		post.setRequestBody(data);
		client.executeMethod(post);
		int statusCode = post.getStatusCode();
		post.releaseConnection();
		return statusCode;
	}
}
