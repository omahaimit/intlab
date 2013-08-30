package com.zju.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.zju.dao.ReasoningModifyDao;
import com.zju.model.ReasoningModify;
import com.zju.service.ReasoningModifyManager;

public class ReasoningModifyManagerImpl extends GenericManagerImpl<ReasoningModify, Long> implements ReasoningModifyManager {

	private ReasoningModifyDao reasoningModifyDao;

	@Autowired
	public void setReasoningModifyDao(ReasoningModifyDao reasoningModifyDao) {
		this.reasoningModifyDao = reasoningModifyDao;
		this.dao = reasoningModifyDao;
	}

	@Override
	public List<ReasoningModify> getByDocNo(String docNo) {
		return reasoningModifyDao.getByDocNo(docNo);
	}

	@Override
	public int getAddNumber() {
		return reasoningModifyDao.getAddNumber();
	}

	@Override
	public int getDragNumber() {
		return reasoningModifyDao.getDragNumber();
	}
}
