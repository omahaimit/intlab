package com.zju.webapp.util;

import java.util.HashMap;
import java.util.Map;
import com.zju.model.Library;
import com.zju.service.IndexManager;

public class SampleUtil {

	private static SampleUtil instance = new SampleUtil();
	
	private Map<String, String> map = null;
	
	private SampleUtil() {}
	
	public static SampleUtil getInstance() {
		return instance;
	}
	
	public Map<String, String> getSampleList(IndexManager indexManager) {
		if (map == null) {
			synchronized (instance) {
				map = new HashMap<String, String>();
				for (Library sample : indexManager.getSampleList()) {
					map.put(sample.getSign(), sample.getValue());
				}
			}
		}
		return map;
	}
}
