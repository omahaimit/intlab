package com.zju.webapp.controller;

import java.util.Iterator;
import java.util.List;

import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zju.model.Index;
import com.zju.service.IndexManager;

public class SomeThingControllerTestCase extends BaseControllerTestCase  {

	@Autowired
	private IndexManager indexManager = null;
	
	@Test
	public void TestSeatchIndexByName() throws Exception {
		List<Index> indexs = indexManager.getIndexs("上");
		System.out.println(indexs.size());
		System.out.println(indexs.get(0).getName());
	}
	
	@SuppressWarnings("rawtypes")
	@Test
    public void testJsonIterator() throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("id", "1");
        obj.put("data", "first");
        obj.put("children", "");
        
        Iterator  keys = obj.keys();
        while(keys.hasNext()) {
        	Object key = keys.next();
        	System.out.println(key.equals("id"));
        	if (key.equals("children")) {
        		keys.remove();
        	}
        }
        System.out.println(obj.toString());
    }
}
