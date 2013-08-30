package com.zju.service;

import java.util.List;

import com.zju.model.ReasoningModify;

/**
 *  解释处理
 * @author Winstar
 *
 */
public interface ReasoningModifyManager extends GenericManager<ReasoningModify, Long> {
	
	/**
	 *  获取某样本的解释列表
	 * @param docNo
	 * @return
	 */
	List<ReasoningModify> getByDocNo(String docNo);

	int getAddNumber();

	int getDragNumber();
}
