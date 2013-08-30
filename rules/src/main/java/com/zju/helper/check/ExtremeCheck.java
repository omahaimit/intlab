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
import com.zju.webapp.util.IndexMapUtil;

public class ExtremeCheck implements DroolsCheck {

	private DroolsRunner droolsRunner = DroolsRunner.getInstance();
	private RuleManager ruleManager = null;
	private static IndexMapUtil util = IndexMapUtil.getInstance();

	public ExtremeCheck(RuleManager ruleManager) {
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
			if (rule.getType() == EXTREME_RULE) {
				List<Index> indexs = ruleManager.getUsedIndex(rule.getId());
				for (Index i : indexs) {
					String s = util.getKey(i.getIndexId());
					if (trMap.containsKey(i.getIndexId())) {
						if(markTests.contains(i.getIndexId() + DIFF_COLOR)||!note.contains("差值")) {
							markTests += i.getIndexId() + EXTREME_COLOR;
							result = false;
						}
					} else if (trMap.containsKey(s)) {
						if(markTests.contains(s + DIFF_COLOR)||!note.contains("差值")) {
							markTests += s + EXTREME_COLOR;
							result = false;
						}
					}
				}
			}
		}
		
		if (!result) {
			info.setMarkTests(markTests);
			info.setAuditStatus(UNPASS);
			info.setAuditMark(EXTREME_MARK);
		}
		
		return result;
	}

}
