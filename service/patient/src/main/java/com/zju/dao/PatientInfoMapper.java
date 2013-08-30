package com.zju.dao;

import java.util.List;
import com.zju.model.PatientInfo;

public interface PatientInfoMapper {

	PatientInfo getInfoByDoctID(long doctID);
	
	List<PatientInfo> getInfoListByPatientID(String patientID);
	
	void batchInsert(List<PatientInfo> patientInfoList);
	
	void batchDelete(List<PatientInfo> patientInfoList);
	
	int getSampleCount(String preSampleNo);
	
	int getPatientCount(String preSampleNo);
}
