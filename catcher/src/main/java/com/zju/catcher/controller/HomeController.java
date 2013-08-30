package com.zju.catcher.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.zju.catcher.entity.local.MapPK;
import com.zju.catcher.entity.local.PK;
import com.zju.catcher.entity.local.PatientSample;
import com.zju.catcher.entity.z1.PatientAndResult;
import com.zju.catcher.entity.z1.PatientInfo;
import com.zju.catcher.entity.z1.ReferenceValue;
import com.zju.catcher.entity.z1.TestDescribe;
import com.zju.catcher.entity.z1.TestResult;
import com.zju.catcher.service.local.PatientSampleService;
import com.zju.catcher.service.local.TestDataService;
import com.zju.catcher.service.local.UserService;
import com.zju.catcher.service.z1.PatientInfoService;
import com.zju.catcher.service.z1.ReferenceValueService;
import com.zju.catcher.service.z1.TestDescribeService;
import com.zju.catcher.service.z1.TestResultService;
import com.zju.catcher.util.Config;
import com.zju.catcher.util.Constants;
import com.zju.catcher.util.FillFieldUtil;

@Controller
public class HomeController {

	private static AtomicBoolean currentFlag = new AtomicBoolean(false);
	private static AtomicBoolean writeBackFlag = new AtomicBoolean(false);
	private static AtomicBoolean measureFlag = new AtomicBoolean(false);
	private static AtomicLong interval = new AtomicLong(Config.getSyncDataInterval());
	private static AtomicLong writeBackInterval = new AtomicLong(Config.getWriteBackInterval());
	private static AtomicLong measureInterval = new AtomicLong(Config.getMeasureInterval());
	private static AtomicInteger index = new AtomicInteger(0);
	private static AtomicInteger threadIndex = new AtomicInteger(0);
	private static AtomicInteger writeThreadIndex = new AtomicInteger(0);
	private static AtomicInteger measureThreadIndex = new AtomicInteger(0);
	private static Date syncDate = new Date();
	private static SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat ldf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static Log log = LogFactory.getLog(HomeController.class);
	private static HomeController homeController = null;
	private static final int STATUS_NO_RESULT = -1;
	private static final int STATUS_UNAUDIT = 0;
	private static final int STATUS_WRITEBACK = 5;
	private static FillFieldUtil fillUtil = null;
	private static Set<String> currWriteback = new HashSet<String>();
	private static boolean isWritebackWhenSync = false;	//ͬ���������б༭����д�أ����ֲ�����testresult����
	private static boolean isDeleteWhenSync = false;	//ͬ����������ɾ������д�أ����ֲ�����testresult����
	private static boolean remoteSyncOnceFlag = false;

	public static HomeController getInstance() {
		if (homeController == null)
			throw new NullPointerException();
		return homeController;
	}

	public HomeController() {
		log.info("Controller builded!");
		homeController = this;
	}

	@RequestMapping(value = "/home.htm", method = RequestMethod.GET)
	public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String hexReceive = request.getParameter("hex");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		java.security.MessageDigest md5 = java.security.MessageDigest.getInstance("MD5");
		md5.update(df.format(new Date()).getBytes());
		String hex = byte2hex(md5.digest());
		
		if (hexReceive != null) {
			if (!hex.equals(hexReceive)) {
				md5.update(df.format(new Date(new Date().getTime() - 60000)).getBytes());
				String hex2 = byte2hex(md5.digest());
				if (!hex2.equals(hexReceive)) {
					log.error(hexReceive);
					log.error(hex);
					log.error(hex2);
					throw new Exception("������֤����");
				}
			}
		} else {
			throw new Exception("���Ϸ��ķ��ʣ�");
		}
		
		if (currentFlag.get()) {
			request.setAttribute("syncStart", "�ر�");
		} else {
			request.setAttribute("syncStart", "����");
		}
		
		if (measureFlag.get()) {
			request.setAttribute("mSyncStart", "�ر�");
		} else {
			request.setAttribute("mSyncStart", "����");
		}
		
		if (writeBackFlag.get()) {
			request.setAttribute("writeStart", "�ر�");
		} else {
			request.setAttribute("writeStart", "����");
		}

