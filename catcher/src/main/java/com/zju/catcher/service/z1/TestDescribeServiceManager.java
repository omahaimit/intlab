package com.zju.catcher.service.z1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zju.catcher.dao.z1.TestDescribeDao;
import com.zju.catcher.entity.z1.TestDescribe;

@Service
public class TestDescribeServiceManager implements TestDescribeService {

	@Autowired
	private TestDescribeDao testDescribeDao;
	
	public List<TestDescribe> getAll() {
		return testDescribeDao.getAll();
	}

}
