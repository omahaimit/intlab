package com.zju.dao;

import java.util.List;

import com.zju.model.Distribute;

public interface BayesDao {

	List<Distribute> getDistribute(String testId);
	
	void update(List<Distribute> disList);
}
