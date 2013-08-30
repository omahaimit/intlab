package com.zju.dao;

import java.util.List;

import com.zju.model.PatientInfo;

/**
 * PatientInfo Data Access Object (GenericDao) interface.
 */
public interface PatientInfoDao extends GenericDao<PatientInfo, Long> {
	
	/**
     * Gets patient information for role_doctor based on date.
     * @param username the doctor's username
     * @param fromDate
     * @param toDate 
     * @return patient information populated patientInfo object
     * found in database
     */
	List<PatientInfo> getForDoctor(String username, String fromDate, String toDate);
	
	/**
     * Gets patient information for role_patient based on date.
     * @param patientId the patient's id
     * @param fromDate
     * @param toDate 
     * @return patient information populated patientInfo object
     * found in database
     */
	List<PatientInfo> getForPatient(String patientId, String fromDate, String toDate);

	
	/**
     * Gets patient information for sample based on patientId.
     * @param patientId the patient's id
     * @return patient information populated patientInfo object
     * found in database
     */
	List<PatientInfo> getById(String patientId);
	
	/**
     * Gets patient information for sample based on sampleNo.
     * @param sampleNo the sample's id
     * @return patient information populated patientInfo object
     * found in database
     */
	List<PatientInfo> getBySampleNo(String sampleNo);
	
	/**
	 *  获取检验人员
	 * @param operator  检验员姓名
	 * @param fromDate  起始时间
	 * @param toDate   结束时间
	 * @param sample  样本段
	 * @return
	 */
	List<PatientInfo> getForAudit(String operator, String fromDate, String toDate, String sample);
	
	/**
	 *   获取该检验人员24小时内的样本
	 * @param operator
	 * @return
	 */
	List<PatientInfo> getForAudit(String operator);
	
	/**
	 *   根据样本的标记和状态获取样本
	 * @param operator
	 * @param mark
	 * @param status
	 * @return
	 */
	List<PatientInfo> getForAudit(String operator, int mark, int status);

	List<PatientInfo> getDiffCheck(PatientInfo info);
	
	List<PatientInfo> getSampleByOperator(String date, String department, String code, int mark, int status);
	
	List<Integer> getAuditInfo(String date, String department, String code, String user);

	List<PatientInfo> getHistoryTable(String patientId, String lab);
	
	List<PatientInfo> getHasResult(String sample, int limit);

	List<PatientInfo> getByBLH(String blh);

	List<PatientInfo> getSampleByCode(String code);

	List<PatientInfo> getByPatientName(String from, String to, String pName);

}

