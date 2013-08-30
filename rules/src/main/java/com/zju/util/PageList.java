package com.zju.util;

import java.util.ArrayList;
import java.util.List;

import org.displaytag.pagination.PaginatedList;
import org.displaytag.properties.SortOrderEnum;

import com.zju.model.Constants;

public class PageList<T> implements PaginatedList {

	private List<T> list = new ArrayList<T>();
	private int pageNumber = 1;
	private int pageSize = Constants.PAGE_SIZE;
	private int fullSize = 0;
	private String searchId; 
	private String sortCriterion; 
	private SortOrderEnum sortDirection = SortOrderEnum.ASCENDING; 

	// 构造
	public PageList() {}
	
	public PageList(int pageNumber, int fullSize) {
		this.pageNumber = pageNumber;
		this.fullSize = fullSize;
	}
	
	public PageList(int pageNumber, int fullSize, int pageSize) {
		this(pageNumber, fullSize);
		this.pageSize = pageSize;
	}
	
	// 结果的总数
	public int getFullListSize() {
		return fullSize;
	}
	public void setFullListSize(int fullSize) {
		this.fullSize = fullSize;
	}

	@SuppressWarnings("rawtypes")
	public List getList() {
		return list;
	}
	
	// 添加结果
	public void add(T bean) {
		list.add(bean);
	}
	public void setList(List<T> list) {
		this.list = list;
	}

	// 每页的最大容量
	public int getObjectsPerPage() {
		return this.pageSize;
	}
	public void setPerPage(int pageSize) {
		this.pageSize = pageSize;
	}

	// 当前的页号
	public int getPageNumber() {
		return this.pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	// 暂时不用
	public String getSearchId() {
		return this.searchId;
	}

	public String getSortCriterion() {
		return this.sortCriterion;
	}

	public SortOrderEnum getSortDirection() {
		return this.sortDirection;
	}
	
	public void setSearchId(String searchId) {
		this.searchId = searchId;
	}

	public void setSortCriterion(String sortCriterion) {
		this.sortCriterion = sortCriterion;
	}

	public void setSortDirection(SortOrderEnum sortDirection) {
		this.sortDirection = sortDirection;
	}
	
	public void setSortDirection(String dir) {   
        if (dir == null || "null".equals(dir) || "asc".equalsIgnoreCase(dir)) {   
            sortDirection = SortOrderEnum.ASCENDING;   
        } else {   
            sortDirection = SortOrderEnum.DESCENDING;   
        }   
    }
}
