package com.zju.webapp.controller.rule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.zju.model.Bag;
import com.zju.model.Rule;
import com.zju.model.User;
import com.zju.service.BagManager;
import com.zju.service.RuleManager;
import com.zju.service.UserManager;
import com.zju.util.PageList;
import com.zju.webapp.util.CheckAllow;
import com.zju.webapp.util.UserUtil;

@Controller
@RequestMapping("/rule/list*")
public class RuleListController {

	private RuleManager ruleManager = null;
	private BagManager bagManager = null;
	private UserManager userManager = null;

	@Autowired
	public void setRuleManager(RuleManager ruleManager) {
		this.ruleManager = ruleManager;
	}

	@Autowired
	public void setBagManager(BagManager bagManager) {
		this.bagManager = bagManager;
	}

	@Autowired
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int pageNumber = 1;
		boolean isAll = true; // 0为所有
		String criterion = "modifyTime"; // 排序字段
		boolean isAsc = false;
		String page = request.getParameter("page");
		String bag = request.getParameter("bag");
		String sort = request.getParameter("sort");
		String dir = request.getParameter("dir");

		PageList<Rule> ruleList = null;
		if (!StringUtils.isEmpty(page)) {
			pageNumber = Integer.parseInt(page);
		}
		if (!StringUtils.isEmpty(bag) && !"0".equals(bag) && StringUtils.isNumeric(bag)) {
			isAll = false;
		}
		if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(dir)) {
			criterion = sort;
			isAsc = "asc".equals(dir) ? true : false;
		}
		
		User user = UserUtil.getCurrentUser(request, userManager);
		
		if (ruleManager != null) {
			List<Rule> rules = null;
			int totalNum = 0;
			if (isAll) {
				rules = ruleManager.getRules(pageNumber, criterion, isAsc);
				totalNum = ruleManager.getRulesCount();
			} else {
				rules = ruleManager.getRules(bag, pageNumber, criterion, isAsc);
				totalNum = ruleManager.getRulesCount(bag);
			}
			ruleList = new PageList<Rule>(pageNumber, totalNum);
			for (Rule _r : rules) {
				if (!user.getUsername().equals("admin") && _r.getCreateUser() != null && _r.getCreateUser().getId() == user.getId()) {
					_r.setSelfCreate(true);
				}
			}
			ruleList.setList(rules);
		}

		ruleList.setSortCriterion(sort);
		ruleList.setSortDirection(dir);

		// 获取当前的规则包列表
		List<Bag> bags = bagManager.getAll();
		Map<String, String> map = new HashMap<String, String>();
		for (Bag b : bags) {
			map.put(b.getId().toString(), b.getName());
		}

		
		if (CheckAllow.isAdmin(user)) {
			request.setAttribute("disabled", false);
		} else {
			request.setAttribute("disabled", true);
		}
		request.setAttribute("category", bag);
		request.setAttribute("bagList", map);
		return new ModelAndView("rule/list", "ruleList", ruleList);
	}
}
