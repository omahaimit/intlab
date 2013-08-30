package com.zju.webapp.controller.index;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.zju.model.Index;
import com.zju.model.User;
import com.zju.service.IndexManager;
import com.zju.service.UserManager;
import com.zju.webapp.util.CheckAllow;
import com.zju.webapp.util.UserUtil;

@Controller
@RequestMapping("/index/delete*")
public class DeleteIndexController {

	private IndexManager indexManager = null;
	private UserManager userManager = null;

	@Autowired
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	@Autowired
	public void setIndexManager(IndexManager indexManager) {
		this.indexManager = indexManager;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String id = request.getParameter("id");
		Index index = indexManager.get(Long.parseLong(id));
		User user = UserUtil.getCurrentUser(request, userManager);
		// 是否有访问权限
		if (!CheckAllow.allow(index, user)) {
			response.sendError(403);
			return null;
		}

		indexManager.remove(Long.parseLong(id));
		return new ModelAndView("redirect:/index/list");
	}
}
