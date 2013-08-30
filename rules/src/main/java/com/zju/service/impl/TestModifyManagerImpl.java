package com.zju.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.zju.dao.TestModifyDao;
import com.zju.model.TestModify;
import com.zju.service.TestModifyManager;

public class TestModifyManagerImpl extends GenericManagerImpl<TestModify, Long> implements TestModifyManager {

	private TestModifyDao testModifyDao;

	@Autowired
	public void setTestModifyDao(TestModifyDao testModifyDao) {
		this.testModifyDao = testModifyDao;
		this.dao = testModifyDao;
	}

	@Override
	public List<TestModify> getBySampleNo(String sampleNo) {
		return testModifyDao.getBySampleNo(sampleNo);
	}
}
