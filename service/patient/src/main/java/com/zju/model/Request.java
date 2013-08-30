package com.zju.model;

import java.util.Date;

public class Request {

	private long id;
	private String patientId;
	private Date requestTime;
	private String requestContent;
	private Date respondTime;
	private String respondContent;
	private String responder;
	private int respondMethod;

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public Date getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}

	public String getRequestContent() {
		return requestContent;
	}

	public void setRequestContent(String requestContent) {
		this.requestContent = requestContent;
	}

	public Date getRespondTime() {
		return respondTime;
	}

	public void setRespondTime(Date respondTime) {
		this.respondTime = respondTime;
	}

	public String getRespondContent() {
		return respondContent;
	}

	public void setRespondContent(String respondContent) {
		this.respondContent = respondContent;
	}

	public String getResponder() {
		return responder;
	}

	public void setResponder(String responder) {
		this.responder = responder;
	}

	public int getRespondMethod() {
		return respondMethod;
	}

	public void setRespondMethod(int respondMethod) {
		this.respondMethod = respondMethod;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
