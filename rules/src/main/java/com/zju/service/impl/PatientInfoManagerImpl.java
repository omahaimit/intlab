package com.zju.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.zju.dao.PatientInfoDao;
import com.zju.model.PatientInfo;
import com.zju.service.PatientInfoManager;
import com.zju.service.PatientInfoService;

public class PatientInfoManagerImpl 
	extends GenericManagerImpl<PatientInfo, Long> implements PatientInfoManager, PatientInfoService{

	PatientInfoDao patientInfoDao;
	
	@Autowired
	public void setPatientInfoDao(PatientInfoDao patientInfoDao) {
		this.dao = patientInfoDao;
		this.patientInfoDao = patientInfoDao;
	}
	
	/**
     * {@inheritDoc}
     */
	@Override
	public List<PatientInfo> getForPatient(String patientId, String fromDate,
			String toDate) {
		return patientInfoDao.getForPatient(patientId, fromDate, toDate);
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public List<PatientInfo> getSampleById(String patientId) {
		return patientInfoDao.getById(patientId);
	}
	
	/**
     * {@inheritDoc}
     */
	@Override
	public PatientInfo getBySampleNo(String sampleNo) {
		List<PatientInfo> list = patientInfoDao.getBySampleNo(sampleNo);
		if(list.size() > 0){
			return list.get(0);
		}else{
			return null;
		}
	}

	public List<PatientInfo> getForAudit(String operator, String fromDate, String toDate, String sample) {
		return patientInfoDao.getForAudit(operator, fromDate, toDate, sample);
	}

	public List<PatientInfo> getForAudit(String operator) {
		return patientInfoDao.getForAudit(operator);
	}

	public List<PatientInfo> getForAudit(String operator, int mark, int status) {	
		return patientInfoDao.getForAudit(operator, mark, status);
	}

	@Override
	public List<PatientInfo> getForDoctor(String doctorId, String fromDate,
			String toDate) {
		return patientInfoDao.getForDoctor(doctorId, fromDate, toDate);
	}

	@Override
	public List<PatientInfo> getDiffCheckHistory(PatientInfo info) {
		return patientInfoDao.getDiffCheck(info);
	}

	@Override
	public List<PatientInfo> getSampleList(String date, String department, String code, int mark, int status) {
		return patientInfoDao.getSampleByOperator(date, department, code, mark, status);
	}

	@Override
	public List<Integer> getAuditInfo(String date, String department, String code, String user) {
		return patientInfoDao.getAuditInfo(date, department, code, user);
	}

	@Override
	public List<PatientInfo> getHistorySample(String patientId, String lab) {
		return patientInfoDao.getHistoryTable(patientId, lab);
	}

	@Override
	public List<PatientInfo> getSampleByBLH(String blh) {
		return patientInfoDao.getByBLH(blh);
	}

	public List<PatientInfo> getHasResult(String sample, int limit) {
		return patientInfoDao.getHasResult(sample, limit);
	}

	@Override
	public List<PatientInfo> getListBySampleNo(String sampleNo) {
		List<PatientInfo> list = patientInfoDao.getBySampleNo(sampleNo);
		if(list.size() > 0){
			return list;
		}else{
			return null;
		}
	}

	@Override
	public List<PatientInfo> getSampleByPrefix(String prefix) {
		return patientInfoDao.getSampleByCode(prefix);
	}

	@Override
	public List<PatientInfo> getSampleByPatientName(String fromDate, String toDate,
			String patientName) {
		return patientInfoDao.getByPatientName(fromDate, toDate, patientName);
	}
}
