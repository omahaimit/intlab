package com.zju.webapp.controller.rule;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zju.model.Role;
import com.zju.model.Rule;
import com.zju.model.User;
import com.zju.service.BagManager;
import com.zju.service.ItemManager;
import com.zju.service.ResultManager;
import com.zju.service.RuleManager;
import com.zju.service.UserManager;
import com.zju.webapp.util.UserUtil;

@Controller
@RequestMapping("/rule/add*")
public class AddRuleController {

	private RuleManager ruleManager = null;
	private ItemManager itemManager = null;
	private ResultManager resultManager = null;
	private BagManager bagManager = null;
	private UserManager userManager = null;
	
	@ModelAttribute
    @RequestMapping(method = RequestMethod.GET)
    public Rule showForm(HttpServletRequest request, HttpServletResponse response) {
        Rule rule = new Rule();
        rule.setActivate(false);
        rule.setCore(false);
        rule.setCredibility(0);
        
        Map<Integer, String> typeList = new HashMap<Integer, String>();
		typeList.put(0, "默认");
		typeList.put(1, "差值校验");
		typeList.put(2, "比值校验");
		typeList.put(3, "复检	");
		typeList.put(4, "危急");
		typeList.put(5, "二级报警");
		typeList.put(6, "三级报警");
		typeList.put(7, "极值");
		typeList.put(10, "临时");
		
		request.setAttribute("typeList", typeList);
		
		Map<Integer, String> algorithmList = new HashMap<Integer, String>();
		algorithmList.put(1, "差值");
		algorithmList.put(2, "差值百分率");
		algorithmList.put(3, "差值变化");
		algorithmList.put(4, "差值变化率");
		
		request.setAttribute("algorithmList", algorithmList);
		
		Map<Integer, String> hospitalModeList = new HashMap<Integer, String>();
		hospitalModeList.put(0, "默认");
		hospitalModeList.put(1, "门诊");
		hospitalModeList.put(2, "病房");
		
		request.setAttribute("hospitalModeList", hospitalModeList);
        
        return rule;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String onSubmit(Rule rule, BindingResult errors, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

		// 映射数据的保留和更改
		String itemId = rule.getItemId();
		String bagId = rule.getBagId();
		String resultId = rule.getResultId();
		
		if (!StringUtils.isEmpty(itemId)) {
			String[] items = itemId.split(",");
			for (String item : items) {
				if (StringUtils.isNumeric(item)) {
					rule.addItem(itemManager.get(Long.parseLong(item)));
				}
			}
		}
		if (!StringUtils.isEmpty(bagId)) {
			String[] bags = bagId.split(",");
			for (String bag : bags) {
				rule.addBag(bagManager.get(Long.parseLong(bag)));
			}
		}
		if (!StringUtils.isEmpty(resultId)) {
			String[] results = resultId.split(",");
			for (String result : results) {
				rule.addResult(resultManager.get(Long.parseLong(result)));
			}
		}
		
		// 创建者信息保存
		User user = UserUtil.getCurrentUser(request, userManager);
		Date now = new Date();
		rule.setCreateUser(user);
		rule.setCreateTime(now);
		rule.setModifyUser(user);
		rule.setModifyTime(now);
		for (Role role : user.getRoles()) {
			if ("ROLE_ADMIN".equals(role.getName())) {
				rule.setCore(true);
				rule.setActivate(true);
				break;
			}
		}
		
		Rule newRule = null;
		try {
			newRule = ruleManager.addRule(rule);
		} catch (Exception e) {}
		
		return "redirect:/rule/view?h=1&id="+newRule.getId().toString();
    }

    @Autowired
    public void setRuleManager(RuleManager ruleManager) {
		this.ruleManager = ruleManager;
	}
    @Autowired
	public void setItemManager(ItemManager itemManager) {
		this.itemManager = itemManager;
	}
    @Autowired
	public void setResultManager(ResultManager resultManager) {
		this.resultManager = resultManager;
	}
    @Autowired
	public void setBagManager(BagManager bagManager) {
		this.bagManager = bagManager;
	}
    @Autowired
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
}
