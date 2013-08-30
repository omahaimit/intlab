package com.zju.webapp.controller.result;

import java.util.Date;

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
import com.zju.webapp.controller.BaseFormController;
import com.zju.webapp.util.CheckAllow;
import com.zju.webapp.util.UserUtil;

@Controller
@RequestMapping("/result/edit*")
public class EditResultController extends BaseFormController {

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

	public EditResultController() {
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String id = request.getParameter("id");
		Result result = resultManager.get(Long.parseLong(id));
		User user = UserUtil.getCurrentUser(request, userManager);
		// 是否有访问权限
		if (!CheckAllow.allow(result, user)) {
			response.sendError(403);
			return null;
		}
		return new ModelAndView("result/edit", "result", result);
	}

	@RequestMapping(method = RequestMethod.POST)
	public String onSubmit(Result result, HttpServletRequest request, HttpServletResponse response) throws Exception {

		if (request.getParameter("cancel") != null) {
			return "redirect:/result/list";
		}
		Result old = resultManager.get(result.getId());
		String userName = request.getRemoteUser();
		User user = userManager.getUserByUsername(userName);

		// 是否有访问权限
		if (!CheckAllow.allow(old, user)) {
			response.sendError(403);
			return null;
		}
		result.setCreateUser(old.getCreateUser());
		result.setCreateTime(old.getCreateTime());
		result.setModifyTime(new Date());
		result.setModifyUser(user);
		result.setRules(old.getRules());

		resultManager.updateResult(result);
		return "redirect:/result/view?id=" + result.getId().toString();
	}
}
