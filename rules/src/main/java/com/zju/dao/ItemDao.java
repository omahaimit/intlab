package com.zju.dao;

import java.util.List;
import com.zju.model.Item;
import com.zju.model.Library;

public interface ItemDao extends GenericDao<Item, Long> {

	public List<Library> getPatientInfo(String name);
	
	public Library getInfo(String sign);
	
	public Item getWithIndex(Long id);
	
	public Item exsitItem(Long indexId, String value);
}
