package com.zju.webapp.controller.explain;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.zju.model.Role;
import com.zju.model.User;
import com.zju.service.UserManager;
import com.zju.webapp.util.UserUtil;

@Controller
@RequestMapping("/explain/skip*")
public class SkipController {

	private UserManager umr = null;
	
	@Autowired
	public void setUmr(UserManager umr) {
		this.umr = umr;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		User user = UserUtil.getCurrentUser(request, umr);
		if (user != null) {
			for (Role r : user.getRoles()) {
				if ("ROLE_DOCTOR".equals(r.getName())) {
					return new ModelAndView("redirect:/explain/doctor");
				} else if ("ROLE_OPERATOR".equals(r.getName())) {
					return new ModelAndView("redirect:/explain/audit/list");
				} else if ("ROLE_PATIENT".equals(r.getName())) {
					return new ModelAndView("redirect:/explain/patient");
				}
			}
		}
		
		return new ModelAndView("redirect:/rule/list");
	}

	

}
