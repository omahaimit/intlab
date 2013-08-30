package com.zju.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zju.dao.PatientInfoMapper;
import com.zju.dao.PatientMapper;
import com.zju.dao.RecordMapper;
import com.zju.dao.RequestMapper;
import com.zju.dao.TestDescribeMapper;
import com.zju.dao.TestResultMapper;
import com.zju.model.Patient;
import com.zju.model.PatientInfo;
import com.zju.model.Record;
import com.zju.model.Request;
import com.zju.model.TestDescribe;
import com.zju.model.TestResult;
import com.zju.util.RequestUtil;

@Controller
public class PatientController {

	@Autowired
	private PatientInfoMapper patientInfoMapper;
	
	@Autowired
	private TestResultMapper testResultMapper;
	
	@Autowired
	private PatientMapper patientMapper;
	
	@Autowired
	private TestDescribeMapper testDescribeMapper;
	
	@Autowired
	private RequestMapper requestMapper;
	
	@Autowired
	private RecordMapper recordMapper;
	
	private Map<String, String> testNameMap = null;
	
	private RequestUtil requestUtil = null;
	
	private SimpleDateFormat shortdf = new SimpleDateFormat("yyyy年MM月dd日");
	//private SimpleDateFormat longdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	private void initNameMap() {
		if (testNameMap == null) {
			synchronized (this) {
				if (testNameMap == null) {
					testNameMap = new HashMap<String, String>();
					List<TestDescribe> describes = testDescribeMapper.getAllDescribe();
					for (TestDescribe des : describes) {
						testNameMap.put(des.getTestId(), des.getName());
					}
				}
			}
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/skip")
	public ModelAndView skipView(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String jzkh = request.getRemoteUser();
		if (jzkh.equals("ejianyan")) {
			return new ModelAndView("redirect:/admin/message");
		} else if (!StringUtils.isEmpty(jzkh)) {
			Patient patient = patientMapper.getPatient(jzkh);
			Record record = buildRecord(request, patient);
			recordMapper.insert(record);
			if (patient.getSTATUS() == Patient.STATUS_HASSENDSMS) {
				patient.setSTATUS(Patient.STATUS_HASLOGIN);
			}
			return new ModelAndView("redirect:/patient");
		} else {
			return new ModelAndView("redirect:/login");
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/patient")
	public ModelAndView getRecordList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String jzkh = request.getRemoteUser();
		List<PatientInfo> patientInfos = patientInfoMapper.getInfoListByPatientID(jzkh);
		Patient patient = patientMapper.getPatient(jzkh);
		Map<String, List<PatientInfo>> dateInfoMap = new TreeMap<String, List<PatientInfo>>();
		boolean hasSartSmsNotification = false;	// 是否请求短信通知
		
		if (patient.getSTATUS() == Patient.STATUS_REQUIRESMS) {
			hasSartSmsNotification = true;
		}
		
		for (PatientInfo info : patientInfos) {
			String sampleNo = info.getSampleNo();
			String date = sampleNo.substring(0, 4) + "-" + sampleNo.substring(4, 6) + "-" + sampleNo.substring(6, 8);
			if (!dateInfoMap.containsKey(date)) {
				dateInfoMap.put(date, new ArrayList<PatientInfo>());
			}
			dateInfoMap.get(date).add(info);
		}
		
		List<Map.Entry<String, List<PatientInfo>>> dateInfoList = new ArrayList<Map.Entry<String,List<PatientInfo>>>(dateInfoMap.entrySet());
		for (Map.Entry<String, List<PatientInfo>> dateGroup : dateInfoList) {
			List<PatientInfo> infoList = dateGroup.getValue();
			Collections.sort(infoList, new Comparator<PatientInfo>() {
				public int compare(PatientInfo o1, PatientInfo o2) {
					return o2.getReceivetime().compareTo(o1.getReceivetime());
				}
			});
		}
		Collections.sort(dateInfoList, new Comparator<Map.Entry<String, List<PatientInfo>>>() {
			public int compare(Entry<String, List<PatientInfo>> o1, Entry<String, List<PatientInfo>> o2) {
				return o2.getKey().compareTo(o1.getKey());
			}
		});
		
		request.setAttribute("hasRequireSms", hasSartSmsNotification);
		request.setAttribute("dateInfoMap", dateInfoList);
		request.setAttribute("patientName", patient.getXM());
		request.setAttribute("patientSex", patient.getSex());
		request.setAttribute("patientAge", patient.getAge() + "岁");
		request.setAttribute("patientBlh", patient.getBAH());
		return new ModelAndView("patient");
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/record")
	public ModelAndView getRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		initNameMap();
		String id = request.getParameter("id");
		PatientInfo patientInfo = patientInfoMapper.getInfoByDoctID(Long.parseLong(id));
		if (!patientInfo.getPatientId().equals(request.getRemoteUser())) {
			throw new Exception("访问页面与当前登录用户不符...");
		}
		String sampleNo = patientInfo.getSampleNo();
		List<TestResult> results= testResultMapper.getResultBySampleNo(sampleNo);
		Patient patient = patientMapper.getPatient(patientInfo.getPatientId());
		Iterator<TestResult> resultIterator = results.iterator();
		while (resultIterator.hasNext()) {
			TestResult result = resultIterator.next();
			String name = testNameMap.get(result.getTestId());
			if (name == null) {
				resultIterator.remove();
			} else {
				result.setName(name);
			}
		}
		request.setAttribute("doctID", id);
		request.setAttribute("testResult", results);
		request.setAttribute("sampleNo", patientInfo.getSampleNo());
		request.setAttribute("patientExamine", patientInfo.getExaminaim());
		request.setAttribute("patientDate", shortdf.format(patientInfo.getReceivetime()));
		request.setAttribute("patientName", patientInfo.getPatientName());
		request.setAttribute("patientSex", patient.getSex());
		request.setAttribute("patientAge", patient.getAge() + "岁");
		request.setAttribute("sampleType", "血液");
		request.setAttribute("diagnostic", patientInfo.getDiagnostic());
		request.setAttribute("patientBlh", patient.getBAH());
		return new ModelAndView("record");
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/request")
	public ModelAndView submitRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String jzkh = request.getRemoteUser();
		List<Request> requests = requestMapper.getPatientRequest(jzkh);
		request.setAttribute("requests", requests);
		return new ModelAndView("request");
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/ajax/request")
	@ResponseBody
	public int customRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int result = 0;
		try {
			String requestContent = request.getParameter("content");
			if (StringUtils.isEmpty(requestContent)) {
				requestContent = "请求化验单解释";
			}
			String jzkh = request.getRemoteUser();
			
			if (requestUtil == null) {
				requestUtil = RequestUtil.getInstance(requestMapper);
			}
			requestUtil.receiveRequest(jzkh, requestContent);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/ajax/notification")
	@ResponseBody
	public int startSmsNotification(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int result = 0;
		try {
			String op = request.getParameter("op");
			String jzkh = request.getRemoteUser();
			Patient patient = patientMapper.getPatient(jzkh);
			if ("start".equals(op)) {
				patient.setSTATUS(Patient.STATUS_REQUIRESMS);
				result = 1;
			} else if ("stop".equals(op)) {
				patient.setSTATUS(Patient.STATUS_HASLOGIN);
				result = 1;
			}
			patientMapper.update(patient);
			return result;
		} catch (Exception e) {
			return result;
		}
	}
	
	private Record buildRecord(HttpServletRequest request, Patient patient) {
		
		Record record = new Record();
		record.setPatientId(patient.getJZKH());
		record.setSex(patient.getXB());
		record.setAge(patient.getAge());
		
		String agent = request.getHeader("User-Agent");
		if (agent.contains("iPhone")) {
			record.setDevice(1);
		} else if (agent.contains("Android")) {
			record.setDevice(2);
		} else if (agent.contains("Windows")) {
			record.setDevice(3);
		}
		record.setLoginTime(new Date());
		
		return record;
	}
}
