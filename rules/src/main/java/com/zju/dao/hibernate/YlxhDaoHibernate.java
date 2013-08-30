package com.zju.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zju.dao.YlxhDao;
import com.zju.model.Ylxh;

@Repository
public class YlxhDaoHibernate extends GenericDaoHibernate<Ylxh, Long> implements YlxhDao {

	/**
     * Constructor to create a Generics-based version using bag as the entity
     */
	public YlxhDaoHibernate() {
		super(Ylxh.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Ylxh> getTest(String lab) {
		return getHibernateTemplate().find("from Ylxh where ksdm=?", lab);
	}
}
