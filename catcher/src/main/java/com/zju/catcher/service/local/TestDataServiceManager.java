package com.zju.catcher.service.local;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zju.catcher.dao.local.TestDataDao;
import com.zju.catcher.entity.local.MapPK;
import com.zju.catcher.entity.local.PK;
import com.zju.catcher.entity.z1.TestResult;

@Service
public class TestDataServiceManager implements TestDataService {

    @Autowired
    private TestDataDao testDataDao;

    public void insert(List<TestResult> testResults) {
        testDataDao.insert(testResults);
    }

    public void update(List<TestResult> testResults) {
        testDataDao.update(testResults);
    }
    
    public void delete(List<TestResult> testResults) {
    	testDataDao.delete(testResults);
    }

    public void insertMapping(List<MapPK> mapList) {
        testDataDao.insertMapping(mapList);
    }

    public List<PK> getTestResultPK(Date date) {
        return testDataDao.getTestResultPK(date);
    }
    
    public List<PK> getTestResultPKNot(Date date) {
    	return testDataDao.getTestResultPKNot(date);
    }

    public List<MapPK> getMapPk(Date date) {
        return testDataDao.getMapPk(date);
    }

	public List<TestResult> getResultWithEdit(String sample, String lib, String operator) {
		return testDataDao.getResultWithEdit(sample, lib, operator);
	}

	public void updateEditMark(List<TestResult> resultList) {
		testDataDao.updateEditMark(resultList);
	}

	public List<TestResult> getSample(String sampleNo) {
		return testDataDao.getSample(sampleNo);
	}

}
