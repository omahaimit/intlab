package com.zju.webapp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zju.model.PatientInfo;
import com.zju.model.TestDescribe;
import com.zju.model.TestResult;
import com.zju.model.Tongji;
import com.zju.service.PatientInfoManager;
import com.zju.service.TestDescribeManager;
import com.zju.webapp.util.DataResponse;

@Controller
@RequestMapping("/tongji*")
public class TongjiController {
	
	private PatientInfoManager patientInfoManager = null;
	private TestDescribeManager testDescribeManager = null;
	private Map<String, TestDescribe> idMap = new HashMap<String, TestDescribe>();
	
	@RequestMapping(method = {RequestMethod.GET})
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView();
	}
	
	@RequestMapping(value = "/sample*", method = RequestMethod.GET)
	@ResponseBody
	public DataResponse getSample(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String code = request.getParameter("code");
		String fromNo = request.getParameter("from");
		String toNo = request.getParameter("to");
		String day = request.getParameter("time");
		
		List<PatientInfo> infoList = patientInfoManager.getSampleByPrefix(day+code);
		int start = Integer.parseInt(fromNo);
		int end = Integer.parseInt(toNo);
		
		List<PatientInfo> list = new ArrayList<PatientInfo>();
		// 过滤
		for (PatientInfo patient : infoList) {
			int index = Integer.parseInt(patient.getSampleNo().substring(11));
			if (index >= start && index <= end) {
				list.add(patient);
			}
		}
		
		Map<String, Tongji> tongjiMap = new HashMap<String, Tongji>();
		Map<String, Object> userdata = new HashMap<String, Object>();
		int totalUnpassSample = 0;
		int totalSample = 0;
		
		for (PatientInfo info : list) {
			if (info.getAuditStatus() == 1 || info.getAuditStatus() == 2) {
				Map<String, Integer> colorMap = StringToMap(info.getMarkTests());
				totalSample++;
				if (colorMap.size() > 0) {
					totalUnpassSample++;
				}
				for (TestResult t : info.getResults()) {
					String testid = t.getTestId();
					Tongji tongji = new Tongji();
					if (tongjiMap.containsKey(testid)) {
						tongji = tongjiMap.get(testid);
					} else {
						tongji.setTestid(testid);
					}
					int totalNo = tongji.getTotalNo() + 1;
					tongji.setTotalNo(totalNo);
					if (colorMap.containsKey(testid)) {
						int totalUnpassNo = tongji.getTotalUnpassNo() + 1;
						tongji.setTotalUnpassNo(totalUnpassNo);
						switch (colorMap.get(testid)) {
						case 1:
							int diffUnpassNo = tongji.getDiffUnpassNo() + 1;
							tongji.setDiffUnpassNo(diffUnpassNo);
							break;
						case 2:
							int ratioUnpassNo = tongji.getRatioUnpassNo() +1;
							tongji.setRatioUnpassNo(ratioUnpassNo);
							break;
						case 3:
							int dangerUnpassNo = tongji.getDangerUnpassNo() + 1;
							tongji.setDangerUnpassNo(dangerUnpassNo);
							break;
						case 4:
							int reTestUnpassNo = tongji.getReTestUnpassNo() + 1;
							tongji.setReTestUnpassNo(reTestUnpassNo);
							break;
						}
					} else {
						int passNo = tongji.getPassNo() + 1;
						tongji.setPassNo(passNo);
					}
					tongjiMap.put(testid, tongji);
				}
			}
		}
		
		DataResponse dataResponse = new DataResponse();
		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		
		if (idMap.size() == 0)
			initMap();
		
		for (Tongji tj : tongjiMap.values()) {
			if(idMap.containsKey(tj.getTestid())){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", tj.getTestid());
				map.put("name", idMap.get(tj.getTestid()).getChineseName());
				map.put("totalNo", tj.getTotalNo());
				map.put("passNo", tj.getPassNo());
				map.put("totalUnpassNo", tj.getTotalUnpassNo());
				map.put("diffUnpassNo", tj.getDiffUnpassNo());
				map.put("ratioUnpassNo", tj.getRatioUnpassNo());
				map.put("reTestUnpassNo", tj.getReTestUnpassNo());
				map.put("dangerUnpassNo", tj.getDangerUnpassNo());
				dataRows.add(map);
			}
		}
		
		dataResponse.setRows(dataRows);
		dataResponse.setRecords(dataRows.size());
		userdata.put("totalSample", totalSample);
		userdata.put("totalUnpassSample", totalUnpassSample);
		dataResponse.setUserdata(userdata);
		
		response.setContentType("text/html;charset=UTF-8");
		return dataResponse;
	}
	

	@Autowired
	public void setPatientInfoManager(PatientInfoManager patientInfoManager) {
		this.patientInfoManager = patientInfoManager;
	}
	
	@Autowired
	public void setTestDescribeManager(TestDescribeManager testDescribeManager) {
		this.testDescribeManager = testDescribeManager;
	}
	
	synchronized private void initMap() {
		List<TestDescribe> list = testDescribeManager.getAll();
		for (TestDescribe t : list) {
			idMap.put(t.getTestId(), t);
		}
	}
	
	private Map<String, Integer> StringToMap(String ts) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (String s : ts.split(";")) {
			if (!"".equals(s) && s.contains(":")) {
				String[] array = s.split(":");
				map.put(array[0], Integer.parseInt(array[1]));
			}
		}
		return map;
	}
}
