package com.zju.catcher.dao.z1;

import java.util.List;

import com.zju.catcher.entity.z1.ReferenceValue;

public interface ReferenceValueDao {
	
	List<ReferenceValue> getAll();

	List<String> getIdList();
}
