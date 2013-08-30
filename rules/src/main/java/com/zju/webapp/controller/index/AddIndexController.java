package com.zju.webapp.controller.index;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.zju.model.Index;
import com.zju.model.User;
import com.zju.service.IndexManager;
import com.zju.service.UserManager;
import com.zju.webapp.controller.BaseFormController;
import com.zju.webapp.util.SampleUtil;
import com.zju.webapp.util.UserUtil;

@Controller
@RequestMapping("/index/add*")
public class AddIndexController extends BaseFormController {

	private IndexManager indexManager = null;
	private UserManager userManager = null;
	
	@ModelAttribute
	@RequestMapping(method = RequestMethod.GET)
	public Index showForm(HttpServletRequest request, HttpServletResponse response) {
		
		Map<String, String> samples = SampleUtil.getInstance().getSampleList(indexManager);
		request.setAttribute("sampleList", samples);
		
		Map<String, String> type = new HashMap<String, String>();
		type.put("", "");
		type.put("N", "数值型");
		type.put("S", "字符型");
		type.put("E", "枚举型");
		request.setAttribute("typeList", type);
		
		Map<Integer, String> algorithm = new HashMap<Integer, String>();
		algorithm.put(1, "差值");
		algorithm.put(2, "差值百分率");
		algorithm.put(3, "差值变化");
		algorithm.put(4, "差值变化率");
		request.setAttribute("algorithmList", algorithm);
		
		return new Index();
	}

	@RequestMapping(method = RequestMethod.POST)
	public String onSubmit(Index index, BindingResult errors, HttpServletRequest request, HttpServletResponse response) throws Exception {

		User user = UserUtil.getCurrentUser(request, userManager);
		Date date = new Date();
		index.setModifyUser(user);
		index.setCreateUser(user);
		index.setModifyTime(date);	
		index.setCreateTime(date);
		
		try {
			index = indexManager.addIndex(index);
		} catch (Exception e) {}
		
		return "redirect:/index/view?id="+index.getId().toString();
	}
	
	@Autowired
	public void setIndexManager(IndexManager indexManager) {
		this.indexManager = indexManager;
	}
	@Autowired
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
}
