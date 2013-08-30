package com.zju.webapp.model;

import java.io.Serializable;
import java.util.Date;

public class PatientInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1271781635964488463L;
	
	// Primary Key
	private Long id;
	private Date receivetime; 
	private int stayHospitalMode;
	private String patientId;
	private String patientName;
	private String diagnostic;
	private char sampleType;
	private String examinaim;
	private String sampleNo;
	private int resultStatus;
	private int printFlag;
	private String ruleIds;
	private String blh;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getReceivetime() {
		return receivetime;
	}

	public void setReceivetime(Date receivetime) {
		this.receivetime = receivetime;
	}

	public int getStayHospitalMode() {
		return stayHospitalMode;
	}

	public void setStayHospitalMode(int stayHospitalMode) {
		this.stayHospitalMode = stayHospitalMode;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getDiagnostic() {
		return diagnostic;
	}

	public void setDiagnostic(String diagnostic) {
		if (diagnostic != null && diagnostic.contains(" ")) {
			diagnostic = diagnostic.replaceAll(" ", "");
		}
		this.diagnostic = diagnostic;
	}

	public char getSampleType() {
		return sampleType;
	}

	public void setSampleType(char sampleType) {
		this.sampleType = sampleType;
	}

	public String getExaminaim() {
		return examinaim;
	}

	public void setExaminaim(String examinaim) {
		this.examinaim = examinaim;
	}

	public String getSampleNo() {
		return sampleNo;
	}

	public void setSampleNo(String sampleNo) {
		this.sampleNo = sampleNo;
	}

	public int getResultStatus() {
		return resultStatus;
	}

	public void setResultStatus(int resultStatus) {
		this.resultStatus = resultStatus;
	}

	public String getRuleIds() {
		return ruleIds;
	}

	public void setRuleIds(String ruleIds) {
		this.ruleIds = ruleIds;
	}

	public int getPrintFlag() {
		return printFlag;
	}

	public void setPrintFlag(int printFlag) {
		this.printFlag = printFlag;
	}

	public String getBlh() {
		return blh;
	}

	public void setBlh(String blh) {
		this.blh = blh;
	}
	
}

