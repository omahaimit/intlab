package com.zju.webapp.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.zju.webapp.dao.TestResultDao;
import com.zju.webapp.model.TestResult;

public class TestResultManager implements TestResultService {

	private TestResultDao testResultDao;
	
	@Autowired
	public void setTestResultDao(TestResultDao testResultDao) {
		this.testResultDao = testResultDao;
	}

	@Override
	public List<TestResult> getTestResultFromZ1(Date date) {
		return testResultDao.getTestResultFromZ1(date);
	}

}
