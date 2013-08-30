package com.zju.dao;

import java.util.Date;
import java.util.List;

import com.zju.model.TestResult;
import com.zju.model.TestResultPK;


public interface TestResultDao extends GenericDao<TestResult, TestResultPK>{
	
	
	/**
     * Gets test results for sample.
     * @param sample the sample's id
     * @return the test results
     * found in database
     */
	List<TestResult> getBySampleNo(String sampleNo);

	/**
     * Gets test results for sampleList.
     * @param sampleList the samples' id list
     * @return the test results
     * found in database
     */
	List<TestResult> getByList(String sampleList);

	/**
     * Gets test results for history map.
     * @param patientId the patient's id
     * @param history the testid list for the history  
     * @return the test results
     * found in database
     */
	List<TestResult> getHistory(String patientId, String history);
	
	
	/**
     * Gets test results for patient.
     * @param patientId the patient's id
     * @return the test results
     * found in database
     */
	List<TestResult> getByPatientId(String patientId);
	
	/**
     * Gets test results for difference checksum.
     * @param patientId the patient's id
     * @param testId the test's id
     * @param date the test's measuretime
     * @return the test results
     * found in database
     */
	List<TestResult> getForDifCheck(String patientId, String testId, Date date);

	List<TestResult> getUnAudit(String operator);

	List<TestResult> getListByTestString(String sampleNo, String testString);

	List<TestResult> getSingleHistory(String testid, String patientName,
			String birthday);

}
