package com.zju.webapp.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class IndexMapUtil {

	private Map<String, String> map = null;
	private static IndexMapUtil instance = new IndexMapUtil();
	
	private IndexMapUtil() {}
	
	public static IndexMapUtil getInstance() {
		if (instance.map == null) {
			synchronized (instance) {
				instance.initMap();
			}
		}
		return instance;
	}
	
	private void initMap() {
		
		map = new HashMap<String, String>();
		String dic = this.getClass().getResource("/").getPath();
		File file = new File(dic + "/map.txt");
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			while (line != null) {
				String[] mp = line.split(":");
				map.put(mp[0], mp[1]);
				line = reader.readLine();
			}
			reader.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *   指标ID映射
	 * @param indexId  原始指标ID
	 * @return	映射后的指标ID
	 */
	public String getValue(String indexId) {
		if (map.containsKey(indexId)) {
			return map.get(indexId);
		} else {
			return indexId;
		}
	}
	
	public String getKey(String indexId) {
		String str = indexId;
		if (map.containsValue(indexId)) {
			for(String s : map.keySet()) {
				if(map.get(s).equals(indexId))
					str = s;
			}
		} 
		return str;
	}
	
	public boolean isNeedMap(String indexId) {
		if (map.containsKey(indexId)) {
			return true;
		} else {
			return false;
		}
	}
}
