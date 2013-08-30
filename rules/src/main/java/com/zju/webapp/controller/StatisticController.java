package com.zju.webapp.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zju.model.PatientInfo;
import com.zju.model.Statistic;
import com.zju.model.TestDescribe;
import com.zju.model.TestResult;
import com.zju.service.PatientInfoManager;
import com.zju.service.TestDescribeManager;
import com.zju.webapp.util.DataResponse;


@Controller
@RequestMapping("/statistic*")
public class StatisticController {
	
	private static Log log = LogFactory.getLog(StatisticController.class);
	private PatientInfoManager patientInfoManager = null;
	private TestDescribeManager testDescribeManager = null;
	private Map<String, TestDescribe> idMap = new HashMap<String, TestDescribe>();
	
	@RequestMapping(method = {RequestMethod.GET})
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView();
	}
	
	@RequestMapping(value = "/sample*", method = RequestMethod.GET)
	@ResponseBody
	public DataResponse getStatistics(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String code = request.getParameter("code");
		String fromNo = request.getParameter("from");
		String toNo = request.getParameter("to");
		String day = request.getParameter("time");
		DecimalFormat deFormat = new DecimalFormat("#.####");
		
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
		
		Map<String, List<Double>> resultMap = new HashMap<String, List<Double>>();
		List<Statistic> statisticList = new ArrayList<Statistic>();
		
		for (PatientInfo info : list) {
			for (TestResult t : info.getResults()) {
				List<Double> resultList = new ArrayList<Double>();
				if(resultMap.containsKey(t.getTestId())){
					resultList = resultMap.get(t.getTestId());
				} else {
					resultMap.put(t.getTestId(), resultList);
				}
				try { 
					double b = Double.parseDouble(t.getTestResult()); 
					resultList.add(b);
				} catch (Exception e){
					log.error(e.getMessage());
				} 
			}
		}
		
		for (String tId : resultMap.keySet()) {
			Statistic s = new Statistic();
			int num = 0;
			Double average;
			Double max = 0.0;
			Double min = 10000.0;
			Double total = 0.0;
			Double standardDeviation;
			Double coefficientOfVariation;
			s.setTestid(tId);
			List<Double> result = resultMap.get(tId);
			for (Double d : result) {
				if(d > max){
					max = d;
				}
				if(d < min){
					min = d;
				}
				total = total + d;
				num = num +1;
			}
			average = total/result.size();
			s.setNum(num);
			s.setAverage(average);
			s.setMax(max);
			s.setMin(min);
			Double variance = 0.0;
			for (Double d : result) {
				variance = variance + Math.pow(d-average, 2);
			}
			standardDeviation = Math.sqrt(variance/result.size());
			coefficientOfVariation = standardDeviation*100/average;
			s.setStandardDeviation(standardDeviation);
			s.setCoefficientOfVariation(coefficientOfVariation);
			statisticList.add(s);
		}
		
		DataResponse dataResponse = new DataResponse();
		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		
		if (idMap.size() == 0)
			initMap();

		for (Statistic s : statisticList) {
			if(idMap.containsKey(s.getTestid())){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", s.getTestid());
				map.put("name", idMap.get(s.getTestid()).getChineseName());
				map.put("num", s.getNum());
				map.put("average", deFormat.format(s.getAverage()));
				map.put("max", s.getMax());
				map.put("min", s.getMin());
				map.put("standardDeviation", deFormat.format(s.getStandardDeviation()));
				map.put("coefficientOfVariation", deFormat.format(s.getCoefficientOfVariation()));
				dataRows.add(map);
			}
		}
		
		dataResponse.setRows(dataRows);
		dataResponse.setRecords(dataRows.size());
		
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
	
}
