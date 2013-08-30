package com.zju.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.zju.dao.BayesDao;
import com.zju.model.Distribute;
import com.zju.service.BayesService;

public class BayesManagerImpl implements BayesService {

	private BayesDao bayesDao;
	
	@Autowired
	public void setBayesDao(BayesDao bayesDao) {
		this.bayesDao = bayesDao;
	}

	@Override
	public List<Distribute> getDistribute(String testId) {
		return bayesDao.getDistribute(testId);
	}

	@Override
	public void update(List<Distribute> disList) {
		bayesDao.update(disList);
	}

}
