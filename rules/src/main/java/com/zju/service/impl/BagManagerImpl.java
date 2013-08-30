package com.zju.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.zju.dao.BagDao;
import com.zju.model.Bag;
import com.zju.service.BagManager;
import com.zju.service.BagService;

public class BagManagerImpl extends GenericManagerImpl<Bag, Long> implements BagManager, BagService {
	
	private BagDao bagDao;

	@Autowired
	public void setBagDao(BagDao bagDao) {
		this.dao = bagDao;
		this.bagDao = bagDao;
	}
	
	@Override
	public List<Bag> getBagByName(String name) {
		return bagDao.getBag(name);
	}

	@Override
	public List<Bag> getBag() {
		return bagDao.getBag();
	}

	@Override
	public List<Bag> getBag(Long parentId) {
		return bagDao.getByParentId(parentId);
	}
}
