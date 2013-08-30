package com.zju.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zju.dao.TestDescribeDao;
import com.zju.model.TestDescribe;
import com.zju.service.TestDescribeManager;

@Service("testDescribeManager")
public class TestDescribeManagerImpl 
	extends GenericManagerImpl<TestDescribe, String> implements TestDescribeManager{
	
	@SuppressWarnings("unused")
	private TestDescribeDao testDescribeDao;
	
	@Autowired
	public void setTestDescribeDao(TestDescribeDao testDescribeDao) {
		this.dao=testDescribeDao;
		this.testDescribeDao = testDescribeDao;
	}


}
