package com.zju.catcher.service.z1;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zju.catcher.dao.z1.TestResultDao;
import com.zju.catcher.entity.z1.TestResult;
import com.zju.catcher.entity.z1.TestResultPK;

@Service
public class TestResultServiceManager implements TestResultService {

	@Autowired
	private TestResultDao testResultDao;
	
	public List<TestResult> getResult(String preSample) {
		return testResultDao.getResult(preSample);
	}
	
	public List<TestResult> getListBetween(Date from, Date to) {
		if (from == null)
			return null;
		else if (to == null)
			to = new Date();

		return testResultDao.getListBetween(from, to);
	}

	public List<String> getSampleNo(Date from, Date to) {
		return testResultDao.getSampleNo(from, to);
	}

	public List<TestResult> getEditList(Date from, Date to) {
		return testResultDao.getEditList(from, to);
	}

    public List<TestResultPK> getTestResult(Date date) {
        return testResultDao.getTestResult(date);
    }

	public void writeBackResult(List<TestResult> updateList) {
		
	}

	public void updateField(List<TestResult> resultList) {
		testResultDao.updateField(resultList);
	}

	public void deleteTestResult(List<TestResult> resultList) {
		testResultDao.deleteTestResult(resultList);
	}

	public void addTestResult(List<TestResult> resultList) {
		testResultDao.addTestResult(resultList);
	}

	public void editTestResult(List<TestResult> resultList) {
		testResultDao.editTestResult(resultList);
	}

	public List<String> getEditSampleNoFrom(Date date) {
		return testResultDao.getEditSampleNoFrom(date);
	}
}
