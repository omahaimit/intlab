package com.zju.api.model;

import java.io.Serializable;

public class Patient implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6468825783813108691L;
	
	private String patientId;
	private String address;
	private String phone;
	private String blh;

	public Patient() {
	}

	public Patient(String patientId, String address, String phone, String blh) {
		this.patientId = patientId;
		this.address = address;
		this.phone = phone;
		this.blh = blh;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getBlh() {
		return blh;
	}

	public void setBlh(String blh) {
		this.blh = blh;
	}

}