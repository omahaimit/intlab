package com.zju.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.zju.dao.MessageMapper;
import com.zju.dao.PatientInfoMapper;
import com.zju.dao.PatientMapper;
import com.zju.dao.RecordMapper;
import com.zju.dao.RequestMapper;
import com.zju.dao.TestDescribeMapper;
import com.zju.dao.TestResultMapper;
import com.zju.model.Message;
import com.zju.model.PatientInfo;
import com.zju.model.Record;
import com.zju.model.Request;
import com.zju.model.TestDescribe;
import com.zju.model.TestResult;
import com.zju.util.Config;
import com.zju.util.DataResponse;

@Controller
@RequestMapping("/admin") 
public class AdminController {
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private static final SimpleDateFormat ddf = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	private MessageMapper messageMapper;
	
	@Autowired
	private RecordMapper recordMapper;
	
	@Autowired
	private RequestMapper requestMapper;
	
	@Autowired
	private PatientInfoMapper patientInfoMapper;
	
	@Autowired
	private TestResultMapper testResultMapper;
	
	@Autowired
	private PatientMapper patientMapper;
	
	@Autowired
	private TestDescribeMapper testDescribeMapper;
	
	private Map<String, String> testNameMap = null;
	
	@RequestMapping(method = RequestMethod.GET, value="/message*")
	public ModelAndView messageRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
			
