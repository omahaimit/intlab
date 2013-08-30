package com.zju.helper.check;

import com.zju.drools.R;
import com.zju.model.PatientInfo;

public interface DroolsCheck extends Check {

	boolean doCheck(PatientInfo info, R r);
}
