package com.zju.helper.check;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.zju.drools.DroolsRunner;
import com.zju.drools.R;
import com.zju.model.Index;
import com.zju.model.PatientInfo;
import com.zju.model.Rule;
import com.zju.model.TestResult;
import com.zju.service.RuleManager;
import com.zju.webapp.util.IndexMapUtil;

public class RetestCheck implements DroolsCheck {

	private DroolsRunner droolsRunner = DroolsRunner.getInstance();
	private RuleManager ruleManager = null;
	private static IndexMapUtil util = IndexMapUtil.getInstance();
	
	public RetestCheck(RuleManager ruleManager) {
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
		Set<String> testIds = new HashSet<String>();
		for(TestResult t : info.getResults()) {
			testIds.add(t.getTestId());
		}
		
		for (Rule rule : ruleManager.getRuleManual(ruleId)) {
			if (rule.getType() == RETEST_RULE) {
				List<Index> indexs = ruleManager.getUsedIndex(rule.getId());
				for (Index i : indexs) {
					String s = util.getKey(i.getIndexId());
					if(testIds.contains(i.getIndexId())) {
						result = false;
						markTests += i.getIndexId() + RETEST_COLOR;
					} else if (testIds.contains(s)) {
						result = false;
						markTests += s + RETEST_COLOR;
					} else {
						ruleId = ruleId + ",";
						ruleId = ruleId.replace(rule.getId() + ",", "");
						if(ruleId.length()>0){
							ruleId = ruleId.substring(0, ruleId.length()-1);
						}
					}
				}
			}
		}
		
		info.setRuleIds(ruleId);
		if (!result) {
			info.setMarkTests(markTests);
			info.setAuditStatus(UNPASS);
			info.setAuditMark(RETEST_MARK);
		}
		return result;
	}

}
