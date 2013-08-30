package com.zju.catcher.service.z1;

import java.util.List;

import com.zju.catcher.entity.z1.ReferenceValue;

public interface ReferenceValueService {

	List<ReferenceValue> getAll();

	List<String> getIdList();
}
