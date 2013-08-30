package com.zju.dao;

import java.util.List;

import com.zju.model.ReasoningModify;

public interface ReasoningModifyDao extends GenericDao<ReasoningModify, Long> {
	List<ReasoningModify> getByDocNo(String docNo);

	int getAddNumber();

	int getDragNumber();

}
