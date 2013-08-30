package com.zju.webapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zju.model.Permission;
import com.zju.service.PermissionManager;

@Controller
@RequestMapping("/admin/roles*")
public class RoleController {

	private PermissionManager pmr;

	@Autowired
	public void setPmr(PermissionManager pmr) {
		this.pmr = pmr;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView("admin/roleList", "roleList", pmr.getAll());
	}
	
	@ResponseBody
	@RequestMapping(value = "/ajax/edit*", method = RequestMethod.GET)
	public boolean change(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String info = request.getParameter("id");
		boolean result = false;
		
		if (!StringUtils.isEmpty(info)) {
			if (info.contains(",")) {
				String[] str = info.split(",");
				if (str.length == 2 && StringUtils.isNumeric(str[0]) && StringUtils.isNumeric(str[1])) {
					long id = Long.parseLong(str[0]);
					int index = Integer.parseInt(str[1]);
					Permission p = pmr.get(id);
					result = true;
					switch (index) {
					case 1: 
						p.setbAdmin(!p.isbAdmin());
						break;
					case 2: 
						p.setbDoctor(!p.isbDoctor());
						break;
					case 3: 
						p.setbOperator(!p.isbOperator());
						break;
					case 4: 
						p.setbPatient(!p.isbPatient());
						break;
					case 5: 
						p.setbUser(!p.isbUser());
						break;
					default:
						result = false;
						break;
					}
					if (result) {
						pmr.save(p);
					}
				}
			}
		}
		
		return result;
	}
}
