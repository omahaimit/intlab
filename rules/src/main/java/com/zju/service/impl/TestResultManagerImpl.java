package com.zju.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zju.dao.TestResultDao;
import com.zju.model.TestResult;
import com.zju.model.TestResultPK;
import com.zju.service.TestResultManager;
import com.zju.service.TestResultService;

@Service("testResultManager")
public class TestResultManagerImpl 
	extends GenericManagerImpl<TestResult, TestResultPK> 
		implements TestResultManager, TestResultService{

	private TestResultDao testResultDao;
	
	@Autowired
	public void setTestResultDao(TestResultDao testResultDao) {
		this.dao=testResultDao;
		this.testResultDao = testResultDao;
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public List<TestResult> getBySampleNo(String sampleNo) {
		return testResultDao.getBySampleNo(sampleNo);
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public List<TestResult> getByList(String sampleList) {
		return testResultDao.getByList(sampleList);
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public List<TestResult> getHistory(String patientId, String history) {
		return testResultDao.getHistory(patientId, history);
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public List<TestResult> getByPatientId(String patientId) {
		return testResultDao.getByPatientId(patientId);
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public List<TestResult> getForDifCheck(String patientId, String testId,
			Date date) {
		return testResultDao.getForDifCheck(patientId, testId, date);
	}

	public List<TestResult> getUnAudit(String operator) {
		return testResultDao.getUnAudit(operator);
	}

	@Override
	public List<TestResult> getListByTestString(String sampleNo,
			String testString) {
		return testResultDao.getListByTestString(sampleNo, testString);
	}

	@Override
	public List<TestResult> getSingleHistory(String testid, String patientName,
			String birthday) {
		// TODO Auto-generated method stub
		return testResultDao.getSingleHistory(testid, patientName, birthday);
	}

}
