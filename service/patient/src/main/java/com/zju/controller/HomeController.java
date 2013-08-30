package com.zju.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.zju.dao.MessageMapper;
import com.zju.dao.PatientInfoMapper;
import com.zju.dao.PatientMapper;
import com.zju.dao.TestDescribeMapper;
import com.zju.dao.TestResultMapper;
import com.zju.dao.UserMapper;
import com.zju.model.PatientInfo;
import com.zju.model.TestDescribe;
import com.zju.model.TestResult;

@Controller
@RequestMapping("home") 
public class HomeController {

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private PatientInfoMapper patientInfoMapper;
	
	@Autowired
	private TestResultMapper testResultMapper;
	
	@Autowired
	private PatientMapper patientMapper;
	
	@Autowired
	private MessageMapper messageMapper;
	
	@Autowired
	private TestDescribeMapper testDescribeMapper;
	
	private Map<String, String> testNameMap = null;
	
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
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		PatientInfo info = patientInfoMapper.getInfoByDoctID(34420000);
		List<TestResult> testResult = testResultMapper.getResultBySampleNo("20130710CBC009");

		initNameMap();
		Iterator<TestResult> resultIterator = testResult.iterator();
		while (resultIterator.hasNext()) {
			TestResult result = resultIterator.next();
			String name = testNameMap.get(result.getTestId());
			if (name == null) {
				resultIterator.remove();
			} else {
				result.setName(name);
			}
		}
		request.setAttribute("patientExamine", info.getExaminaim());
		request.setAttribute("testResult", testResult);
		return new ModelAndView("helloworld");
	}

}