		request.setAttribute("today", ddf.format(new Date()));
		return new ModelAndView("messageMonitor");
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/message/ajax/count*")
	@ResponseBody
	public String messageCount(HttpServletRequest request, HttpServletResponse response) throws Exception {
			
		JSONObject root = new JSONObject();
		String date = request.getParameter("date");
		if (date.length() != 10) {
			date = ddf.format(new Date());
		}
		int allSample = patientInfoMapper.getSampleCount(date.replaceAll("-", ""));
		int msgCount = messageMapper.getMesssageSize(date);
		int respondCount = messageMapper.getMsgRespondSize(date);
		root.put("all", allSample);
		root.put("msg", msgCount);
		root.put("respond", respondCount);
		response.setContentType("text/html;charset=UTF-8");
		return root.toString();
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/patient*")
	public ModelAndView patientRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
			
		request.setAttribute("today", ddf.format(new Date()));
		return new ModelAndView("patientMonitor");
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/patient/ajax/count*")
	@ResponseBody
	public String patientCount(HttpServletRequest request, HttpServletResponse response) throws Exception {
			
		JSONObject root = new JSONObject();
		String date = request.getParameter("date");
		if (date.length() != 10) {
			date = ddf.format(new Date());
		}
		String preSampleNo = date.replaceAll("-", "");
		int allSample = patientInfoMapper.getSampleCount(preSampleNo);
		int allMsg = messageMapper.getMesssageSize(date);
		int respondCount = messageMapper.getMsgRespondSize(date);
		int allPatient = patientInfoMapper.getPatientCount(preSampleNo);
		int recordCount = recordMapper.getRecordSize(date);
		int patientCount = recordMapper.getPatientSize(date);
		root.put("allSample", allSample);
		root.put("allMsg", allMsg);
		root.put("msgRespond", respondCount);
		root.put("allPatient", allPatient);
		root.put("loginRecord", recordCount);
		root.put("loginPatient", patientCount);
		response.setContentType("text/html;charset=UTF-8");
		return root.toString();
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/system*")
	public ModelAndView systemRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setAttribute("smsStatus", Config.smsStatus.get());
		request.setAttribute("fromAge", Config.fromAge.get());
		request.setAttribute("toAge", Config.toAge.get());
		request.setAttribute("sex", Config.sex.get());
		request.setAttribute("maxCount", Config.maxCount.get());
		request.setAttribute("nativeUrl", Config.nativeUrl);
		Set<Integer> enbleSet = new HashSet<Integer>();
		for (String status : Config.enbleStatus.split(",")) {
			if (!StringUtils.isEmpty(status)) {
				enbleSet.add(Integer.parseInt(status));
			}
		}
		for (int i = 0; i <= 4; i++) {
			if (enbleSet.contains(new Integer(i))) {
				request.setAttribute("enble" + i, true);
			} else {
				request.setAttribute("enble" + i, false);
			}
		}
		return new ModelAndView("systemControl");
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/request*")
	public ModelAndView requestMonitor(HttpServletRequest request, HttpServletResponse response) throws Exception {
			
		request.setAttribute("today", ddf.format(new Date()));
		return new ModelAndView("requestMonitor");
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/doctor*")
	public ModelAndView queryPatient(HttpServletRequest request, HttpServletResponse response) throws Exception {
			
		String patientId = request.getParameter("patientId");
		if (!StringUtils.isEmpty(patientId)) {
			request.setAttribute("patientId", patientId);
		}
		return new ModelAndView("patientUtil");
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/request/ajax/count*")
	@ResponseBody
	public String requestCount(HttpServletRequest request, HttpServletResponse response) throws Exception {
			
		JSONObject root = new JSONObject();
		String date = request.getParameter("date");
		if (date.length() != 10) {
			date = ddf.format(new Date());
		}
		int allRequest = requestMapper.getRequestCount(date);
		int unRespond = requestMapper.getRequestUnRespondCount(date);
		root.put("all", allRequest);
		root.put("unRespond", unRespond);
		response.setContentType("text/html;charset=UTF-8");
		return root.toString();
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/ajax/smsGrid*")
	@ResponseBody
	public DataResponse getSMSGrid(HttpServletRequest request, HttpServletResponse response) throws Exception {
			
		String pages = request.getParameter("page");
		String rows = request.getParameter("rows");
		int page = Integer.parseInt(pages);
		int row = Integer.parseInt(rows);
		
		String date = request.getParameter("date");
		if (date.length() != 10) {
			date = ddf.format(new Date());
		}
		DataResponse dataResponse = new DataResponse();
		dataResponse.setPage(page);
		List<Message> messageList = messageMapper.getMesssageList(date);
		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		int size = messageList.size();
		dataResponse.setRecords(size);
		int x = size - size % row;
		if (x < size) {
			x = x + row;
		}
		dataResponse.setTotal(x / row);

		int start = row * (page - 1);
		int index = 0;
		while (index < row && (start + index) < size) {
			Message message = messageList.get(start + index);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("sampleNo", message.getSampleNo());
			map.put("patientId", message.getPatientId());
			map.put("telephone", message.getTelephone());
			map.put("sendTime", sdf.format(message.getSendTime()));
			map.put("response", message.getHasResponse());
			index++;
			dataRows.add(map);
		}
		dataResponse.setRows(dataRows);
		response.setContentType("text/html;charset=UTF-8");
		return dataResponse;
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/ajax/patientGrid*")
	@ResponseBody
	public DataResponse getPatientGrid(HttpServletRequest request, HttpServletResponse response) throws Exception {
			
		String pages = request.getParameter("page");
		String rows = request.getParameter("rows");
		int page = Integer.parseInt(pages);
		int row = Integer.parseInt(rows);
		
		String date = request.getParameter("date");
		if (date.length() != 10) {
			date = ddf.format(new Date());
		}
		DataResponse dataResponse = new DataResponse();
		dataResponse.setPage(page);
		List<Record> recordList = recordMapper.getRecordList(date);
		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		int size = recordList.size();
		dataResponse.setRecords(size);
		int x = size - size % row;
		if (x < size) {
			x = x + row;
		}
		dataResponse.setTotal(x / row);

		int start = row * (page - 1);
		int index = 0;
		while (index < row && (start + index) < size) {
			Record record = recordList.get(start + index);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("patientId", record.getPatientId());
			map.put("device", record.getDeviceValue());
			map.put("loginTime", sdf.format(record.getLoginTime()));
			map.put("age", record.getAge());
			map.put("sex", record.getSexValue());
			index++;
			dataRows.add(map);
		}
		dataResponse.setRows(dataRows);
		response.setContentType("text/html;charset=UTF-8");
		return dataResponse;
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/ajax/requestGrid*")
	@ResponseBody
	public DataResponse getRequestGrid(HttpServletRequest request, HttpServletResponse response) throws Exception {
			
		String pages = request.getParameter("page");
		String rows = request.getParameter("rows");
		int page = Integer.parseInt(pages);
		int row = Integer.parseInt(rows);
		
		String date = request.getParameter("date");
		if (date.length() != 10) {
			date = ddf.format(new Date());
		}
		String status = request.getParameter("status");
		DataResponse dataResponse = new DataResponse();
		dataResponse.setPage(page);
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("date", ddf.parse(date));
		parmMap.put("status", Integer.parseInt(status));
		List<Request> requestList = requestMapper.getAllRequestByStatus(parmMap);
		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		int size = requestList.size();
		dataResponse.setRecords(size);
		int x = size - size % row;
		if (x < size) {
			x = x + row;
		}
		dataResponse.setTotal(x / row);

		int start = row * (page - 1);
		int index = 0;
		while (index < row && (start + index) < size) {
			
			Request req = requestList.get(start + index);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", req.getId());
			map.put("patientId", req.getPatientId());
			map.put("requestTime", sdf.format(req.getRequestTime()));
			if (req.getRespondTime() == null) {
				map.put("respondTime", null);
			} else {
				map.put("respondTime", sdf.format(req.getRespondTime()));
			}
			map.put("responder", req.getResponder());
			map.put("respond", req.getRespondContent());
			map.put("content", req.getRequestContent());
			map.put("status", req.getRespondMethod());
			index++;
			dataRows.add(map);
		}
		dataResponse.setRows(dataRows);
		response.setContentType("text/html;charset=UTF-8");
		return dataResponse;
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/ajax/sampleNoList*")
	@ResponseBody
	public DataResponse getsampleNoList(HttpServletRequest request, HttpServletResponse response) throws Exception {
			
		String patientId = request.getParameter("patientId");

		DataResponse dataResponse = new DataResponse();
		List<PatientInfo> infos = patientInfoMapper.getInfoListByPatientID(patientId);
		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		dataResponse.setRecords(infos.size());

		for (PatientInfo info : infos) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("sampleNo", info.getSampleNo());
			map.put("examinaim", info.getExaminaim());
			dataRows.add(map);
		}
		dataResponse.setRows(dataRows);
		response.setContentType("text/html;charset=UTF-8");
		return dataResponse;
	}
	
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
	
	@RequestMapping(method = RequestMethod.GET, value="/ajax/resultList*")
	@ResponseBody
	public DataResponse getresultList(HttpServletRequest request, HttpServletResponse response) throws Exception {
			
		String sampleNo = request.getParameter("sampleNo");
		System.out.println(sampleNo);
		DataResponse dataResponse = new DataResponse();
		List<TestResult> results = testResultMapper.getResultBySampleNo(sampleNo);
		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		dataResponse.setRecords(results.size());
		initNameMap();
		System.out.println(testNameMap.size());
		System.out.println(results.size());
		
		for (TestResult result : results) {
			Map<String, Object> map = new HashMap<String, Object>();
			String testId = result.getTestId();
			if (testNameMap.containsKey(testId)) {
				map.put("testId", testId);
				map.put("name", testNameMap.get(testId));
				map.put("result", result.getTestResult());
				map.put("scope", result.getScope());
				map.put("unit", result.getUnit());
				dataRows.add(map);
			}
		}
		dataResponse.setRows(dataRows);
		response.setContentType("text/html;charset=UTF-8");
		return dataResponse;
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/ajax/dealRequest*")
	@ResponseBody
	public int dealRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
			
		int resultFlag = 0;
		try {
			String id = request.getParameter("id");
			String content = request.getParameter("content");
			if (id != null) {
				Request req = new Request();
				req.setId(new Long(id));
				req.setRespondContent(content);
				req.setRespondTime(new Date());
				req.setRespondMethod(1);
				requestMapper.update(req);
				resultFlag = 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setContentType("text/html;charset=UTF-8");
		return resultFlag;
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/ajax/control/filter*")
	@ResponseBody
	public int controlFilter(HttpServletRequest request, HttpServletResponse response) throws Exception {
			
		int resultFlag = 0;
		try {
			Integer smsStatus = Integer.parseInt(request.getParameter("sms"));
			Integer ageFrom = Integer.parseInt(request.getParameter("from"));
			Integer ageTo = Integer.parseInt(request.getParameter("to"));
			Integer sex = Integer.parseInt(request.getParameter("sex"));
			Integer maxCount = Integer.parseInt(request.getParameter("max"));
			String enbleStr = request.getParameter("enble");
			String nativeUrl = request.getParameter("native");
			
			Config.smsStatus.set(smsStatus == 1 ? true : false);
			Config.setAge(ageFrom, ageTo);
			Config.sex.set(sex);
			Config.maxCount.set(maxCount);
			Config.enbleStatus = enbleStr;
			Config.nativeUrl = nativeUrl;
			resultFlag = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setContentType("text/html;charset=UTF-8");
		return resultFlag;
	}
}
