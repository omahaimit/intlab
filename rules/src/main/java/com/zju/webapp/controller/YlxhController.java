package com.zju.webapp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zju.model.TestDescribe;
import com.zju.model.Ylxh;
import com.zju.service.TestDescribeManager;
import com.zju.service.YlxhManager;
import com.zju.webapp.util.DataResponse;

@Controller
@RequestMapping("/ylxh*")
public class YlxhController {
	
	private static Log log = LogFactory.getLog(YlxhController.class);
	private YlxhManager ylxhManager = null;
	private TestDescribeManager testDescribeManager = null;
	private Map<String, TestDescribe> idMap = new HashMap<String, TestDescribe>();
	
	@Autowired
	public void setYlxhManager(YlxhManager ylxhManager) {
		this.ylxhManager = ylxhManager;
	}
	
	@Autowired
	public void setTestDescribeManager(TestDescribeManager testDescribeManager) {
		this.testDescribeManager = testDescribeManager;
	}
	
	synchronized private void initMap() {
		List<TestDescribe> list = testDescribeManager.getAll();
		for (TestDescribe t : list) {
			idMap.put(t.getTestId(), t);
		}
	}
	
	@RequestMapping(method = {RequestMethod.GET})
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String lab = request.getParameter("lab");
		return new ModelAndView().addObject("lab", lab);
	}
	
	@RequestMapping(value = "/data*", method = RequestMethod.GET)
	@ResponseBody
	public DataResponse getData(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String pages = request.getParameter("page");
		String rows = request.getParameter("rows");
		String lab = request.getParameter("lab");
		int page = Integer.parseInt(pages);
		int row = Integer.parseInt(rows);
		
		if (idMap.size() == 0)
			initMap();

		DataResponse dataResponse = new DataResponse();
		List<Ylxh> list = ylxhManager.getTest(lab);
		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		int listSize = 0;
		if (list != null)
			listSize = list.size();
		dataResponse.setRecords(listSize);
		int x = listSize % (row == 0 ? listSize : row);
		if (x != 0) {
			x = row - x;
		}
		int totalPage = (listSize + x) / (row == 0 ? listSize : row);
		dataResponse.setPage(page);
		dataResponse.setTotal(totalPage);
		int start = row * (page - 1);
		int index = 0;
		while (index < row && (start + index) < listSize) {
			Map<String, Object> map = new HashMap<String, Object>();
			Ylxh y = list.get(start + index);
			map.put("id", y.getYlxh());
			map.put("ylmc", y.getYlmc());
			map.put("ptest", y.getProfiletest());
			dataRows.add(map);
			index++;
		}

		dataResponse.setRows(dataRows);

		response.setContentType("text/html;charset=UTF-8");
		return dataResponse;
	}
	
	@RequestMapping(value = "/test*", method = RequestMethod.GET)
	@ResponseBody
	public DataResponse getTestList(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String xhStr = request.getParameter("ylxh");
		long ylxh = Long.parseLong(xhStr);
		
		if (idMap.size() == 0)
			initMap();

		DataResponse dataResponse = new DataResponse();
		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		Ylxh y = ylxhManager.get(ylxh);
		int size = 0;
		if(y.getProfiletest()==null) {
			size = 1;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", xhStr);
			map.put("name", idMap.get(xhStr) == null ? xhStr : idMap.get(xhStr).getChineseName());
			dataRows.add(map);
		} else {
			for (String s : y.getProfiletest().split(",")) {
				if (s != null) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", s);
					map.put("name", idMap.get(s) == null ? s : idMap.get(s).getChineseName());
					dataRows.add(map);
					size++;
				}
			}
		}
		
		dataResponse.setRecords(size);
		dataResponse.setRows(dataRows);

		response.setContentType("text/html;charset=UTF-8");
		return dataResponse;
	}
	
	@RequestMapping(value = "/add*", method = RequestMethod.POST)
	@ResponseBody
	public boolean addTest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {
			String addTest = request.getParameter("add");
			long ylxh = Long.parseLong(request.getParameter("id"));
			Ylxh y = ylxhManager.get(ylxh);
			y.setProfiletest(y.getProfiletest() + addTest + ",");
			ylxhManager.save(y);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}
		return true;
	}
	
	@RequestMapping(value = "/delete*", method = RequestMethod.POST)
	@ResponseBody
	public boolean deleteTest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {
			String deleteTest = request.getParameter("del");
			long ylxh = Long.parseLong(request.getParameter("id"));
			Ylxh y = ylxhManager.get(ylxh);
			y.setProfiletest(y.getProfiletest().replace(deleteTest +",", ""));
			ylxhManager.save(y);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}
		return true;
	}

}
