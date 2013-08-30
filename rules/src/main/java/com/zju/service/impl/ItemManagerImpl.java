package com.zju.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.zju.dao.ItemDao;
import com.zju.model.Item;
import com.zju.model.Library;
import com.zju.service.IndexService;
import com.zju.service.ItemManager;

public class ItemManagerImpl extends GenericManagerImpl<Item, Long> implements ItemManager, IndexService {

	private ItemDao itemDao;
	
	@Autowired
	public void setItemDao(ItemDao itemDao) {
		this.dao = itemDao;
		this.itemDao = itemDao;
	}
	
	public Item addItem(Item item) {
		return itemDao.save(item);
	}

	public boolean isItemConflict(Item item) {
		return false;
	}
	
	public Item exsitItem(Long indexId, String value) {
		return itemDao.exsitItem(indexId, value);
	}

	@Override
	public List<Library> getPatientInfo(String name) {
		return itemDao.getPatientInfo(name);
	}

	@Override
	public Library getInfo(String sign) {
		return itemDao.getInfo(sign);
	}

	@Override
	public Item getWithIndex(Long id) {
		return itemDao.getWithIndex(id);
	}

}
