package com.zju.helper.check;

import com.zju.model.PatientInfo;

public interface Check {

	final int PASS = 1;
	final int UNPASS = 2;
	
	final int AUTO_MARK = 1;
	final int DIFF_MARK = 2;
	final int RATIO_MARK = 3;
	final int LACK_MARK = 4;
	final int RETEST_MARK = 5;
	final int DANGER_MARK = 6;
	final int ALARM2_MARK = 7;
	final int ALARM3_MARK = 8;
	final int EXTREME_MARK = 9;
	final int BAYES_MARK = 10;
	
	final String DIFF_COLOR = ":1;";
	final String RATIO_COLOR = ":2;";
	final String RETEST_COLOR = ":4;";
	final String DANGER_COLOR = ":3;";
	final String ALARM2_COLOR = ":5;";
	final String ALARM3_COLOR = ":6;";
	final String EXTREME_COLOR = ":7;";
	
	final int DEFAULT_RULE = 0;
	final int DIFF_RULE = 1;
	final int RATIO_RULE = 2;
	final int RETEST_RULE = 3;
	final int DANGER_RULE = 4;
	final int ALARM2_RULE = 5;
	final int ALARM3_RULE = 6;
	final int EXTREME_RULE = 7;
	
	final String AUTO_AUDIT = "自动审核";
	final String MANUAL_AUDIT = "人工审核";
	
	boolean doCheck(PatientInfo info);
	
}
