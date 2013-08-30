package com.zju.webapp.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.zju.model.Distribute;
import com.zju.model.PatientInfo;
import com.zju.model.TestResult;
import com.zju.service.BayesService;

public class BayesUtil {

	private Map<String, List<Distribute>> disMap = new HashMap<String, List<Distribute>>();
	private BayesService bayesService = null;
	private final int AUDIT_MAX_COUNT = 1000;
	private int index = 0;
	private static BayesUtil util = null;
	
	public static BayesUtil getInstance(BayesService bayesService) {
		if (util == null) {
			util = new BayesUtil(bayesService);
		}
		return util;
	}
	
	private BayesUtil(BayesService bayesService) {
		this.bayesService = bayesService;
	}
	
	public void add(PatientInfo info) {
		
		if (info == null || info.getResults() == null) {
			return;
		}
		//System.out.println("Add");
		if (++index > AUDIT_MAX_COUNT) {
			List<Distribute> disList = new ArrayList<Distribute>();
			for (List<Distribute> list : disMap.values())
				disList.addAll(list);
			//System.out.println("Update");
			bayesService.update(disList);
			disMap.clear();
			index = 0;
		} /*else {
			System.out.println(disMap.size());
		}*/
		
		for (TestResult tr : info.getResults()) {
			String testId = tr.getTestId();
			String result = tr.getTestResult();
			
			if (!isFloat(result)) continue;
			int sex = (info.getSex() != 2) ? 1 : 2;
			float val = Float.parseFloat(result);
			boolean isPass = info.getAuditStatus() == 1 ? true : false;
			List<Distribute> disList = getDistributes(testId);
			for (Distribute dis : disList) {
				if (dis.getSEX() == sex && dis.isIn(val)) {
					if (isPass)
						dis.addPass();
					else
						dis.addUnpass();
					break;
				}
			}
		}
	}
	
	public void move(PatientInfo info) {
		if (info == null || info.getResults() == null) {
			return;
		}
		
		for (TestResult tr : info.getResults()) {
			String testId = tr.getTestId();
			String result = tr.getTestResult();
			if (!isFloat(result)) continue;
			int sex = (info.getSex() != 2) ? 1 : 2;
			float val = Float.parseFloat(result);
			boolean isPass = info.getAuditStatus() == 1 ? true : false;
			List<Distribute> disList = getDistributes(testId);
			for (Distribute dis : disList) {
				if (dis.getSEX() == sex && dis.isIn(val)) {
					if (isPass) {
						dis.removeUnpass();
						dis.addPass();
					} else {
						dis.removePass();
						dis.addUnpass();
					}
					break;
				}
			}
		}
	}
	
	public double audit(PatientInfo info) {
		int sex = (info.getSex() != 2) ? 1 : 2;
		List<Float> rateList = new ArrayList<Float>();
		
		for (TestResult tr : info.getResults()) {
			String key = tr.getTestId();
			String value = tr.getTestResult();
			if (!isFloat(value)) continue;

			float val = Float.parseFloat(value);
			int passCount = 0, unPassCount = 0;
			int passTotal = 0, unPassTotal = 0;
			boolean hasGetCount = false;
			for (Distribute dis : getDistributes(key)) {
				if (dis.getSEX() == sex) {
					if (!hasGetCount && dis.isIn(val)) {
						passCount = dis.getPASSCOUNT();
						unPassCount = dis.getUNPASSCOUNT();
						hasGetCount = true;
					}
					passTotal += dis.getPASSCOUNT();
					unPassTotal += dis.getUNPASSCOUNT();
				}
			}
			
			if (passTotal == 0 || unPassTotal == 0 || passCount == 0 || unPassCount == 0) continue;
			
			float rate = passCount * 1.0f / passTotal / (passCount * 1.0f / passTotal + unPassCount * 1.0f / unPassTotal);
			rateList.add(rate);
		}
		
		double d1 = 1.0;
		double d2 = 1.0;
		for (Float f : rateList) {
			d1 *= f.doubleValue();
			d2 *= 1.0 - f.doubleValue();
		}
		
		return d1 / (d1 + d2);
	}
	
	private List<Distribute> getDistributes(String testId) {
		if (!disMap.containsKey(testId)) {
			List<Distribute> disList = bayesService.getDistribute(testId);
			if (disList != null) {
				disMap.put(testId, disList);
			} else {
				disMap.put(testId, new ArrayList<Distribute>());
			}
		}
		return disMap.get(testId);
	}
	
	private boolean isFloat(String value) {
		try {
			Float.parseFloat(value);
		} catch(Exception e) {
			return false;
		}
		return true;
	}
}
