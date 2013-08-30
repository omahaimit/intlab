package com.zju.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.zju.dao.AuditTraceDao;
import com.zju.model.AuditTrace;
import com.zju.service.AuditTraceManager;

public class AuditTraceManagerImpl extends GenericManagerImpl<AuditTrace, Long> implements AuditTraceManager {

	private AuditTraceDao auditTraceDao;

	@Autowired
	public void setTestModifyDao(AuditTraceDao auditTraceDao) {
		this.auditTraceDao = auditTraceDao;
		this.dao = auditTraceDao;
	}

	@Override
	public List<AuditTrace> getBySampleNo(String sampleNo) {
		return auditTraceDao.getBySampleNo(sampleNo);
	}
}