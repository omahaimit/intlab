package com.zju.model;

import java.util.Date;

public class Message {

	private String sampleNo;
	private Date sendTime;
	private String patientId;
	private String telephone;
	private int hasResponse;

	public Message() {

	}

	public Message(String sampleNo, String patientId, String telephone) {
		this.sampleNo = sampleNo;
		this.sendTime = new Date();
		this.patientId = patientId;
		this.telephone = telephone;
	}

	public String getSampleNo() {
		return sampleNo;
	}

	public void setSampleNo(String sampleNo) {
		this.sampleNo = sampleNo;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public int getHasResponse() {
		return hasResponse;
	}

	public void setHasResponse(int hasResponse) {
		this.hasResponse = hasResponse;
	}

}
