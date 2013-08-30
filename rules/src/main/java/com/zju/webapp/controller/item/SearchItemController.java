package com.zju.webapp.controller.item;

import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.zju.model.Index;
import com.zju.model.Item;
import com.zju.model.User;
import com.zju.service.IndexManager;
import com.zju.service.ItemManager;
import com.zju.service.ResultManager;
import com.zju.service.UserManager;
import com.zju.webapp.util.SampleUtil;
import com.zju.webapp.util.UserUtil;

@Controller
@RequestMapping("/item/ajax")
public class SearchItemController {

	private IndexManager indexManager = null;
	private ItemManager itemManager = null;
	private UserManager userManager = null;
	private ResultManager resultManager = null;

	@Autowired
	public void setIndexManager(IndexManager indexManager) {
		this.indexManager = indexManager;
	}
	@Autowired
	public void setItemManager(ItemManager itemManager) {
		this.itemManager = itemManager;
	}
	@Autowired
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	@Autowired
	public void setResultManager(ResultManager resultManager) {
		this.resultManager = resultManager;
	}
	
	@RequestMapping(value = "/getItem*", method = RequestMethod.GET)
	@ResponseBody
	public String search(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String id = request.getParameter("id");
		if (StringUtils.isEmpty(id)) {
			return null;
		}
		if (id.startsWith("I")) {
			id = id.substring(1);
		}
		
		Map<String, String> map = SampleUtil.getInstance().getSampleList(indexManager);
		
		Index index = indexManager.get(Long.parseLong(id));
		JSONArray array = new JSONArray();
		if (index != null) {
			for (Item item : index.getItem()) {
				String unit = item.getUnit();
				String value = item.getValue().replace("&&", "与").replace("||", "或");
				if (!StringUtils.isEmpty(unit)) {
					unit = "," + unit;
				} else {
					unit = "";
				}
				JSONObject o = new JSONObject();
				o.put("id", "I" + item.getId());
				o.put("content", index.getName()+":"+value+" ("+map.get(index.getSampleFrom())+unit+")");
				array.put(o);
			}
		}
		
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(array.toString());
		return null;
	}
	
	@RequestMapping(value = "/addItem*", method = RequestMethod.POST)
	@ResponseBody
	public String add(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Item item = new Item();
		String id = request.getParameter("id");
		if (id.startsWith("I")) {
			id = id.substring(1);
		}
		String value = request.getParameter("value");
		String unit = request.getParameter("unit");
		Index index = indexManager.get(Long.parseLong(id));
		item.setIndex(index);
		item.setValue(value);
		item.setUnit(unit);

		// 创建者信息保存
		User createUser = UserUtil.getCurrentUser(request, userManager);
		item.setCreateUser(createUser);
		item.setCreateTime(new Date());
		
		Item newItem = itemManager.addItem(item);

		Map<String, String> map = SampleUtil.getInstance().getSampleList(indexManager);
		
		JSONObject obj = new JSONObject();
		value = newItem.getValue().replace("&&", "与").replace("||", "或");
		unit = StringUtils.isEmpty(unit) ? "" : ("," + unit);
		obj.put("value", newItem.getIndex().getName()+":"+value+" ("+map.get(index.getSampleFrom())+unit+")");
		obj.put("id", "I" + newItem.getId().toString());

		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(obj.toString());
		return null;
	}
	
	@RequestMapping(value = "/deleteItem*", method = RequestMethod.GET)
	@ResponseBody
	public int delete(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String id = request.getParameter("id");
		if (StringUtils.isEmpty(id)) {
			return -1;
		}
		if (id.startsWith("I")) {
			id = id.substring(1);
			long _id = Long.parseLong(id);
			int num = itemManager.get(_id).getRules().size();
			if (num == 0) {
				itemManager.remove(_id);
				return 0;
			} else {
				return num;
			}
		} else if (id.startsWith("R")) {
			id = id.substring(1);
			long _id = Long.parseLong(id);
			int num = resultManager.get(_id).getRules().size();
			if (num == 0) {
				resultManager.remove(_id);
				return 0;
			} else {
				return num;
			}
		}
		return -1;
	}
}