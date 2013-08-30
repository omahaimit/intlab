package com.zju.webapp.controller.search;

import java.util.HashMap;
import java.util.List;
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
import com.zju.model.Library;
import com.zju.model.Result;
import com.zju.model.Rule;
import com.zju.service.IndexManager;
import com.zju.service.ResultManager;
import com.zju.service.RuleManager;

@Controller
@RequestMapping("/ajax")
public class GlobalSearchController {

	private RuleManager ruleManager = null;
	private ResultManager resultManager = null;
	private IndexManager indexManager = null;

	@RequestMapping(value = "/getInfo*", method = RequestMethod.GET)
	@ResponseBody
	public String search(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String name = request.getParameter("name");
		
		if (StringUtils.isEmpty(name)) {
			return null;
		}
		List<Rule> rules = ruleManager.searchRule(name);
		List<Index> indexs = indexManager.getIndexs(name);
		List<Result> results = resultManager.getResults(name);
		if (name.length() == 4) {
			Index i = indexManager.getIndex(name);
			if (i != null) {
				indexs.add(0, i);
			}
		}
		
		JSONArray array = new JSONArray();
		
		for (Rule rule : rules) {
			JSONObject r = new JSONObject();
			r.put("id", rule.getId());
			r.put("name", rule.getName());
			r.put("category", "R");
			array.put(r);
		}
		Map<String, String> map = new HashMap<String, String>();
		for (Library lib : indexManager.getSampleList()) {
			map.put(lib.getSign(), lib.getValue());
		}
		for (Index index : indexs) {
			String unit = index.getUnit();
			String sample = index.getSampleFrom();
			if (map.containsKey(sample)) {
				sample = map.get(index.getSampleFrom());
			}
			String indexStr = index.getName() + " (" + sample;
			if (!StringUtils.isEmpty(unit)) {
				unit = "," + unit;
			} else {
				unit = "";
			}
			indexStr += unit + ")";
			
			JSONObject i = new JSONObject();
			i.put("id", index.getId());
			i.put("name", indexStr);
			i.put("category", "I");
			array.put(i);
		}
		for (Result result : results) {
			JSONObject t = new JSONObject();
			t.put("id", result.getId());
			t.put("name", result.getContent());
			t.put("category", "T");
			array.put(t);
		}
		
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(array.toString());
		
		return null;
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
	public void setBagManager(IndexManager indexManager) {
		this.indexManager = indexManager;
	}
}
