package com.zju.helper.check;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.zju.drools.Diff;
import com.zju.drools.DroolsRunner;
import com.zju.model.PatientInfo;
import com.zju.model.TestDescribe;
import com.zju.model.TestResult;
import com.zju.service.RuleManager;
import com.zju.webapp.util.AuditUtil;

public class DiffCheck implements Check {

	private Map<String, TestDescribe> idMap = null;
	private Map<String, Diff> mDiffMap = null;
	private Map<String, Diff> bDiffMap = null;
	private Map<Long, PatientInfo> diffDataMap = null;
	private DroolsRunner droolsRunner = DroolsRunner.getInstance();

	public DiffCheck(Map<String, TestDescribe> idMap, RuleManager ruleManager,
			Map<Long, PatientInfo> diffDataMap) {
		this.idMap = idMap;
		this.mDiffMap = AuditUtil.getMDiff(ruleManager);
		this.bDiffMap = AuditUtil.getBDiff(ruleManager);
		this.diffDataMap = diffDataMap;
	}

	@Override
	public boolean doCheck(PatientInfo info) {

		boolean result = true;
		Map<String, Diff> diffMap = null;

		if (info.getStayHospitalMode() == 2) {
			diffMap = bDiffMap;
		} else {
			diffMap = mDiffMap;
		}
		
		Set<String> diffResult = new HashSet<String>();
		Map<String, TestResult> testMap = new HashMap<String, TestResult>();
		if (diffDataMap.containsKey(info.getId())) {
			System.out.println(info.getId());
			Set<TestResult> currTr = info.getResults();
			Set<TestResult> lastTr = diffDataMap.get(info.getId()).getResults();
			diffResult = droolsRunner.getDiffCheckResult(currTr, lastTr,
					diffMap);
			for (TestResult t : currTr) {
				testMap.put(t.getTestId(), t);
			}
		}

		if (diffResult.size() != 0) {
			String markTests = info.getMarkTests();
			result = false;
			Set<String> noteSet = new HashSet<String>();

			for (String diffId : diffResult) {
				TestResult test = testMap.get(diffId);
				if (test.getResultFlag().charAt(0) != 'A') {
					markTests += diffId + DIFF_COLOR;
					TestDescribe des = idMap.get(diffId);
					if (des != null)
						noteSet.add(des.getChineseName());
					else
						noteSet.add(diffId);
				}
			}
			
			if (noteSet.size() > 0) {
				String note = "差值：" + CheckUtil.toString(noteSet);
				info.setNotes(note);
				info.setAuditMark(DIFF_MARK);
				info.setAuditStatus(UNPASS);
				info.setMarkTests(markTests);
			}
		}

		return result;
	}

}
