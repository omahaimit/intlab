package com.zju.service;

import java.util.List;

import com.zju.model.TestModify;

/**
 * 检验结果修改记录的接口
 */
public interface TestModifyManager extends GenericManager<TestModify, Long> {

	/**
     * 获得某一样本的检验项目修改记录
     *
     * @param sampleNo 样本号
     * @return List 该样本的检验项目修改记录
     */
	List<TestModify> getBySampleNo(String sampleNo);

}
