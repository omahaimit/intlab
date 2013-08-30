package com.zju.webapp.dao;

import java.util.List;

import com.zju.webapp.model.Patient;

public interface PatientDao {

	List<Patient> getPatients(String inSql);
	
	List<String> exsitPatients(String inSql);
	
	void batchInsert(List<Patient> patients);
}
