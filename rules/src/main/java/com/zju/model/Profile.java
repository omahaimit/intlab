package com.zju.model;

/**
 * 检验套餐（旧）
 */
public class Profile {

	private String name;
	private String describe;
	private String deviceId;
	private String test;
	private String jyz;

	/**
	 * 套餐名称
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 套餐描述
	 */
	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	/**
	 * 仪器号
	 */
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * 包含的项目
	 */
	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}

	/**
	 * 检验者
	 */
	public String getJyz() {
		return jyz;
	}

	public void setJyz(String jyz) {
		this.jyz = jyz;
	}

}
