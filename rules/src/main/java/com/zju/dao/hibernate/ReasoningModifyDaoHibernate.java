package com.zju.dao.hibernate;

import java.util.List;

import com.zju.dao.ReasoningModifyDao;
import com.zju.model.ReasoningModify;

public class ReasoningModifyDaoHibernate extends GenericDaoHibernate<ReasoningModify, Long> implements ReasoningModifyDao {

	/**
     * Constructor to create a Generics-based version using ReasoningModify as the entity
     */
	public ReasoningModifyDaoHibernate() {
		super(ReasoningModify.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ReasoningModify> getByDocNo(String docNo) {
		
		return getHibernateTemplate().find("from ReasoningModify r where r.docNo=? order by r.modifyTime",docNo);
	}

	@Override
	public int getAddNumber() {
		return getHibernateTemplate().find("from ReasoningModify r where r.type='ADD'").size();
	}

	@Override
	public int getDragNumber() {
		return getHibernateTemplate().find("from ReasoningModify r where r.type='DRAG'").size();
	}
}
