package com.zju.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 检验套餐（新）
 */
@Entity
@Table(name = "L_YLXHDESCRIBE")
public class Ylxh extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Primary Key
	private Long ylxh; // 医疗序号
	
	private String profiletest;   //检验组合
	private String ylmc;		//医疗名称
	private String ksdm;		//科室代码
	
	/**
	 * 医疗序号
	 */
	@Id
	@Column(name = "YLXH")
	public Long getYlxh() {
		return ylxh;
	}

	public void setYlxh(Long ylxh) {
		this.ylxh = ylxh;
	}

	/**
	 * 套餐所包含的项目
	 */
	@Column(name = "PROFILETEST")
	public String getProfiletest() {
		return profiletest;
	}

	public void setProfiletest(String profiletest) {
		this.profiletest = profiletest;
	}

	/**
	 * 医疗名称
	 */
	@Column(name = "YLMC")
	public String getYlmc() {
		return ylmc;
	}

	public void setYlmc(String ylmc) {
		this.ylmc = ylmc;
	}

	/**
	 * 科室代码
	 */
	@Column(name = "KSDM", length=10)
	public String getKsdm() {
		return ksdm;
	}

	public void setKsdm(String ksdm) {
		this.ksdm = ksdm;
	}

	@Override
	public String toString() {
		return null;
	}

	@Override
	public boolean equals(Object o) {
		return false;
	}

	@Override
	public int hashCode() {
		return 0;
	}

	
}
