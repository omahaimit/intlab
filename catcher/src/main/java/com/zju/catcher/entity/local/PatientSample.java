package com.zju.catcher.entity.local;

import com.zju.catcher.entity.z1.PatientInfo;

public class PatientSample {

	private PatientInfo patientInfo;
	private int auditStatus;
	private int auditMark;
	private int writeBack;

	public PatientInfo getPatientInfo() {
		return patientInfo;
	}

	public void setPatientInfo(PatientInfo patientInfo) {
		this.patientInfo = patientInfo;
	}

	public int getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(int auditStatus) {
		this.auditStatus = auditStatus;
	}

	public int getWriteBack() {
		return writeBack;
	}

	public void setWriteBack(int writeBack) {
		this.writeBack = writeBack;
	}

	public int getAuditMark() {
		return auditMark;
	}

	public void setAuditMark(int auditMark) {
		this.auditMark = auditMark;
	}

}
