package com.zju.webapp.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.zju.webapp.dao.PatientInfoDao;
import com.zju.webapp.model.PatientInfo;
import com.zju.webapp.model.PatientInfoWithResult;

public class PatientInfoManager implements PatientInfoService {

	private PatientInfoDao patientInfoDao;

	@Autowired
	public void setPatientInfoDao(PatientInfoDao patientInfoDao) {
		this.patientInfoDao = patientInfoDao;
	}

	@Override
	public List<PatientInfo> getPatientInfoFromZ1(Date date) {
		return patientInfoDao.getPatientInfoFromZ1(date);
	}

	@Override
	public List<PatientInfoWithResult> getPatientInfoWithResultByMeasureTime(
			Date date) {
		return patientInfoDao.getPatientInfoWithResultByMeasureTime(date);
	}
}
