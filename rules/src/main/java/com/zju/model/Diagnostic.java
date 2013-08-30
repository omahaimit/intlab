package com.zju.model;

/**
 * 诊断知识库映射
 */
public class Diagnostic {
	private String DIAGNOSTIC;
	private String KNOWLEDGENAME;
	
	/**
	 * 诊断名称
	 */
	public String getDIAGNOSTIC() {
		return DIAGNOSTIC;
	}
	public void setDIAGNOSTIC(String dIAGNOSTIC) {
		DIAGNOSTIC = dIAGNOSTIC;
	}
	/**
	 * 知识库对应的诊断名称
	 */
	public String getKNOWLEDGENAME() {
		return KNOWLEDGENAME;
	}
	public void setKNOWLEDGENAME(String kNOWLEDGENAME) {
		KNOWLEDGENAME = kNOWLEDGENAME;
	}
	
	

}
