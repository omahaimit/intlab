package com.zju.model;

/**
 * 自动审核的编码对象，包括代码段、是否开启自动审核、起始号、结束号
 */
public class Code {
	private String labCode;
	private boolean active;
	private String lo;
	private String hi;

	/**
	 * 检验代码段
	 */
	public String getLabCode() {
		return labCode;
	}

	public void setLabCode(String labCode) {
		this.labCode = labCode;
	}

	/**
	 * 是否开启自动审核
	 */
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * 起始的样本号
	 */
	public String getLo() {
		return lo;
	}

	public void setLo(String lo) {
		this.lo = lo;
	}

	/**
	 * 结束的样本号
	 */
	public String getHi() {
		return hi;
	}

	public void setHi(String hi) {
		this.hi = hi;
	}

}