package com.zju.webapp.util;

import java.util.List;
import java.util.Map;

public class DataResponse {

	// 需要显示的数据集
	private List<Map<String, Object>> rows;

	// 每页显示数量
	private int page = 1;

	// 数据总数
	private int records;

	// 可显示的页数
	private int total = 1;
	
	private Map<String, Object> userdata;

	public List<Map<String, Object>> getRows() {
		return rows;
	}

	public void setRows(List<Map<String, Object>> rows) {
		this.rows = rows;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRecords() {
		return records;
	}

	public void setRecords(int records) {
		this.records = records;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public Map<String, Object> getUserdata() {
		return userdata;
	}

	public void setUserdata(Map<String, Object> userdata) {
		this.userdata = userdata;
	}
}