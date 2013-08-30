package com.zju.dao;

import java.util.List;

import com.zju.model.Patients;

public interface PatientsDao {
	
	Patients get(long id);

    boolean exists(long id);
    
	public List<Patients> findBySection(String section);
}
