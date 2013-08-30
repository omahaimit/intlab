package com.zju.service;

import java.util.List;

import com.zju.dao.ItemDao;
import com.zju.model.Item;
import com.zju.model.Library;

public interface ItemManager extends GenericManager<Item, Long> {

	void setItemDao(ItemDao itemDao);
	
	/**
	 *  添加知识点并获取id
	 * @param item
	 * @return 新增知识点
	 */
	Item addItem(Item item);
	
	/**
	 *  该知识点是否冲突，包括已存在的情况
	 * @param item
	 * @return
	 */
	boolean isItemConflict(Item item);
	
	Item exsitItem(Long indexId, String value);
	
	List<Library> getPatientInfo(String name);
	
	Library getInfo(String sign);
	
	Item getWithIndex(Long id);
}
