package com.zju.catcher.dao.z1;

import java.util.Date;
import java.util.List;

import com.zju.catcher.entity.z1.PatientAndResult;
import com.zju.catcher.entity.z1.TestResult;
import com.zju.catcher.entity.z1.TestResultPK;

public interface TestResultDao {

	List<TestResult> getListBetween(Date from, Date to);
	
	List<String> getSampleNo(Date from, Date to);
	
	List<TestResult> getEditList(Date from, Date to);
	
	List<TestResult> getResult(String preSample);
	
	List<TestResultPK> getTestResult(Date date);
	
	List<PatientAndResult> getAll(Date date);
	
	void updateField(List<TestResult> resultList);
	
	void deleteTestResult(List<TestResult> resultList);
	
	void addTestResult(List<TestResult> resultList);
	
	void editTestResult(List<TestResult> resultList);
	
	List<String> getEditSampleNoFrom(Date date);
}
