package com.zju.webapp.controller.result;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.zju.model.Result;
import com.zju.service.ResultManager;
import com.zju.util.PageList;

@Controller
@RequestMapping("/result/list*")
public class ResultListController {

	private ResultManager resultManager = null;

	@Autowired
	public void setResultManager(ResultManager resultManager) {
		this.resultManager = resultManager;
	}
	
	@RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {	
		int pageNumber = 1;
		String criterion = "modifyTime";  //排序字段
		boolean isAsc = false;
		String page = request.getParameter("page");
		String sort = request.getParameter("sort");
		String dir = request.getParameter("dir");
		
		if (!StringUtils.isEmpty(page)) {
			pageNumber = Integer.parseInt(page);
		}
		if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(dir)) {
			criterion = sort;
			isAsc = "asc".equals(dir) ? true : false;
		}
		
		List<Result> results = resultManager.getResults(pageNumber, criterion, isAsc);
		int totalNum = resultManager.getResultsCount();;

		PageList<Result> resultList = new PageList<Result>(pageNumber, totalNum);
		resultList.setList(results);
		resultList.setSortCriterion(sort);
		resultList.setSortDirection(dir);
		
		request.setAttribute("listparm", request.getQueryString());
		return new ModelAndView("result/list","resultList",resultList);
	}
}
