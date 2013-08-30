package com.zju.webapp.controller.rule;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.zju.model.Rule;
import com.zju.model.User;
import com.zju.service.RuleManager;
import com.zju.service.UserManager;
import com.zju.webapp.util.CheckAllow;
import com.zju.webapp.util.UserUtil;

@Controller
@RequestMapping("/rule/view*")
public class ViewRuleController {

	private RuleManager ruleManager = null;
	private UserManager userManager = null;

	@Autowired
	public void setRuleManager(RuleManager ruleManager) {
		this.ruleManager = ruleManager;
	}

	@Autowired
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String id = request.getParameter("id");
		if (StringUtils.isEmpty(id)) {
			return null;
		}
		if (!StringUtils.isEmpty(request.getParameter("h"))) {
			request.setAttribute("history", 3);
		} else {
			request.setAttribute("history", 1);
		}

		Rule r = ruleManager.get(Long.parseLong(id));

		User user = UserUtil.getCurrentUser(request, userManager);
		request.setAttribute("canEdit", CheckAllow.allow(r, user));
		return new ModelAndView("rule/view", "rule", r);
	}
}
