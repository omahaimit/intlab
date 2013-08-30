package com.zju.dao;

import java.util.List;

import com.zju.model.Patient;

public interface PatientMapper {

	void batchInsert(List<Patient> patientList);
	
	void batchDelete(List<Patient> patientList);
	
	void update(Patient patient);
	
	List<Patient> getPatients(List<String> jzkhs);
	
	Patient getPatient(String jzkh);
}
