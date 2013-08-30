package com.zju.webapp.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zju.model.Bag;
import com.zju.model.Index;
import com.zju.model.User;
import com.zju.service.BagManager;
import com.zju.service.IndexManager;
import com.zju.service.UserManager;

@Controller
@RequestMapping("/usercustom")
public class UserCustomController extends BaseFormController {
	private UserManager userManager = null;
	private BagManager bagManager = null;
	private IndexManager indexManager = null;

	@Autowired
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	
	@Autowired
	public void setBagManager(BagManager bagManager) {
		this.bagManager = bagManager;
	}
	
	@Autowired
	public void setIndexManager(IndexManager indexManager) {
		this.indexManager = indexManager;
	}


	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView handlerequest(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
		User user = userManager.getUserByUsername(request.getRemoteUser());
		List<Bag> ruleList = new ArrayList<Bag>();
		List<Index> historyList = new ArrayList<Index>();
		String rulebase = user.getRulebase();
		String history = user.getHistoryList();
		
		if(rulebase!=null&&!rulebase.equals("")){
			if(rulebase.contains(",")){
				for(String s : rulebase.split(",")){
					ruleList.add(bagManager.get(Long.parseLong(s)));
				}
			}else{
				ruleList.add(bagManager.get(Long.parseLong(rulebase)));
			}
		}
		
		if(history!=null&&!history.equals("")){
			if(history.contains(",")){
				for(String s : history.split(",")){
					historyList.add(indexManager.getIndex(s));
				}
			}else{
				historyList.add(indexManager.getIndex(history));
			}
		}
		ModelAndView view = new ModelAndView();
		view.addObject("bagIdList", ruleList);
		view.addObject("indexIdList", historyList);
		return view;
	}
	
	
	@RequestMapping(value = "/data*", method = RequestMethod.GET)
	@ResponseBody
	public List<Object> getData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<String> knowledgeList = new ArrayList<String>();
		List<String> resultList = new ArrayList<String>();
		List<Object> dataList = new ArrayList<Object>();
		User user = userManager.getUserByUsername(request.getRemoteUser());
		String knowledge = user.getKnowledgebase();
		String reasoningResult = user.getReasoningResult();
		if(knowledge!=null){
			if(knowledge.contains(",")){
				for(String s : knowledge.split(",")){
					knowledgeList.add(s);
				}
			}else{
				knowledgeList.add(knowledge);
			}
		}
		
		if(reasoningResult!=null){
			if(reasoningResult.contains(",")){
				for(String s : reasoningResult.split(",")){
					resultList.add(s);
				}
			}else{
				resultList.add(reasoningResult);
			}
		}
		dataList.add(knowledgeList);
		dataList.add(resultList);
		return dataList;
	}
	
	@RequestMapping(value = "/submit*", method = RequestMethod.POST)
	@ResponseBody
	public String onSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String knowledge = request.getParameter("knowledge");
		String result = request.getParameter("result");
		String bag = request.getParameter("bag");
		String history = request.getParameter("history");
		User user = userManager.getUserByUsername(request.getRemoteUser());
		user.setKnowledgebase(knowledge);
		user.setReasoningResult(result);
		user.setRulebase(bag);
		user.setHistoryList(history);
		userManager.save(user);
		
		return "success";
	}
}
