package com.zju.webapp.util;

import javax.servlet.http.HttpServletRequest;
import com.zju.model.User;
import com.zju.service.UserManager;

public class UserUtil {

	static public User getCurrentUser(HttpServletRequest request, UserManager userManager) {
		
		String userName = request.getRemoteUser();
		
		User user = userManager.getUserByUsername(userName);
		
		return user;
	}
}
