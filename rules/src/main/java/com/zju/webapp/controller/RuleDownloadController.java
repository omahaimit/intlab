package com.zju.webapp.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.util.List;

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
import com.zju.service.BagManager;
import com.zju.service.ItemManager;
import com.zju.service.ResultManager;
import com.zju.service.RuleManager;
import com.zju.webapp.util.AnalyticUtil;

@Controller
@RequestMapping("/admin/download*")
public class RuleDownloadController {

	private RuleManager ruleManager = null;
	private ItemManager itemManager = null;
	private ResultManager resultManager = null;
	private BagManager bagManager = null;

	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public void download(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String id = request.getParameter("id");
		if (!StringUtils.isEmpty(id)) {
			Bag bag = bagManager.get(Long.parseLong(id));
			if (bag == null)
				return;
			List<Rule> rules = ruleManager.getRules(bag.getId().toString());
			String fileName=new String(bag.getName().getBytes("UTF-8"),"ISO8859-1");
			response.setHeader("Content-disposition", "attachment;filename="+fileName+".txt");
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()));
			AnalyticUtil util = new AnalyticUtil(itemManager, resultManager);
			BufferedReader reader = new BufferedReader(util.getReader(rules)); 
			String line = reader.readLine();
			while (line != null) {
				writer.write(line);
				writer.newLine();
				line = reader.readLine();
			}
			writer.flush();
			reader.close();
			writer.close();
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		List<Bag> bag = bagManager.getAll();
		return new ModelAndView("admin/download", "bagList", bag);
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
}
