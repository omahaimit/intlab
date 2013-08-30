package com.zju.helper.check;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.zju.drools.DroolsRunner;
import com.zju.drools.R;
import com.zju.model.Index;
import com.zju.model.PatientInfo;
import com.zju.model.Rule;
import com.zju.model.TestResult;
import com.zju.service.RuleManager;

public class Alarm3Check implements DroolsCheck {

	private DroolsRunner droolsRunner = DroolsRunner.getInstance();
	private RuleManager ruleManager = null;

	public Alarm3Check(RuleManager ruleManager) {
		this.ruleManager = ruleManager;
	}
	
	@Override
	public boolean doCheck(PatientInfo info) {
		
		R r = droolsRunner.getResult(info.getResults(), info);
		return this.doCheck(info, r);
	}

	@Override
	public boolean doCheck(PatientInfo info, R r) {

		boolean result = true;
		String ruleId = CheckUtil.toString(r.getRuleIds());

		String markTests = info.getMarkTests();
		String note = info.getNotes();
		Map<String, TestResult> trMap = new HashMap<String, TestResult>();
		for (TestResult tr : info.getResults())
			trMap.put(tr.getTestId(), tr);
		
		for (Rule rule : ruleManager.getRuleList(ruleId)) {
			if (rule.getType() == ALARM3_RULE) {
				List<Index> indexs = ruleManager.getUsedIndex(rule.getId());
				for (Index i : indexs) {
					if (trMap.containsKey(i.getIndexId())) {
						if(markTests.contains(i.getIndexId() + DIFF_COLOR)||!note.contains("差值")) {
							markTests += i.getIndexId() + ALARM3_COLOR;
							result = false;
						}
					}
				}
			}
		}
		
		if (!result) {
			info.setMarkTests(markTests);
			info.setAuditStatus(UNPASS);
			info.setAuditMark(ALARM3_MARK);
		}
		
		return result;
	}

}
