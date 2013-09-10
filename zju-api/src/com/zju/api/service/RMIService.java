package com.zju.api.service;

import java.util.List;

import com.zju.api.model.FormulaItem;
import com.zju.api.model.Ksdm;
import com.zju.api.model.LabGroupInfo;
import com.zju.api.model.Patient;

public interface RMIService {

	List<Ksdm> getAllKsdm();
	
	List<String> getProfileJYZ(String profileName, String deviceId);
	
	List<Patient> getPatientList(String patientIds);
	
	List<FormulaItem> getFormulaItem(String labdepartment);
	
	List<LabGroupInfo> getLabGroupInfo();
	
	int writeBack(String code, String lab, String user);
	
	int writeBack(String code, String lab, String user, int from, int to);
}
