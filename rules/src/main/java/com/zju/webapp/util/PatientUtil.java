package com.zju.webapp.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zju.model.Library;
import com.zju.service.ItemManager;

public class PatientUtil {

	private static PatientUtil instance = new PatientUtil();
	
	private List<Library> patientInfo = null;
	private Map<String, String> map1 = new HashMap<String, String>();
	private Map<Long, Library> map2 = new HashMap<Long, Library>();
	
	private PatientUtil() {}
	
	public static PatientUtil getInstance() {
		return instance;
	}
	
	synchronized private void initMap(ItemManager manager) {
		if (patientInfo != null) {
			return;
		}
		if (patientInfo == null) {
			patientInfo = manager.getPatientInfo("");
		}
		for (Library p : patientInfo) {
			map1.put(p.getSign(), p.getValue());
			map2.put(p.getId(), p);
		}
	}
	
	public Library getInfo(Long id, ItemManager manager) {
		if (patientInfo == null) {
			initMap(manager);
		}
		if (map2.containsKey(id)) {
			return map2.get(id);
		} else {
			return null;
		}
	}
	
	public String getValue(String sign, ItemManager manager) {
		if (patientInfo == null) {
			initMap(manager);
		}
		if (map1.containsKey(sign)) {
			return map1.get(sign);
		} else {
			return null;
		}
	}
	
}
