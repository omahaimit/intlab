package com.zju.catcher.service.local;

import java.util.Date;
import java.util.List;

import com.zju.catcher.entity.local.PatientSample;
import com.zju.catcher.entity.z1.PatientInfo;

public interface PatientSampleService {

	void insert(List<PatientSample> patientInfos);
	
	void update(List<PatientInfo> patientInfos);
	
	void updateWithStatus(List<PatientSample> patientInfos);
	
	List<Long> getPatientId(Date date);
	
	List<Long> getPatientId(Date date, int status);
	
	List<PatientSample> getPatientInfo(String sample, String lib, int status, String operator);
	
	PatientSample getPatientInfo(String sampleNo);
	
	void updateEditPatientInfo(List<String> sampleNoList);
	
	List<String> exsitSampleList(List<PatientInfo> sampleNoList);
}
