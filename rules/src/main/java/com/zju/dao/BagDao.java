package com.zju.dao;

import java.util.List;

import com.zju.model.Bag;

public interface BagDao extends GenericDao<Bag, Long> {
	
	List<Bag> getByParentId(Long parentId);

	List<Bag> getBag();
	
	List<Bag> getBag(String name);
	
}
