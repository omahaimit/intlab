package com.zju.webapp.controller.result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.zju.model.Result;
import com.zju.model.User;
import com.zju.service.ResultManager;
import com.zju.service.UserManager;
import com.zju.webapp.util.CheckAllow;
import com.zju.webapp.util.UserUtil;

@Controller
@RequestMapping("/result/delete*")
public class DeleteResultController {

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
	
	@RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {	
		
		String idStr = request.getParameter("id");
		long id = Long.parseLong(idStr);
		Result result = resultManager.get(id);
		User user = UserUtil.getCurrentUser(request, userManager);
		// 是否有访问权限
		if (!CheckAllow.allow(result, user)) {
			response.sendError(403);
			return null;
		}
		resultManager.remove(id);
		return new ModelAndView("redirect:/result/list");
	}
}
