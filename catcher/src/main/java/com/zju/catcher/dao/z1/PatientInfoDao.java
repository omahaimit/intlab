package com.zju.catcher.dao.z1;

import java.util.Date;
import java.util.List;

import com.zju.catcher.entity.local.PatientSample;
import com.zju.catcher.entity.z1.PatientAndResult;
import com.zju.catcher.entity.z1.PatientInfo;

public interface PatientInfoDao {

	List<PatientInfo> getListBetween(Date from, Date to);
	
	List<PatientInfo> getAllChanged();
	
	List<PatientInfo> getList(List<String> sampleNoList);
	
	List<PatientAndResult> getNotToday(Date date);
	
	List<PatientAndResult> getAll(Date date);
	
	List<PatientInfo> getPatientInfo(Date date);
	
	void UpdateSampleHasPass(List<PatientSample> infos);
}
