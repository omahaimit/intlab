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
@RequestMapping("/result/view*")
public class ViewResultController {

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
		
		String id = request.getParameter("id");
		Result result = resultManager.get(Long.parseLong(id));
		User user = UserUtil.getCurrentUser(request, userManager);

		request.setAttribute("canEdit", CheckAllow.allow(result, user));	
		request.setAttribute("rulesList", result.getRules());
		
		return new ModelAndView("result/view","result",result);
	}
}
