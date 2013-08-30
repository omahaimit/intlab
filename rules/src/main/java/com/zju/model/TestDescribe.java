package com.zju.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 检验项目描述
 */
@Entity
@Table(name = "TESTDESCRIBE")
public class TestDescribe extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Primary Key
	private String testId;

	private String chineseName;
	private String knowledgeName;
	private int printord;
	private String sampleType;

	/**
	 * 检验项目id
	 */
	@Id
	@Column(name = "TESTID")
	public String getTestId() {
		return testId;
	}

	public void setTestId(String testId) {
		this.testId = testId;
	}

	/**
	 * 检验项目中文名称
	 */
	@Column(name = "CHINESENAME")
	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	/**
	 * 检验项目知识库名称
	 */
	@Column(name = "KNOWLEDGENAME")
	public String getKnowledgeName() {
		return knowledgeName;
	}

	public void setKnowledgeName(String knowledgeName) {
		this.knowledgeName = knowledgeName;
	}

	/**
	 * 检验项目打印顺序
	 */
	@Column(name = "PRINTORD")
	public int getPrintord() {
		return printord;
	}

	public void setPrintord(int printord) {
		this.printord = printord;
	}
	
	/**
	 * 检验项目样本来源
	 */
	@Column(name = "SAMPLETYPE")
	public String getSampleType() {
		return sampleType;
	}

	public void setSampleType(String sampleType) {
		this.sampleType = sampleType;
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
