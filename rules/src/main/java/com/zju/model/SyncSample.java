package com.zju.model;

public class SyncSample {

	private SyncPatient patient;
	private int auditStatus;

	public SyncSample (SyncPatient patient, int status) {
		this.patient = patient;
		this.auditStatus = status;
	}
	
	public SyncPatient getPatient() {
		return patient;
	}

	public void setPatient(SyncPatient patient) {
		this.patient = patient;
	}

	public int getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(int auditStatus) {
		this.auditStatus = auditStatus;
	}
}
