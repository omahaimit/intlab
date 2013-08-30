package com.zju.dao;

import java.util.List;

import com.zju.model.InvalidSamples;

public interface InvalidSamplesDao {
	
	InvalidSamples get(String barCode);
	
	void remove(String barCode);

    boolean exists(String barCode);

	public void save(InvalidSamples sample);

	public List<InvalidSamples> findByParamter(String username, String fromdate, String todate, String labdepartment);
}
