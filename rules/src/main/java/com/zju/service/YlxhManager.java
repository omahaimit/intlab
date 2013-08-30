package com.zju.service;

import java.util.List;

import com.zju.model.Ylxh;

public interface YlxhManager extends GenericManager<Ylxh, Long> {

	/**
     * 获得某一检验科室所做的检验套餐
     *
     * @param lab 科室的编号
     * @return Ylxh 该科室所有的检验套餐
     */
	List<Ylxh> getTest(String lab);

}
