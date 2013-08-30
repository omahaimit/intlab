package com.zju.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.zju.dao.ResultDao;
import com.zju.model.Result;
import com.zju.service.ResultManager;
import com.zju.service.RuleService;

public class ResultManagerImpl extends GenericManagerImpl<Result, Long> implements ResultManager, RuleService {

	private ResultDao resultDao;
	
	@Autowired
	public void setResultDao(ResultDao resultDao) {
		this.dao = resultDao;
		this.resultDao = resultDao;
	}

	public List<Result> getResults(String name) {
		return resultDao.getResultsByName(name);
	}

	public List<Result> getResults(int pageNum, String field, boolean isAsc) {
		return resultDao.getResults(pageNum, field, isAsc);
	}

	public List<Result> getResults(String category, int pageNum, String field, boolean isAsc) {
		return resultDao.getResultsByCategory(category, pageNum, field, isAsc);
	}

	public Result addResult(Result result) {
		return resultDao.save(result);
	}

	public Result updateResult(Result result) {
		return resultDao.save(result);
	}

	@Override
	public int getResultsCount() {
		return resultDao.getResultsCount();
	}
}
