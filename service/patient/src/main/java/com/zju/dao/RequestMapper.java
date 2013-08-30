package com.zju.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.zju.model.Request;

public interface RequestMapper {

	List<Request> getPatientRequest(String patientId);
	
	List<Request> getAllRequest(Date date);
	
	List<Request> getAllRequestByStatus(Map<String, Object> map);
	
	void insert(Request request);
	
	void update(Request request);
	
	int getRequestCount(String date);
	
	int getRequestUnRespondCount(String date);
}
