package com.zju.service;

import java.util.List;

import com.zju.model.Distribute;

/**
 * 贝叶斯算法的数据Service
 * @author Winstar
 *
 */
public interface BayesService {

	/**
	 * 	获取某指标的贝叶斯分离对象
	 * @param testId
	 * @return
	 */
	List<Distribute> getDistribute(String testId);
	
	/**
	 *  更新分离数据
	 * @param disList
	 */
	void update(List<Distribute> disList);
}
