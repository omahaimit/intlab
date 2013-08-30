package com.zju.webapp.dao;

import java.util.Date;
import java.util.List;

import com.zju.webapp.model.TestDescribe;
import com.zju.webapp.model.TestResult;

public interface TestResultDao {

	List<TestResult> getTestResultFromZ1(Date date);
	
	List<TestResult> getTestResultByMeasureTime(Date date);
	
	void updateClodMark(List<TestResult> results);
	
	List<TestDescribe> getAllDescribe();
}