		request.setAttribute("date", sdf.format(syncDate));
		request.setAttribute("interval", interval.get() / 1000);
		request.setAttribute("writeInterval", writeBackInterval.get() / 1000);
		request.setAttribute("currentFlag", currentFlag.get());
		request.setAttribute("writeBackFlag", writeBackFlag.get());
		request.setAttribute("measureInterval", measureInterval.get() / 1000);
		request.setAttribute("measureFlag", measureFlag.get());

		return new ModelAndView("home");
	}
	
	private void SyncEditInfo(Date date) throws Exception {
		
		List<String> editSampleNoList = testResultService.getEditSampleNoFrom(date);
		System.out.println(ldf.format(date) + ":" + editSampleNoList.size());
		if (editSampleNoList.size() != 0) {
			patientSampleService.updateEditPatientInfo(editSampleNoList);
		}
	}

	private void SyncData(Date date) throws Exception {

		isWritebackWhenSync = false;
		isDeleteWhenSync = false;
		
		Set<Long> exsitSample = new HashSet<Long>();
		Set<Long> exsitSampleNoResult = new HashSet<Long>();
		Set<PK> exsitResult = new HashSet<PK>();
		Set<MapPK> exsitMap = new HashSet<MapPK>();

		if (fillUtil == null) {
			List<TestDescribe> desList = testDescribeService.getAll();
			List<ReferenceValue> refList = referenceValueService.getAll();
			fillUtil = FillFieldUtil.getInstance(desList, refList);
		}

		log.info("<<< ��ȡ" + sdf.format(date) + "������ >>>");
		long start = System.currentTimeMillis();
		List<PatientAndResult> list = patientInfoService.getAll(date);
		log.info("Count : " + list.size());
		log.info(System.currentTimeMillis() - start + " ����");

		log.info("��ȡPatient MAP");
		start = System.currentTimeMillis();
		List<Long> doctList = patientSampleService.getPatientId(date);
		List<Long> doctListNoResult = patientSampleService.getPatientId(date, STATUS_NO_RESULT);
		log.info(System.currentTimeMillis() - start + " ����");

		log.info("��ȡTestResult Map");
		start = System.currentTimeMillis();
		List<PK> pkList = testDataService.getTestResultPK(date);
		log.info(System.currentTimeMillis() - start + " ����");

		log.info("��ȡӳ���");
		start = System.currentTimeMillis();
		List<MapPK> mapPkList = testDataService.getMapPk(date);
		log.info(System.currentTimeMillis() - start + " ����");

		log.info("����map");
		start = System.currentTimeMillis();
		exsitSample.addAll(doctList);
		exsitSampleNoResult.addAll(doctListNoResult);
		exsitResult.addAll(pkList);
		exsitMap.addAll(mapPkList);
		log.info(System.currentTimeMillis() - start + " ����");

		log.info("���ݹ��˷���");
		start = System.currentTimeMillis();
		List<PatientSample> patientInsertList = new ArrayList<PatientSample>();
		List<PatientSample> patientUpdateListWithStatus = new ArrayList<PatientSample>();
		List<PatientInfo> patientUpdateList = new ArrayList<PatientInfo>();
		List<TestResult> testResultInsertList = new ArrayList<TestResult>();
		List<TestResult> testResultUpdateList = new ArrayList<TestResult>();
		List<MapPK> mapList = new ArrayList<MapPK>();
		Set<Long> newAddSample = new HashSet<Long>();
		
		for (PatientAndResult obj : list) {
			TestResult tr = obj.getTestResult();
			PatientInfo pi = obj.getPatientInfo();
			if (pi.getDOCTADVISENO() == 0 || "0".equals(pi.getSAMPLENO()))
				continue;

			long doctId = pi.getDOCTADVISENO();
			if (!newAddSample.contains(doctId)) {
				if (!exsitSample.contains(doctId)) {
					int status = STATUS_UNAUDIT;
					if (tr.getTESTID() == null) {
						status = STATUS_NO_RESULT;
					}
					PatientSample sample = new PatientSample();
					sample.setAuditStatus(status);
					sample.setPatientInfo(pi);
					patientInsertList.add(sample);
				} else {
					patientUpdateList.add(pi);
				}
				newAddSample.add(doctId);
			}

			if (tr.getTESTID() == null) {
				continue;
			} else if (exsitSampleNoResult.contains(doctId)) {
				PatientSample sample = new PatientSample();
				sample.setPatientInfo(pi);
				sample.setAuditStatus(STATUS_UNAUDIT);
				patientUpdateListWithStatus.add(sample);
				exsitSampleNoResult.remove(doctId);
			}

			PK pk = new PK(tr.getSAMPLENO(), tr.getTESTID());
			if (exsitResult.contains(pk)) {				
				testResultUpdateList.add(tr);
			} else {
				try {
					fillUtil.fillResult(tr, pi);
				} catch (Exception e) {
					log.error(e.getMessage() + e.getStackTrace(), e);
				}
				testResultInsertList.add(tr);
				exsitResult.add(pk);
			}

			MapPK mpk = new MapPK(pi.getDOCTADVISENO(), tr.getSAMPLENO(), tr.getTESTID());
			if (!exsitMap.contains(mpk)) {
				mapList.add(mpk);
				exsitMap.add(mpk);
			}
		}

		log.info(System.currentTimeMillis() - start + " ����");

		log.info("<<< ���ݴ�Сͳ�� >>>");
		log.info("patient����     : " + patientInsertList.size());
		log.info("patient����status     : " + patientUpdateListWithStatus.size());
		log.info("patient����     : " + patientUpdateList.size());
		log.info("result����      : " + testResultInsertList.size());
		log.info("result����      : " + testResultUpdateList.size());
		log.info("����ӳ��        : " + mapList.size());

		log.info("��ʼͬ��");
		start = System.currentTimeMillis();
		try {
			patientSampleService.insert(patientInsertList);
		} catch (DuplicateKeyException e) {
			// ������ͻ��ִ��һ�θ��£�����ͻ���������¡�����һ��ִ��ʱ����ȡ���Ѵ��ڵ�������ִ�и���
			List<PatientInfo> updateInsertList = new ArrayList<PatientInfo>();
			List<PatientSample> reverseInsertList = new ArrayList<PatientSample>();
			int pSize = patientInsertList.size();
			for (int i = 0; i < pSize; i++) {
				reverseInsertList.add(patientInsertList.get(pSize - i - 1));
				updateInsertList.add(patientInsertList.get(i).getPatientInfo());
			}
			try {
				patientSampleService.update(updateInsertList);
				patientSampleService.insert(reverseInsertList);
			} catch (Exception ex) {
			}
		}
		patientSampleService.updateWithStatus(patientUpdateListWithStatus);
		patientSampleService.update(patientUpdateList);
		if (!isDeleteWhenSync) {
			testDataService.insert(testResultInsertList);
		}
		if (!isWritebackWhenSync) {
			testDataService.update(testResultUpdateList);
		}
		
		testDataService.insertMapping(mapList);

		log.info(System.currentTimeMillis() - start + " ����");

	}
	
	private void SyncDataNot(Date date) throws Exception {

		Set<PK> exsitResult = new HashSet<PK>();
		Set<String> exsitInfo = new HashSet<String>();
		
		List<PatientAndResult> list = patientInfoService.getNotToday(date);
		log.info("Count : " + list.size());
		
		List<PK> pkList = testDataService.getTestResultPKNot(date);
		log.info("*****PK:" + pkList.size());
		exsitResult.addAll(pkList);

		List<PatientInfo> patientInfoList = new ArrayList<PatientInfo>();
		List<PatientSample> patientInfoInsertList = new ArrayList<PatientSample>();
		List<PatientInfo> patientInfoUpdateList = new ArrayList<PatientInfo>();
		List<TestResult> testResultInsertList = new ArrayList<TestResult>();
		List<TestResult> testResultUpdateList = new ArrayList<TestResult>();
		Set<Long> exsitSample = new HashSet<Long>();
		
		if (fillUtil == null) {
			List<TestDescribe> desList = testDescribeService.getAll();
			List<ReferenceValue> refList = referenceValueService.getAll();
			fillUtil = FillFieldUtil.getInstance(desList, refList);
		}
		
		for (PatientAndResult obj : list) {
			TestResult tr = obj.getTestResult();
			PatientInfo pi = obj.getPatientInfo();
			if (pi.getDOCTADVISENO() == 0 || "0".equals(pi.getSAMPLENO()) || tr.getTESTID() == null)
				continue;

			long doctId = pi.getDOCTADVISENO();
			if (!exsitSample.contains(doctId)) {
				PatientSample sample = new PatientSample();
				sample.setAuditStatus(STATUS_UNAUDIT);
				sample.setPatientInfo(pi);
				patientInfoList.add(pi);
				exsitSample.add(doctId);
			}
			
			PK pk = new PK(tr.getSAMPLENO(), tr.getTESTID());
			try {
				fillUtil.fillResult(tr, pi);
			} catch (Exception e) {
				log.error(e);
			}
			if (exsitResult.contains(pk)) {
				testResultUpdateList.add(tr);
			} else {
				testResultInsertList.add(tr);
			}
		}
		log.info("****Update:" + testResultUpdateList.size());
		log.info("****Insert:" + testResultInsertList.size());
		
		List<String> sampleNoList = patientSampleService.exsitSampleList(patientInfoList);
		exsitInfo.addAll(sampleNoList);
		for (PatientInfo info : patientInfoList) {
			if (exsitInfo.contains(info.getSAMPLENO())) {
				patientInfoUpdateList.add(info);
			} else {
				PatientSample sample = new PatientSample();
				sample.setAuditStatus(STATUS_UNAUDIT);
				sample.setPatientInfo(info);
				patientInfoInsertList.add(sample);
			}
		}
		try {
			patientSampleService.insert(patientInfoInsertList);
		} catch (Exception e) {
			log.error("MeasureTime PatientInfo����������ͻ:" +e.getMessage());
		}
		patientSampleService.update(patientInfoUpdateList);
		try {
			testDataService.delete(testResultInsertList);
			testDataService.insert(testResultInsertList);
		} catch (Exception e) {
			log.error("MeasureTime TestResult����������ͻ:" +e.getMessage());
		}
		testDataService.update(testResultUpdateList);
	}

	private void writeBack(final Date date) throws Exception {

		Thread thread = new Thread(new Runnable() {
			public void run() {

				int w_index = writeThreadIndex.incrementAndGet();
				writeBackFlag.set(true);
				
				while (writeBackFlag.get() && w_index == writeThreadIndex.get()) {
					try {
						String sample = df.format(date);
						writeBackOnce(sample+"CBC", "1300101", null);
						
					} catch (Exception e) {
						log.error(this, e);
					}
					
					try {
						Thread.sleep(writeBackInterval.get());
					} catch (InterruptedException e) {
						log.error(this, e);
					}
				}
			}
		});

		thread.start();
	}
	
	private void writeBackOnce(final String sample, String lib, String operator) {
		
		log.info("��ʼ��ȡ��ͨ��������Id");
		List<PatientSample> needWriteBackSample = patientSampleService.getPatientInfo(sample, lib, STATUS_WRITEBACK, operator);
		log.info("��ȡ�������," + needWriteBackSample.size() + "��");
		Map<String, PatientInfo> sampeMap = new HashMap<String, PatientInfo>();
		for (PatientSample info : needWriteBackSample) {
			sampeMap.put(info.getPatientInfo().getSAMPLENO(), info.getPatientInfo());
			System.out.println(info.getPatientInfo().getSAMPLENO());
		}
		
		log.info("��ʼ��ȡ�����testResult");
		List<TestResult> resultList = testDataService.getResultWithEdit(sample, lib, operator);
		List<TestResult> needSyncList = new ArrayList<TestResult>();
		List<TestResult> updateList = new ArrayList<TestResult>();
		List<TestResult> addList = new ArrayList<TestResult>();
		List<TestResult> editList = new ArrayList<TestResult>();
		List<TestResult> deleteList = new ArrayList<TestResult>();
		log.info("��д��testResult����  " + resultList.size());
		
		log.info("testResult����");
		int mark = 0;
		for (TestResult tr : resultList) {
			try {
				if (sampeMap.containsKey(tr.getSAMPLENO())) {
					fillUtil.fillResult(tr, sampeMap.get(tr.getSAMPLENO()));
				}
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			updateList.add(tr);
			mark = tr.getEDITMARK();
			if (mark == 0) continue;
			needSyncList.add(tr);
			if (mark == Constants.FILL_FLAG)
				continue;
			
			if (mark % Constants.ADD_FLAG == 0) {
				addList.add(tr);
				deleteList.add(tr);	//��������Ŀ������ɾ������ֹ������ͻ
			} else if (mark % Constants.DELETE_FLAG == 0) {
				deleteList.add(tr);
			} else if (mark % Constants.EDIT_FLAG == 0){
				editList.add(tr);
			}
		}
			
		log.info("Edit:	" + editList.size());
		log.info("Delete:	" + deleteList.size());
		log.info("Add:	" + addList.size());
		log.info("Update:	" + updateList.size());

		if (editList.size() != 0 ) {
			testResultService.editTestResult(editList);
			isWritebackWhenSync = true;
		}
		if (deleteList.size() != 0) {
			testResultService.deleteTestResult(deleteList);
			isDeleteWhenSync = true;
		}
		testResultService.addTestResult(addList);
		testResultService.updateField(updateList);
		testDataService.updateEditMark(needSyncList);
		log.info("д��testresult�������");
		
		try {
			patientInfoService.UpdateSampleHasPass(needWriteBackSample);
		} catch (Exception e) {
			log.error("״̬д�س���", e);
		}
		log.info("PatientInfo���״̬д�����");
	}
	
	private void writeBackPart(final String sample, String lib, String operator, int from, int to) {
		
		log.info("��ʼ��ȡ��ͨ��������Id");
		List<PatientSample> needWriteBackSample = new ArrayList<PatientSample>();
		List<PatientSample> sampleList = patientSampleService.getPatientInfo(sample, lib, STATUS_WRITEBACK, operator);
		for (PatientSample p : sampleList) {
			int sampleno = Integer.parseInt(p.getPatientInfo().getSAMPLENO().substring(11,14));
			if (sampleno>=from && sampleno<=to) {
				needWriteBackSample.add(p);
			}
		}
		
		log.info("��ȡ�������," + needWriteBackSample.size() + "��");
		Map<String, PatientInfo> sampeMap = new HashMap<String, PatientInfo>();
		for (PatientSample info : needWriteBackSample) {
			sampeMap.put(info.getPatientInfo().getSAMPLENO(), info.getPatientInfo());
			System.out.println(info.getPatientInfo().getSAMPLENO());
		}
		
		log.info("��ʼ��ȡ�����testResult");
		List<TestResult> resultList = testDataService.getResultWithEdit(sample, lib, operator);
		List<TestResult> needSyncList = new ArrayList<TestResult>();
		List<TestResult> updateList = new ArrayList<TestResult>();
		List<TestResult> addList = new ArrayList<TestResult>();
		List<TestResult> editList = new ArrayList<TestResult>();
		List<TestResult> deleteList = new ArrayList<TestResult>();
		log.info("��д��testResult����  " + resultList.size());
		
		log.info("testResult����");
		int mark = 0;
		for (TestResult tr : resultList) {
			try {
				if (sampeMap.containsKey(tr.getSAMPLENO())) {
					fillUtil.fillResult(tr, sampeMap.get(tr.getSAMPLENO()));
				}
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			updateList.add(tr);
			mark = tr.getEDITMARK();
			if (mark == 0) continue;
			needSyncList.add(tr);
			if (mark == Constants.FILL_FLAG)
				continue;
			
			if (mark % Constants.ADD_FLAG == 0) {
				addList.add(tr);
				deleteList.add(tr);	//��������Ŀ������ɾ������ֹ������ͻ
			} else if (mark % Constants.DELETE_FLAG == 0) {
				deleteList.add(tr);
			} else if (mark % Constants.EDIT_FLAG == 0){
				editList.add(tr);
			}
		}
			
		log.info("Edit:	" + editList.size());
		log.info("Delete:	" + deleteList.size());
		log.info("Add:	" + addList.size());
		log.info("Update:	" + updateList.size());

		if (editList.size() != 0 ) {
			testResultService.editTestResult(editList);
			isWritebackWhenSync = true;
		}
		if (deleteList.size() != 0) {
			testResultService.deleteTestResult(deleteList);
			isDeleteWhenSync = true;
		}
		testResultService.addTestResult(addList);
		testResultService.updateField(updateList);
		testDataService.updateEditMark(needSyncList);
		log.info("д��testresult�������");
		
		try {
			patientInfoService.UpdateSampleHasPass(needWriteBackSample);
		} catch (Exception e) {
			log.error("״̬д�س���", e);
		}
		log.info("PatientInfo���״̬д�����");
	}
	
	@ResponseBody
	@RequestMapping(value = "/ajax/writeBack.htm", method = RequestMethod.POST)
	public void writeBack(HttpServletRequest request, HttpServletResponse response) throws Exception {

		boolean result = false;
		if (result == false) return;
		String op = request.getParameter("op");
		log.info(op);
		if ("start".equals(op) && !writeBackFlag.get()) {
			String strDate = request.getParameter("date");
			String strInterval = request.getParameter("interval");
			try {
				if (strInterval != null && !"".equals(strInterval)) {
					writeBackInterval.set(Long.parseLong(strInterval) * 1000);
				}

				if (strDate != null && !"".equals(strDate)) {
					log.info(strDate);
					writeBack(sdf.parse(strDate));
				}
				result = true;
			} catch (Exception e) {
				log.error(this, e);
			}

		} else if ("stop".equals(op) && writeBackFlag.get()) {
			writeBackFlag.set(false);
			result = true;
		}

		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(result);
	}
	
	@ResponseBody
	@RequestMapping(value = "/ajax/writeBack/once.htm", method = RequestMethod.GET)
	public void writeBackOnceWithCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String code = request.getParameter("code");
		String lib = request.getParameter("lib");
		String operator = request.getParameter("operator");
		String date = df.format(new Date());
		String callback = request.getParameter("callback");
		
		int result = 0;
		String strKey = lib + code;
		try {
			if (currWriteback.contains(strKey)) {
				result = 0;
			} else {
				currWriteback.add(strKey);
				String[] codes = code.split(",");
				for (String cd : codes) {
					if (cd.length() == 3) {
						writeBackOnce(date + cd, lib, operator);
					}
				}
				result = 1;
			}
		} catch (Exception e) {
			log.error("д���쳣", e);
			result = 2;
		} finally {
			currWriteback.remove(strKey);
		}
		
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(callback + "({'result':" + result + "})");
	}
	
	@ResponseBody
	@RequestMapping(value = "/ajax/writeBack/part.htm", method = RequestMethod.GET)
	public void writeBackPartWithCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String code = request.getParameter("code");
		String lib = request.getParameter("lib");
		String operator = request.getParameter("operator");
		String date = df.format(new Date());
		String callback = request.getParameter("callback");
		String from = request.getParameter("from");
		String to = request.getParameter("to");
		
		int result = 0;
		String strKey = lib + code;
		try {
			if (currWriteback.contains(strKey)) {
				result = 0;
			} else {
				currWriteback.add(strKey);
				if(code.length() == 3)
					writeBackPart(date + code, lib, operator, Integer.parseInt(from), Integer.parseInt(to));
				result = 1;
			}
		} catch (Exception e) {
			log.error("д���쳣", e);
			result = 2;
		} finally {
			currWriteback.remove(strKey);
		}
		
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(callback + "({'result':" + result + "})");
	}

	@ResponseBody
	@RequestMapping(value = "/ajax/operate.htm", method = RequestMethod.POST)
	public void operate(HttpServletRequest request, HttpServletResponse response) throws Exception {

		boolean result = false;
		String op = request.getParameter("op");
		log.info(op);
		if ("start".equals(op) && !currentFlag.get()) {
			String strDate = request.getParameter("date");
			String strInterval = request.getParameter("interval");
			try {
				if (strDate != null && !"".equals(strDate)) {
					syncDate = sdf.parse(strDate);
				}
				if (strInterval != null && !"".equals(strInterval)) {
					interval.set(Long.parseLong(strInterval) * 1000);
				}
				start();
				result = true;
			} catch (Exception e) {
				log.error(this, e);
			}

		} else if ("stop".equals(op) && currentFlag.get()) {
			currentFlag.set(false);
			result = true;
		}

		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(result);
	}
	
	@ResponseBody
	@RequestMapping(value = "/ajax/measure.htm", method = RequestMethod.POST)
	public void operateMeasure(HttpServletRequest request, HttpServletResponse response) throws Exception {

		boolean result = false;
		String op = request.getParameter("op");
		log.info(op);
		if ("start".equals(op) && !measureFlag.get()) {
			String strInterval = request.getParameter("interval");
			try {
				if (strInterval != null && !"".equals(strInterval)) {
					measureInterval.set(Long.parseLong(strInterval) * 1000);
				}
				startNotToday();
				result = true;
			} catch (Exception e) {
				log.error(this, e);
			}

		} else if ("stop".equals(op) && measureFlag.get()) {
			measureFlag.set(false);
			result = true;
		}

		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(result);
	}

	@ResponseBody
	@RequestMapping(value = "/ajax/syncOnce.htm", method = RequestMethod.POST)
	public void syncOnce(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String startDate = request.getParameter("start");
		String endDate = request.getParameter("end");

		log.info(startDate + "," + endDate);
		if (null != startDate && !"".equals(startDate)) {
			if (null == endDate || "".equals(endDate)) {
				startOnce(sdf.parse(startDate), null);
			} else {
				Date start = sdf.parse(startDate);
				Date end = sdf.parse(endDate);
				startOnce(start, end);
			}
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/ajax/remoteSyncOnce.htm", method = RequestMethod.GET)
	public void RemoteSyncOnce(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String startDate = request.getParameter("start");
		String endDate = request.getParameter("end");
		String callback = request.getParameter("callback");
		int result = 0;
		
		log.info(startDate + "," + endDate);
		if (null != startDate && !"".equals(startDate) && !remoteSyncOnceFlag) {
			if (null == endDate || "".equals(endDate)) {
				startOnce(sdf.parse(startDate), null);
			} else {
				Date start = sdf.parse(startDate);
				Date end = sdf.parse(endDate);
				startOnce(start, end);
				result = 1;
			}
		}
		
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(callback + "({'result':" + result + "})");
	}

	private void start() {

		Thread thread = new Thread(new Runnable() {

			public void run() {

				int t_index = threadIndex.incrementAndGet();
				currentFlag.set(true);
				index.set(0);

				while (currentFlag.get() && t_index == threadIndex.get()) {

					long time = 0;
					try {
						
						long start = System.currentTimeMillis();
						Date startDate = syncDate;
						syncDate = new Date();
						log.info("��" + index.incrementAndGet() + "�ֵ�����ͬ�� >>>>>> ��ʼ��" + ldf.format(new Date()));
						SyncData(startDate);
						time = System.currentTimeMillis() - start;
						log.info("��" + index.get() + "������ͬ�����,��ʱ" + time / 1000 + "��\n");
						SyncEditInfo(startDate);
						
					} catch (Exception e) {
						log.error(this, e);
					}

					try {
						if (interval.get() > time)
							Thread.sleep(interval.get() - time);
						else
							Thread.sleep(60000);
					} catch (InterruptedException e) {
						log.error(this, e);
					}
				}
			}
		});

		thread.start();
	}
	
	private void startNotToday() {

		Thread thread = new Thread(new Runnable() {

			public void run() {

				int t_index = measureThreadIndex.incrementAndGet();
				measureFlag.set(true);

				while (measureFlag.get() && t_index == measureThreadIndex.get()) {

					long time = 0;
					try {
						long start = System.currentTimeMillis();
						SyncDataNot(new Date());
						time = System.currentTimeMillis() - start;
						log.info("MeasureTime����ͬ�����,��ʱ" + time / 1000 + "��\n");
					} catch (Exception e) {
						log.error(this, e);
					}

					try {
						Thread.sleep(measureInterval.get());
					} catch (InterruptedException e) {
						log.error(this, e);
					}
				}
			}
		});

		thread.start();
	}

	private void startOnce(final Date startDate, final Date endDate) {

		Thread thread = new Thread(new Runnable() {

			public void run() {
				remoteSyncOnceFlag = true;
				Date date = new Date(startDate.getTime());
				while (date.getTime() <= endDate.getTime()) {
					try {
						long start = System.currentTimeMillis();
						log.info("����" + sdf.format(date) + "����ͬ�� >>>>>> ��ʼ��" + ldf.format(new Date()));
						SyncData(date);
						long time = System.currentTimeMillis() - start;
						log.info("����" + sdf.format(date) + "����ͬ�����,��ʱ" + time / 1000 + "��\n");
						date.setTime(date.getTime() + 24 * 3600 * 1000);
					} catch (Exception e) {
						log.error(this, e);
					}
				}
				remoteSyncOnceFlag = false;
			}
		});

		thread.start();
	}

	public void StartUpaExecute() {
		log.info("Running");
		if (Config.getSyncSelfStart()) {
			//start();
		}
	}
	
	private static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}
	
	@Autowired
	private UserService userService = null;

	@Autowired
	private PatientInfoService patientInfoService = null;

	@Autowired
	private TestResultService testResultService = null;

	@Autowired
	private PatientSampleService patientSampleService = null;

	@Autowired
	private TestDataService testDataService = null;
	
	@Autowired
	private TestDescribeService testDescribeService = null;
	
	@Autowired
	private ReferenceValueService referenceValueService = null;
}
