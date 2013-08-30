package com.zju.webapp.util;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zju.model.Constants;
import com.zju.model.Describe;
import com.zju.model.FormulaItem;
import com.zju.model.PatientInfo;
import com.zju.model.TestDescribe;
import com.zju.model.TestResult;
import com.zju.service.PatientInfoManager;
import com.zju.service.SyncManager;
import com.zju.service.TestResultManager;

public class FormulaUtil {

	private Map<String, List<FormulaItem>> formulaMap = new HashMap<String, List<FormulaItem>>();
	private Map<String, TestDescribe> idMap = null;
	private SyncManager syncManager = null;
	private TestResultManager testResultManager = null;
	private PatientInfoManager patientInfoManager = null;
	private FillFieldUtil fillUtil = null;
	
	private static FormulaUtil util = new FormulaUtil();
	
	private FormulaUtil() {}
	
	public static FormulaUtil getInstance(SyncManager syncManager, TestResultManager testResultManager, PatientInfoManager patientInfoManager, Map<String, TestDescribe> idMap, FillFieldUtil fillUtil) {
		util.syncManager = syncManager;
		util.testResultManager = testResultManager;
		util.patientInfoManager = patientInfoManager;
		util.idMap = idMap;
		util.fillUtil = fillUtil;
		return util;
	}
	
	public void formula(PatientInfo info, String operator) {
		String lab = info.getLabdepartMent();
		
		if (!formulaMap.containsKey(lab)) {
			List<FormulaItem> items = syncManager.getFormulaItem(lab);
			formulaMap.put(lab, items);
		}
		
		List<FormulaItem> items = formulaMap.get(lab);
		if (items != null && items.size() != 0) {
			Map<String, TestResult> testMap = new HashMap<String, TestResult>();
			for (TestResult tr : info.getResults()) {
				String id = tr.getTestId();
				if (idMap.containsKey(id)) {
					TestDescribe des = idMap.get(tr.getTestId());
					testMap.put(id + "[" + des.getSampleType(), tr);
				}
			}
			for (FormulaItem item : items) {
				String fm = item.getFORMULA();
				String[] keys = item.getFORMULAITEM().split(",");
				String testid = item.getTESTID();
				char sampletype = item.getSAMPLETYPE();
				int isprint = item.getISPRINT();
	
				boolean flag = true;
				for (String key : keys) {
					if (!testMap.containsKey(key)) {
						flag = false;
					}
				}
				if (flag) {
					boolean isFloat = true;
					for (String key : keys) {
						TestResult tr = testMap.get(key);
						if(!isDouble(tr.getTestResult())) {
							isFloat = false;
						}
						if(isFloat) {
							fm = fm.replace(key, tr.getTestResult());
						} else {
							break;
						}
					}
					
					fm = fm.replace("sex", String.valueOf(info.getSex()));
					fm = fm.replace("age", String.valueOf(info.getAge()));
					
					if (info.getAge() == 0) {
						isFloat = false;
					}
					
					boolean isSave = true;
					if(isFloat){
						TestResult t = null;
						String k = testid + "[" + sampletype;
						Describe des = fillUtil.getDescribe(testid);
						if (testMap.containsKey(k)) {
							t = testMap.get(k);
							t.setCorrectFlag("6");
							if (t.getEditMark() != 0 && t.getEditMark() % Constants.EDIT_FLAG != 0){
								t.setEditMark(t.getEditMark() * Constants.EDIT_FLAG);
							}
						} else {
							t = new TestResult();
							t.setSampleNo(info.getSampleNo());
							t.setTestId(testid);
							t.setSampleType(sampletype);
							t.setTestStatus(1);
							t.setCorrectFlag("3");
							t.setEditMark(Constants.ADD_FLAG);
							isSave = false;
						}
						if (des != null) {
							t.setUnit(des.getUNIT());
						}
						t.setMeasureTime(new Date());
						t.setOperator(operator);
						t.setIsprint(isprint);
						
						if (isSave) {
							testResultManager.save(t);
						} else {
							info.getResults().add(t);
							patientInfoManager.save(info);
						}
						
						syncManager.updateFormula(t, fm);
						
						TestResult test = syncManager.getTestResult(testid, info.getSampleNo());
						fillUtil.fillResult(test, info);
						syncManager.saveTestResult(test);
						for (TestResult tr : info.getResults()) {
							String id = test.getTestId();
							if (tr.getTestId().equals(id)) {
								tr.setTestResult(test.getTestResult());
							}
						}
					}
				}
			}
		}
	}
	
	private boolean isDouble(String str) {
		try {
			Double.parseDouble(str);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
