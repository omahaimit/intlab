package com.zju.dao;

import java.util.List;

import com.zju.model.AuditTrace;

public interface AuditTraceDao extends GenericDao<AuditTrace, Long> {
	
	List<AuditTrace> getBySampleNo(String sampleNo);

}
