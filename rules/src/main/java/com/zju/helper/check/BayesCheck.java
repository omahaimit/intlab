package com.zju.helper.check;

import com.zju.model.PatientInfo;
import com.zju.service.BayesService;
import com.zju.webapp.util.BayesUtil;

public class BayesCheck implements Check {

	private static final double PASS_RATE = 0.8;
	private BayesUtil util = null;
	
	public BayesCheck(BayesService bayesService) {
		util = BayesUtil.getInstance(bayesService);
	}
	
	@Override
	public boolean doCheck(PatientInfo info) {
		collect(info);
		if (info.getAuditStatus() == PASS) {
			double rate = util.audit(info);
			// System.out.println("Rate: " + rate);
			if (rate < PASS_RATE) {
				info.setAuditMark(BAYES_MARK);
			}
		}
		return false;
	}

	public void collect(PatientInfo info) {
		util.add(info);
	}
}
