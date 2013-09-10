package com.zju.api.model;

import java.io.Serializable;

public class LabGroupInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8628908168979913125L;

	private String spNo;
	private int expectAvg;

	public LabGroupInfo() {
	}

	public LabGroupInfo(String spNo, int expectAvg) {
		this.spNo = spNo;
		this.expectAvg = expectAvg;
	}

	public String getSpNo() {
		return spNo;
	}

	public void setSpNo(String spNo) {
		this.spNo = spNo;
	}

	public int getExpectAvg() {
		return expectAvg;
	}

	public void setExpectAvg(int expectAvg) {
		this.expectAvg = expectAvg;
	}

}
