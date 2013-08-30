package com.zju.model;

/**
 * 医生的联系信息
 */
public class ContactInfor {
	private Long WORKID;
	private String NAME;
	private String SECTION;
	private String PHONE;
	
	/**
	 * 医生工号
	 */
	public Long getWORKID() {
		return WORKID;
	}
	public void setWORKID(Long wORKID) {
		WORKID = wORKID;
	}
	/**
	 * 医生姓名
	 */
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	/**
	 * 医生所在的科室
	 */
	public String getSECTION() {
		return SECTION;
	}
	public void setSECTION(String sECTION) {
		SECTION = sECTION;
	}
	/**
	 * 医生电话
	 */
	public String getPHONE() {
		return PHONE;
	}
	public void setPHONE(String pHONE) {
		PHONE = pHONE;
	}
}
