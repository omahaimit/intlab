package com.zju.helper.check;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import com.zju.drools.DroolsRunner;
import com.zju.drools.Ratio;
import com.zju.model.PatientInfo;
import com.zju.model.TestDescribe;
import com.zju.service.RuleManager;
import com.zju.webapp.util.AuditUtil;

public class RatioCheck implements Check {

	private Map<String, TestDescribe> idMap = null;
	private Map<String, Ratio> ratioMap = null;
	private DroolsRunner droolsRunner = DroolsRunner.getInstance();
	
	public RatioCheck(Map<String, TestDescribe> idMap, RuleManager ruleManager) {
		this.idMap = idMap;
		ratioMap = AuditUtil.getDealRatio(ruleManager);
	}
	
	@Override
	public boolean doCheck(PatientInfo info) {
		
		boolean result = true;
		Set<String> ratioResult = droolsRunner.getRatioCheckResult(info.getResults(), ratioMap);
		if (ratioResult.size() != 0) {
			String markTests = info.getMarkTests();
			result = false;
			Set<String> noteSet = new HashSet<String>();
			for (String ratioId : ratioResult) {
				markTests += ratioId + RATIO_COLOR;
				TestDescribe des = idMap.get(ratioId);
				if (des != null)
					noteSet.add(des.getChineseName());
				else
					noteSet.add(ratioId);
			}
			String note = "比值：" + CheckUtil.toString(noteSet);
			info.setNotes(note);
			info.setAuditMark(RATIO_MARK);
			info.setAuditStatus(UNPASS);
			info.setMarkTests(markTests);
		}
		
		return result;
	}

}
