package com.zju.dao;

import java.util.List;

import com.zju.model.TestModify;

public interface TestModifyDao extends GenericDao<TestModify, Long> {

	List<TestModify> getBySampleNo(String sampleNo);

}
