package com.zju.dao.hibernate;

import java.util.List;
import com.zju.dao.BagDao;
import com.zju.model.Bag;

public class BagDaoHibernate extends GenericDaoHibernate<Bag, Long> implements BagDao {

	/**
     * Constructor to create a Generics-based version using bag as the entity
     */
	public BagDaoHibernate() {
		super(Bag.class);
	}

	@SuppressWarnings("unchecked")
	public List<Bag> getByParentId(Long parentId) {
		return getHibernateTemplate().find("from Bag where parent_id=?",parentId);
	}

	@SuppressWarnings("unchecked")
	public List<Bag> getBag() {
		return getHibernateTemplate().find("from Bag b order by upper(b.id)");
	}

	@SuppressWarnings("unchecked")
	public List<Bag> getBag(String name) {
		return getHibernateTemplate().find("from Bag where name like '%" + name + "%'");
	}
}
