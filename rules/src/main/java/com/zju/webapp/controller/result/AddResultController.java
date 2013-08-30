package com.zju.webapp.controller.result;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zju.model.Result;
import com.zju.model.User;
import com.zju.service.ResultManager;
import com.zju.service.UserManager;

@Controller
@RequestMapping("/result/ajax*")
public class AddResultController {

	private ResultManager resultManager = null;
	private UserManager userManager = null;

	@Autowired
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	@Autowired
	public void setResultManager(ResultManager resultManager) {
		this.resultManager = resultManager;
	}
	
	@RequestMapping(value = "/add*", method = RequestMethod.POST)
	@ResponseBody
	public String add(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String content = request.getParameter("content");
		String category = request.getParameter("category");
		String percent = request.getParameter("percent");
		Result result = new Result();
		result.setContent(content);
		result.setCategory(category);
		result.setPercent(percent);
		
		// 创建者信息保存
		String userName = request.getRemoteUser();
		User user = userManager.getUserByUsername(userName);
		Date now = new Date();
		result.setCreateUser(user);
		result.setCreateTime(now);
		result.setModifyUser(user);
		result.setModifyTime(now);
		
		Result newResult = resultManager.addResult(result);
		
		return newResult.getId().toString();
	}	
}
