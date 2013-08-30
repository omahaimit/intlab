package com.zju.dao.hibernate;

import java.util.List;

import com.zju.dao.TestModifyDao;
import com.zju.model.TestModify;

public class TestModifyDaoHibernate extends GenericDaoHibernate<TestModify, Long> implements TestModifyDao {

	/**
     * Constructor to create a Generics-based version using ReasoningModify as the entity
     */
	public TestModifyDaoHibernate() {
		super(TestModify.class);
	}

	@SuppressWarnings("unchecked")
	public List<TestModify> getBySampleNo(String sampleNo) {
		return getHibernateTemplate().find("from TestModify where sampleNo=? order by modifyTime desc", sampleNo);
	}
}
