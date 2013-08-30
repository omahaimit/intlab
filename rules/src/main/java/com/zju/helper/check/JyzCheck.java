package com.zju.helper.check;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.zju.model.PatientInfo;
import com.zju.model.TestResult;
import com.zju.model.User;
import com.zju.service.SyncManager;
import com.zju.service.UserManager;
import com.zju.webapp.util.FillFieldUtil;

public class JyzCheck implements Check {

	private Map<String, String> profileJYZ = new HashMap<String, String>();
	private SyncManager syncManager = null;
	private UserManager userManager = null;
	private final String LACK_CHECKER_ERROR = "缺少检验者！";
	private final String EQUAL_CHECKER_ERROR = "相同的检验者与审核者";
	
	public JyzCheck(SyncManager syncManager, UserManager userManager) {
		this.syncManager = syncManager;
		this.userManager = userManager;
	}
	
	@Override
	public boolean doCheck(PatientInfo info) {
		
		boolean result = false;
		String profileName = info.getSampleNo().substring(8, 11);
		String deviceId = null;
		
		for (TestResult tr : info.getResults()) {
			deviceId = tr.getDeviceId();
			break;
		}
		
		String key = profileName + deviceId;
		if (profileJYZ.containsKey(key)) {
			info.setChkoper2(profileJYZ.get(key));
			result = true;
		} else {
			String jyz = FillFieldUtil.getJYZ(syncManager, profileName, deviceId);
			if (!StringUtils.isEmpty(jyz)) {
				info.setChkoper2(jyz);
				profileJYZ.put(key, jyz);
				result = true;
			}
		}
		
		if (!result) {
			// -----------------------------------
			info.setAuditStatus(UNPASS);
			info.setNotes(LACK_CHECKER_ERROR);
			// -----------------------------------
		}
		
		User user = userManager.getUserByUsername(info.getCheckOperator());
		if (user.getLastName().equals(info.getChkoper2())) {
			// -----------------------------------
			info.setAuditStatus(UNPASS);
			info.setNotes(EQUAL_CHECKER_ERROR);
			// -----------------------------------
		}
		
		return result;
	}

}
