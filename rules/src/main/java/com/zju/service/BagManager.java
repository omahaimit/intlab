package com.zju.service;

import java.util.List;
import com.zju.model.Bag;

/**
 * Business Service Interface to handle communication between web and
 * persistence layer.
 * 
 * @author 王建平
 */
public interface BagManager extends GenericManager<Bag, Long> {

	/**
	 *  获取所有的包
	 * @return
	 */
	List<Bag> getBag();
	
	/**
	 *  获取所有子包
	 * @param parentId	父包Id
	 * @return
	 */
	List<Bag> getBag(Long parentId);
	
	/**
	 *  根据名称模糊搜索
	 * @param name	包名称
	 * @return
	 */
	List<Bag> getBagByName(String name);
}
