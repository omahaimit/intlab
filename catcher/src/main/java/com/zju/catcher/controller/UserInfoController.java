package com.zju.catcher.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zju.catcher.entity.User;
import com.zju.catcher.service.local.UserService;

@Controller
public class UserInfoController {
	
	@Autowired
	private UserService userService = null;
	
	private static Log log = LogFactory.getLog(UserInfoController.class);

	@RequestMapping(value = "/ajax/userinfo.htm", method = RequestMethod.GET)
	@ResponseBody
	public int syncUserInfo(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<User> allUser = userService.getAllUser();
		List<String> exsitUser = userService.exsitUserList();
		Set<String> exsitSet = new HashSet<String>(exsitUser);
		int count = 0;
		ShaPasswordEncoder sha = new ShaPasswordEncoder();
		for (User user : allUser) {
			if (!exsitSet.contains(user.getDm())) {
				String hex = sha.encodePassword(user.getPwd(), null);
				user.setPwd(hex);
				userService.insertUser(user);	
				count++;
			}
		}
		log.info("新增用户" + count + "个!");
		return count;
	} 
}
