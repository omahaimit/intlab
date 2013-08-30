package com.zju.webapp.util;

import com.zju.model.Index;
import com.zju.model.Result;
import com.zju.model.Role;
import com.zju.model.Rule;
import com.zju.model.User;

public class CheckAllow {

	static public boolean allow(Rule rule, User user) {

		boolean flag = false;
		for (Role role : user.getRoles()) {
			if ("ROLE_ADMIN".equals(role.getName())) {
				flag = true;
			}
		}
		if (!rule.isCore() && rule.getCreateUser() != null) {
			if (rule.getCreateUser().getId() == user.getId()) {
				flag = true;
			}
		}

		return flag;
	}
	
	static public boolean allow(Index index, User user) {

		boolean flag = false;
		for (Role role : user.getRoles()) {
			if ("ROLE_ADMIN".equals(role.getName())) {
				flag = true;
			}
		}
		if (index.getCreateUser() != null) {
			if (index.getCreateUser().getId() == user.getId()) {
				flag = true;
			}
		}

		return flag;
	}
	
	static public boolean allow(Result result, User user) {

		boolean flag = false;
		for (Role role : user.getRoles()) {
			if ("ROLE_ADMIN".equals(role.getName())) {
				flag = true;
			}
		}
		if (result.getCreateUser() != null) {
			if (result.getCreateUser().getId() == user.getId()) {
				flag = true;
			}
		}

		return flag;
	}
	
	static public boolean isAdmin(User user) {
		
		for (Role role : user.getRoles()) {
			if ("ROLE_ADMIN".equals(role.getName())) {
				return true;
			}
		}
		return false;
	}

}
