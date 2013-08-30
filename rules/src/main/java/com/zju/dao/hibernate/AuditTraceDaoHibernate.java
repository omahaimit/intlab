package com.zju.dao.hibernate;

import java.util.List;

import com.zju.dao.AuditTraceDao;
import com.zju.model.AuditTrace;

public class AuditTraceDaoHibernate extends GenericDaoHibernate<AuditTrace, Long> implements AuditTraceDao  {

	/**
     * Constructor to create a Generics-based version using ReasoningModify as the entity
     */
	public AuditTraceDaoHibernate() {
		super(AuditTrace.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AuditTrace> getBySampleNo(String sampleNo) {
		return getHibernateTemplate().find("from AuditTrace where sampleno=? order by checktime desc", sampleNo);
	}

}
