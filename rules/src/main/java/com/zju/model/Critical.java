package com.zju.model;

import java.util.Date;

/**
 * 危急值处理记录
 */
public class Critical {

	private int id = 0;
	private long docId;
	private String sampleNo;
	private String blh;
	private String patientId;
	private String patientName;
	private String patientAddress;
	private String patientPhone;
	private String requester;
	private String requesterName;
	private String requesterPhone;
	private String section;
	private String infoValue;
	private Date dealTime;
	private String dealPerson;

	/**
	 * 包含危急值的样本的开单者的工号
	 */
	public String getRequester() {
		return requester;
	}

	public void setRequester(String requester) {
		this.requester = requester;
	}

	/**
	 * 包含危急值的样本的开单者的姓名
	 */
	public String getRequesterName() {
		return requesterName;
	}

	public void setRequesterName(String requesterName) {
		this.requesterName = requesterName;
	}

	/**
	 * 包含危急值的样本的开单者的电话
	 */
	public String getRequesterPhone() {
		return requesterPhone;
	}

	public void setRequesterPhone(String requesterPhone) {
		this.requesterPhone = requesterPhone;
	}

	/**
	 * 包含危急值的样本的开单者所在的科室
	 */
	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	/**
	 * 包含危急值的样本的患者Id
	 */
	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	/**
	 * 包含危急值的样本的患者姓名
	 */
	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	/**
	 * 包含危急值的样本的患者住址
	 */
	public String getPatientAddress() {
		return patientAddress;
	}

	public void setPatientAddress(String patientAddress) {
		this.patientAddress = patientAddress;
	}

	/**
	 * 包含危急值的样本的患者联系电话
	 */
	public String getPatientPhone() {
		return patientPhone;
	}

	public void setPatientPhone(String patientPhone) {
		this.patientPhone = patientPhone;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 包含危急值的样本的医嘱号
	 */
	public long getDocId() {
		return docId;
	}

	public void setDocId(long docId) {
		this.docId = docId;
	}

	/**
	 * 包含危急值的样本的危急值结果
	 */
	public String getInfoValue() {
		return infoValue;
	}

	public void setInfoValue(String infoValue) {
		this.infoValue = infoValue;
	}

	/**
	 * 包含危急值的样本的样本号
	 */
	public String getSampleNo() {
		return sampleNo;
	}

	public void setSampleNo(String sampleNo) {
		this.sampleNo = sampleNo;
	}

	/**
	 * 包含危急值的样本的病历号
	 */
	public String getBlh() {
		return blh;
	}

	public void setBlh(String blh) {
		this.blh = blh;
	}

	/**
	 * 危急值的处理时间
	 */
	public Date getDealTime() {
		return dealTime;
	}

	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}

	/**
	 * 危急值的处理人
	 */
	public String getDealPerson() {
		return dealPerson;
	}

	public void setDealPerson(String dealPerson) {
		this.dealPerson = dealPerson;
	}

}
