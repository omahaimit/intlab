package com.zju.model;

/**
 * 患者
 */
public class Patient {

	private String patientId;
	private String address;
	private String phone;
	private String blh;

	/**
	 * 患者id
	 */
	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	/**
	 * 家庭住址
	 */
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 联系方式
	 */
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 病历号
	 */
	public String getBlh() {
		return blh;
	}

	public void setBlh(String blh) {
		this.blh = blh;
	}

}
