package com.zju.webapp.service;

import java.util.Date;
import java.util.List;

import com.zju.webapp.model.PatientInfo;
import com.zju.webapp.model.PatientInfoWithResult;

public interface PatientInfoService {

	List<PatientInfo> getPatientInfoFromZ1(Date date);
	
	List<PatientInfoWithResult> getPatientInfoWithResultByMeasureTime(Date date);
}
