package com.zju.model;

import java.io.Serializable;
import java.util.Date;

public class Record implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3848513366328718778L;
	
	private String patientId;
	private int device;
	private Date loginTime;
	private String location;
	private int age;
	private int sex;

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public int getDevice() {
		return device;
	}
	
	public String getDeviceValue() {
		if (device == 1) {
			return "iPhone";
		} else if (device == 2) {
			return "Android";
		} else if (device == 3) {
			return "Windows";
		} else {
			return "其他";
		}
	}

	public void setDevice(int device) {
		this.device = device;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getSex() {
		return sex;
	}
	
	public String getSexValue() {
		if (sex == 1) {
			return "男性";
		} else if (sex == 2) {
			return "女性";
		} else {
			return "未知";
		}
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

}
