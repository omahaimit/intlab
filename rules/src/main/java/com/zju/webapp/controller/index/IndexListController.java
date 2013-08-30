package com.zju.webapp.controller.index;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.zju.model.Index;
import com.zju.service.IndexManager;
import com.zju.util.PageList;
import com.zju.webapp.util.SampleUtil;

@Controller
@RequestMapping("/index/list*")
public class IndexListController {

	private IndexManager indexManager = null;

	@Autowired
	public void setIndexManager(IndexManager indexManager) {
		this.indexManager = indexManager;
	}
	
	@RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {	
	
		int pageNumber = 1;
		boolean isAll = true;	//0为所有
		boolean byName = false;
		String criterion = "id";  //排序字段
		boolean isAsc = true;
		String page = request.getParameter("page");
		String sample = request.getParameter("sample");
		String sort = request.getParameter("sort");
		String dir = request.getParameter("dir");
		String searchText = request.getParameter("text");
		
		PageList<Index> indexList = null;
		//样本列表
		Map<String, String> map = SampleUtil.getInstance().getSampleList(indexManager);

		// 查询
		if (!StringUtils.isEmpty(searchText)) {
			if (searchText.length() == 4 && StringUtils.isNumeric(searchText)) {
				//指标ID查询
				Index index = indexManager.getIndex(searchText);
				if (index != null) {
					index.setSampleFrom(map.get(index.getSampleFrom()));
					indexList = new PageList<Index>(1, 1);
					indexList.add(index);
					
					request.setAttribute("sample", sample);
					request.setAttribute("sampleList", map);
					return new ModelAndView("index/list","indexList",indexList);
				} else {
					indexList = new PageList<Index>(1, 0);
					request.setAttribute("sample", sample);
					request.setAttribute("sampleList", map);
					return new ModelAndView("index/list","indexList",indexList);
				}
			} else {
				//指标名称查询
				byName = true;
			}
		}
		
		if (!StringUtils.isEmpty(page)) {
			pageNumber = Integer.parseInt(page);
		}
		if (!StringUtils.isEmpty(sample)) {
			isAll = false;
			if (sample.equals("\\")) {
				sample = "\\\\";
			}
		}
		if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(dir)) {
			criterion = sort;
			isAsc = "asc".equals(dir) ? true : false;
		}
			
		if (indexManager != null) {
			List<Index> indexs = null;
			int totalNum = 0;
			if (byName) {
				indexs = indexManager.getIndexsByName(searchText, pageNumber, criterion, isAsc);
				totalNum = indexManager.getIndexsByNameCount(searchText);
			} else if (isAll) {
				indexs = indexManager.getIndexs(pageNumber, criterion, isAsc);
				totalNum = indexManager.getIndexsCount();
			} else {
				indexs = indexManager.getIndexs(sample, pageNumber, criterion, isAsc);
				totalNum = indexManager.getIndexsCount(sample);
			}
			for (Index i : indexs) {
				if (map.containsKey(i.getSampleFrom())) {
					i.setSampleFrom(map.get(i.getSampleFrom()));
				}
			}
			indexList = new PageList<Index>(pageNumber, totalNum);
			indexList.setList(indexs);	
		}
		indexList.setSortCriterion(sort);
		indexList.setSortDirection(dir);

		request.setAttribute("sample", sample);
		request.setAttribute("searchText", searchText);
		request.setAttribute("sampleList", map);
		return new ModelAndView("index/list","indexList",indexList);
	}
}
