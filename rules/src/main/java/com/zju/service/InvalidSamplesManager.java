package com.zju.service;

import java.util.List;

import com.zju.model.InvalidSamples;

public interface InvalidSamplesManager {
	
	void remove(String barCode);
	
	void save(InvalidSamples sample);
	
	InvalidSamples getSample(String barCode);

	boolean barCodeExist(String barCode);

	List<InvalidSamples> getList(String username, String fromDate, String toDate, String labDepartment)
			throws Exception;

	List<InvalidSamples> getPatients(List<InvalidSamples> returnList);
}