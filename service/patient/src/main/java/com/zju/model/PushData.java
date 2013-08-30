package com.zju.model;

import java.io.Serializable;
import java.util.List;

import com.zju.model.Patient;
import com.zju.model.PatientInfo;
import com.zju.model.TestResult;

public class PushData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5676253743459231140L;
	
	private List<Patient> patients;
	private List<PatientInfo> infos;
	private List<TestResult> results;

	public List<Patient> getPatients() {
		return patients;
	}

	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}

	public List<PatientInfo> getInfos() {
		return infos;
	}

	public void setInfos(List<PatientInfo> infos) {
		this.infos = infos;
	}

	public List<TestResult> getResults() {
		return results;
	}

	public void setResults(List<TestResult> results) {
		this.results = results;
	}

}
