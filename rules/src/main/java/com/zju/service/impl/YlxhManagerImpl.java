package com.zju.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.zju.dao.YlxhDao;
import com.zju.model.Ylxh;
import com.zju.service.YlxhManager;

public class YlxhManagerImpl extends GenericManagerImpl<Ylxh, Long> implements YlxhManager {

	private YlxhDao ylxhDao;

	@Autowired
	public void setYlxhDao(YlxhDao ylxhDao) {
		this.dao = ylxhDao;
		this.ylxhDao = ylxhDao;
	}

	@Override
	public List<Ylxh> getTest(String lab) {
		return ylxhDao.getTest(lab);
	}
}
