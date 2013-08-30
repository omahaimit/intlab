package com.zju.service;

import java.util.List;

import com.zju.model.AuditTrace;

/**
 * 	获取审核记录
 * @author Winstar
 *
 */
public interface AuditTraceManager extends GenericManager<AuditTrace, Long> {

	/**
	 * 获取某样本的审核记录
	 * @param sampleNo	样本号
	 * @return	审核记录列表
	 */
	List<AuditTrace> getBySampleNo(String sampleNo);
}
