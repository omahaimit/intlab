package com.zju.webapp.controller.explain;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.zju.model.PatientInfo;
import com.zju.model.TestDescribe;
import com.zju.model.TestResult;
import com.zju.service.PatientInfoManager;
import com.zju.service.TestDescribeManager;
import com.zju.service.TestResultManager;


/**
 * Class to show the test results.
 */
@Controller
@RequestMapping("/explain/result*")
public class ResultController {
	private TestResultManager testResultManager;
	private TestDescribeManager testDescribeManager;
	private PatientInfoManager patientInfoManager;
	private static HashMap<String,String> idMap=new HashMap<String,String>();

	@Autowired
	public void setTestResultManager(TestResultManager testResultManager) {
		this.testResultManager = testResultManager;
	}
	
	@Autowired
	public void setTestDescribeManager(TestDescribeManager testDescribeManager) {
		this.testDescribeManager = testDescribeManager;
	}
	
	@Autowired
	public void setPatientInfoManager(PatientInfoManager patientInfoManager) {
		this.patientInfoManager = patientInfoManager;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception{
		if(idMap.size()==0){
			initMap(idMap);
		}
		String sampleNo=request.getParameter("sampleNo");
		if(sampleNo==null){
			return new ModelAndView();
		}
		PatientInfo patientInfo= patientInfoManager.getBySampleNo(sampleNo);
		List<TestResult> testList=testResultManager.getBySampleNo(sampleNo);
		for(TestResult t:testList){
			t.setChineseName(idMap.get(t.getTestId()));
		}
		return new ModelAndView("explain/result","testList",testList)
			.addObject("patient", patientInfo);
	}
	
	private void initMap(HashMap<String, String> idMap2) {
		List<TestDescribe> list=testDescribeManager.getAll();
		for(TestDescribe t:list){
			idMap.put(t.getTestId(), t.getChineseName());
		}
	}

}
