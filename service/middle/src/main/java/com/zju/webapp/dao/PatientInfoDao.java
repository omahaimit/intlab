package com.zju.webapp.dao;

import java.util.Date;
import java.util.List;

import com.zju.webapp.model.PatientInfo;
import com.zju.webapp.model.PatientInfoWithResult;

public interface PatientInfoDao {

	List<PatientInfo> getPatientInfoFromZ1(Date date);
	
	List<PatientInfoWithResult> getPatientInfoWithResultByMeasureTime(Date date);
	
	List<PatientInfo> getPatientInfoByMeasureTime(Date date);
	
	void updateClodMark(List<PatientInfo> infos);
}
