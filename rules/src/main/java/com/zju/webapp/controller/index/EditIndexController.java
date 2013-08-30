package com.zju.webapp.controller.index;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.cxf.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.zju.model.IDMap;
import com.zju.model.Index;
import com.zju.model.User;
import com.zju.service.IndexManager;
import com.zju.service.UserManager;
import com.zju.webapp.controller.BaseFormController;
import com.zju.webapp.util.CheckAllow;
import com.zju.webapp.util.SampleUtil;
import com.zju.webapp.util.UserUtil;

@Controller
@RequestMapping("/index/edit*")
public class EditIndexController extends BaseFormController {

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

	@ModelAttribute
	@RequestMapping(method = RequestMethod.GET)
	public Index showForm(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String id = request.getParameter("id");
		if (StringUtils.isEmpty(id)) {
			return null;
		}
		Index index = indexManager.get(Long.parseLong(id));
		User user = UserUtil.getCurrentUser(request, userManager);
		// 是否有访问权限
		if (!CheckAllow.allow(index, user)) {
			response.sendError(403);
			return null;
		}

		Map<String, String> map = new HashMap<String, String>();
		for (IDMap ip : index.getIdMap()) {
			map.put(ip.getHospitalID().toString(), ip.getHospital().toString());
		}
		request.setAttribute("hospIdMap", map);

		Map<String, String> samples = SampleUtil.getInstance().getSampleList(indexManager);
		request.setAttribute("sampleList", samples);

		Map<String, String> type = new HashMap<String, String>();
		type.put("", "");
		type.put("E", "枚举型");
		type.put("N", "数值型");
		type.put("S", "字符型");
		request.setAttribute("typeList", type);

		Map<Integer, String> algorithm = new HashMap<Integer, String>();
		algorithm.put(1, "差值");
		algorithm.put(2, "差值百分率");
		algorithm.put(3, "差值变化");
		algorithm.put(4, "差值变化率");
		request.setAttribute("algorithmList", algorithm);
		
		if (index.getDiffAlgo() < 1 || index.getDiffAlgo() > 4) {
			index.setDiffAlgo(2);
		}
		return index;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String onSubmit(Index index, BindingResult errors, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Index oldIndex = indexManager.get(index.getId());
		User user = UserUtil.getCurrentUser(request, userManager);
		// 是否有访问权限
		if (!CheckAllow.allow(oldIndex, user)) {
			response.sendError(403);
			return null;
		}

		oldIndex.setName(index.getName());
		oldIndex.setIndexId(index.getIndexId());
		oldIndex.setName(index.getName());
		oldIndex.setType(index.getType());
		oldIndex.setSampleFrom(index.getSampleFrom());
		oldIndex.setDescription(index.getDescription());
		oldIndex.setUnit(index.getUnit());
		oldIndex.setEnumData(index.getEnumData());

		if (!StringUtils.isEmpty(index.getCurrentHosp()) && !StringUtils.isEmpty(index.getCurrentHospId())) {
			for (IDMap m : oldIndex.getIdMap()) {
				if (m.getHospital().toString().equals(index.getCurrentHosp())) {
					m.setHospitalID(Long.parseLong(index.getCurrentHospId()));
				}
			}
		}

		// 编辑者信息保存
		oldIndex.setModifyUser(user);
		oldIndex.setModifyTime(new Date());

		try {
			indexManager.updateIndex(oldIndex);
		} catch (Exception e) {
		}

		return "redirect:/index/view?id=" + index.getId().toString();
	}
}