package com.zju.catcher.service.z1;

import java.util.Date;
import java.util.List;

import com.zju.catcher.entity.z1.TestResult;
import com.zju.catcher.entity.z1.TestResultPK;

public interface TestResultService {

	List<TestResult> getListBetween(Date from, Date to);
	
	List<String> getSampleNo(Date from, Date to);
	
	List<TestResult> getEditList(Date from, Date to);
	
	List<TestResultPK> getTestResult(Date date);
	
	List<TestResult> getResult(String preSample);
	
	void writeBackResult(List<TestResult> updateList);
	
	void updateField(List<TestResult> resultList);
	
	void deleteTestResult(List<TestResult> resultList);
	
	void addTestResult(List<TestResult> resultList);
	
	void editTestResult(List<TestResult> resultList);
	
	/**
	 * @param date 起始时间
	 * @return 起始时间到现在修改样本的样本号
	 */
	List<String> getEditSampleNoFrom(Date date);
}
