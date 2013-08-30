package com.zju.webapp.controller;

import java.util.HashSet;
import java.util.Set;

import org.codehaus.jettison.json.JSONArray;
import org.junit.Test;

import com.zju.model.IDMap;

public class TestController {

	@Test
	public void mainTest() {
		IDMap map1 = new IDMap();
		IDMap map2 = new IDMap();
		Set<IDMap> set = new HashSet<IDMap>();
		set.add(map1);
		set.add(map2);
		JSONArray array = new JSONArray(set);
		System.out.println(array.toString());
		
	}
}
