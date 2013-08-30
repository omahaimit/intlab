package com.zju.webapp.util;

import java.util.HashMap;
import java.util.Map;

import com.zju.model.Section;
import com.zju.service.SyncManager;

public class SectionUtil {

	private static SectionUtil instance = new SectionUtil();
	private static Map<String, String> map = null;
	
	private SectionUtil () {}
	
	public static SectionUtil getInstance(SyncManager manager) {
		
		if (map == null) {
			map = new HashMap<String, String>();
			for (Section s : manager.getSection()) {
				map.put(s.getKsdm(), s.getKsmc());
			}
		}
		return instance;
	}
	
	public String getValue(String key) {
		if (map.containsKey(key)) {
			return map.get(key);
		} else {
			return key;
		}
	}
}
