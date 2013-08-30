package com.zju.webapp.controller.rule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zju.model.Index;
import com.zju.model.Item;
import com.zju.model.Library;
import com.zju.model.Rule;
import com.zju.service.IndexManager;
import com.zju.service.ItemManager;
import com.zju.service.ResultManager;
import com.zju.service.RuleManager;
import com.zju.webapp.util.SampleUtil;

@Controller
@RequestMapping("/rule/ajax")
public class RuleJSONController {

	private RuleManager ruleManager = null;
	private ResultManager resultManager = null;
	private ItemManager itemManager = null;
	private IndexManager indexManager = null;

	@RequestMapping(value = "/getRule*", method = RequestMethod.GET)
	@ResponseBody
	public String getRuleJson(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String id = request.getParameter("id");
		String json = null;

		if (!StringUtils.isEmpty(id)) {
			Rule r = ruleManager.get(Long.parseLong(id));
			try {
				json = getItemJson(r);
			} catch (Exception e) {
				e.printStackTrace();
				json = null;
			}
		}
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(json);
		return null;
	}

	@RequestMapping(value = "/testData*", method = RequestMethod.GET)
	@ResponseBody
	public String getTestData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String id = request.getParameter("id");
		Rule rule = ruleManager.get(Long.parseLong(id));
		Map<String, Index> map = new HashMap<String, Index>();
		JSONArray array = new JSONArray();
		for (Item item : rule.getItems()) {
			Index i = item.getIndex();
			if (!map.containsKey(i.getIndexId())) {
				map.put(i.getIndexId(), i);
				JSONObject obj = new JSONObject();
				obj.put("id", i.getIndexId());
				obj.put("name", i.getName());
				obj.put("unit", item.getUnit());
				obj.put("value", 0);
				array.put(obj);
			}
		}
		

		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(array.toString());
		return null;
	}
	
	@RequestMapping(value = "/activate*", method = RequestMethod.POST)
	@ResponseBody
	public boolean activate(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {
			String id = request.getParameter("id");
			String state = request.getParameter("state");		
			Rule rule = ruleManager.get(Long.parseLong(id));
			rule.setActivate(Boolean.parseBoolean(state));
			ruleManager.updateRule(rule);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private String getItemJson(Rule r) throws Exception {

		Map<String, String> sample = SampleUtil.getInstance().getSampleList(indexManager);

		// 将知识点，病人信息放入map
		HashMap<String, String> map = new HashMap<String, String>();
		for (Item i : r.getItems()) {
			String unit = i.getUnit();
			unit = StringUtils.isEmpty(unit) ? "" : ("," + unit);
			map.put("I" + i.getId().toString(),
					i.getIndex().getName() + ":" + i.getValue() + " (" + sample.get(i.getIndex().getSampleFrom())
							+ unit + ")");
		}
		List<Library> pInfo = itemManager.getPatientInfo("");
		if (pInfo != null) {
			for (Library p : pInfo) {
				map.put("P" + p.getId().toString(), p.getValue());
			}
		}

		if (StringUtils.isEmpty(r.getRelation()))
			return null;

		JSONObject json = new JSONObject(r.getRelation());

		return addJsonData(json, map).toString();
	}

	/**
	 * 递归调用，补全知识点的信息
	 * 
	 * @param obj
	 * @param map
	 *            知识点id和内容的映射表
	 * @return
	 * @throws JSONException
	 */
	private JSONObject addJsonData(JSONObject obj, HashMap<String, String> map) throws JSONException {

		if (!obj.has("id")) {
			return null;
		}

		String id = obj.getString("id");

		if ("and".equals(id)) {
			obj.put("data", "并且");
		} else if ("or".equals(id)) {
			obj.put("data", "或者");
		} else if ("not".equals(id)) {
			obj.put("data", "非");
		} else if (id.startsWith("R")) {
			obj.put("data", resultManager.get(Long.parseLong(id.substring(1))).getContent());
		} else {
			obj.put("data", map.get(id));
		}
		// 移除id，将其加到attr中
		obj.put("metadata", new JSONObject().put("id", id));
		obj.remove("id");

		if (obj.has("children")) {
			JSONArray array = obj.getJSONArray("children");
			// System.out.println(array.length());
			for (int i = 0; i < array.length(); i++) {
				JSONObject o = array.getJSONObject(i);
				this.addJsonData(o, map);
			}
		}
		return obj;
	}

	@Autowired
	public void setIndexManager(IndexManager indexManager) {
		this.indexManager = indexManager;
	}

	@Autowired
	public void setRuleManager(RuleManager ruleManager) {
		this.ruleManager = ruleManager;
	}

	@Autowired
	public void setResultManager(ResultManager resultManager) {
		this.resultManager = resultManager;
	}

	@Autowired
	public void setItemManager(ItemManager itemManager) {
		this.itemManager = itemManager;
	}
}
