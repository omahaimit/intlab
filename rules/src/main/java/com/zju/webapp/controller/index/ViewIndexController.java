package com.zju.webapp.controller.index;

import java.util.Map;
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
import com.zju.webapp.util.SampleUtil;
import com.zju.webapp.util.UserUtil;

@Controller
@RequestMapping("/index/view*")
public class ViewIndexController {

	private IndexManager indexManager = null;
	private UserManager userManager = null;

	@Autowired
	public void setIndexManager(IndexManager indexManager) {
		this.indexManager = indexManager;
	}

	@Autowired
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	
	/**
	 *  指标查看页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {	
	
		String id = request.getParameter("id");
	
		Index index = indexManager.get(Long.parseLong(id));
		User user = UserUtil.getCurrentUser(request, userManager);
		
		Map<String, String> samples = SampleUtil.getInstance().getSampleList(indexManager);
		index.setSampleFrom(samples.get(index.getSampleFrom()));
		
		if ("E".equals(index.getType())) {
			index.setType("枚举型");
		} else if ("N".equals(index.getType())) {
			index.setType("数值型");
		} else {
			index.setType("字符型");
		}
	
		request.setAttribute("canEdit", CheckAllow.allow(index, user));
		request.setAttribute("rulesList", index.getRules());
		return new ModelAndView("index/view","index", index);
	}
}
