package com.zju.catcher.dao.local;

import java.util.Date;
import java.util.List;

import com.zju.catcher.entity.local.MapPK;
import com.zju.catcher.entity.local.PK;
import com.zju.catcher.entity.z1.TestResult;

public interface TestDataDao {

	void insert(List<TestResult> testResults);
	
	void update(List<TestResult> testResults);
	
	void delete(List<TestResult> testResults);
	
	void insertMapping(List<MapPK> mapList);
	
	List<PK> getTestResultPK(Date date);
	
	List<PK> getTestResultPKNot(Date date);
	
	List<MapPK> getMapPk(Date date);
	
	List<TestResult> getResultWithEdit(String sample, String lib, String operator);
	
	void updateEditMark(List<TestResult> resultList);
	
	List<TestResult> getSample(String sampleNo);
}
