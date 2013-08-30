package com.zju.webapp.service;

import java.util.Date;
import java.util.List;

import com.zju.webapp.model.TestResult;

public interface TestResultService {

	List<TestResult> getTestResultFromZ1(Date date);
}
