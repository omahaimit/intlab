package com.zju.dao;

import java.util.List;

import com.zju.model.TestResult;

public interface TestResultMapper {

	List<TestResult> getResultBySampleNo(String sampleNo);
	
	void batchInsert(List<TestResult> resultList);
	
	void batchDelete(List<TestResult> resultList);
}
