package com.zju.helper.check;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.zju.drools.DroolsRunner;
import com.zju.drools.R;
import com.zju.model.Index;
import com.zju.model.PatientInfo;
import com.zju.model.Rule;
import com.zju.model.TestResult;
import com.zju.service.RuleManager;

public class DangerCheck implements DroolsCheck {

	private DroolsRunner droolsRunner = DroolsRunner.getInstance();
	private RuleManager ruleManager = null;

	public DangerCheck(RuleManager ruleManager) {
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

		Set<String> criticalContent = new HashSet<String>();
		String markTests = info.getMarkTests();
		Map<String, TestResult> trMap = new HashMap<String, TestResult>();
		for (TestResult tr : info.getResults())
			trMap.put(tr.getTestId(), tr);
		
		for (Rule rule : ruleManager.getRuleManual(ruleId)) {
			if (rule.getType() == DANGER_RULE) {
				result = false;
				List<Index> indexs = ruleManager.getUsedIndex(rule.getId());
				for (Index i : indexs) {
					if (trMap.containsKey(i.getIndexId())) {
						markTests += i.getIndexId() + DANGER_COLOR;
						TestResult tr = trMap.get(i.getIndexId());
						criticalContent.add(i.getName() + ":" + tr.getTestResult()); //标记危急值
					}
				}
			}
		}
		
		if (!result) {
			info.setMarkTests(markTests);
			info.setCriticalContent(CheckUtil.toString(criticalContent));
			info.setAuditStatus(UNPASS);
			info.setAuditMark(DANGER_MARK);
		}
		
		return result;
	}

}
