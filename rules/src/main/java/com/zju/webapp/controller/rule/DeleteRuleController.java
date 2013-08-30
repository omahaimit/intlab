package com.zju.webapp.controller.rule;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
@RequestMapping("/rule/delete*")
public class DeleteRuleController {

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
		
		String idStr = request.getParameter("id");
		long id = Long.parseLong(idStr);
		
		Rule rule = ruleManager.get(id);
		User user = UserUtil.getCurrentUser(request, userManager);
		if (!CheckAllow.allow(rule, user)) {
			response.sendError(403);
			return null;
		}
			
		ruleManager.remove(id);
		return new ModelAndView("redirect:/rule/list");
	}
}
