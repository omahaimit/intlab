package com.zju.model;

/**
 * 医院科室
 */
public class Section {

	private String ksdm;
	private String ksmc;

	/**
	 * 科室代码
	 */
	public String getKsdm() {
		return ksdm;
	}

	public void setKsdm(String ksdm) {
		this.ksdm = ksdm;
	}

	/**
	 * 科室名称
	 */
	public String getKsmc() {
		return ksmc;
	}

	public void setKsmc(String ksmc) {
		this.ksmc = ksmc;
	}

}
