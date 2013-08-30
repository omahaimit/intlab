package com.zju.catcher.service.z1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zju.catcher.dao.z1.ReferenceValueDao;
import com.zju.catcher.entity.z1.ReferenceValue;

@Service
public class ReferenceValueServiceManager implements ReferenceValueService {
	
	@Autowired
	private ReferenceValueDao referenceValueDao;
	
	public List<ReferenceValue> getAll() {
		return referenceValueDao.getAll();
	}
	
	public List<String> getIdList() {
		return referenceValueDao.getIdList();
	}

}
