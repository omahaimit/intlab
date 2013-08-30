package com.zju.dao;

import java.util.List;

import com.zju.model.Ylxh;

public interface YlxhDao  extends GenericDao<Ylxh, Long> {

	List<Ylxh> getTest(String lab);

}
