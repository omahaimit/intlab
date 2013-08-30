package com.zju.dao.hibernate;

import org.springframework.stereotype.Repository;

import com.zju.dao.TestDescribeDao;
import com.zju.model.TestDescribe;


@Repository("testDescribeDao")
public class TestDescribeDaoHibernate 
	extends GenericDaoHibernate<TestDescribe, String> implements TestDescribeDao{

	/**
     * Constructor that sets the entity to TestDescribe.class.
     */
    public TestDescribeDaoHibernate() {
        super(TestDescribe.class);
    }
}
