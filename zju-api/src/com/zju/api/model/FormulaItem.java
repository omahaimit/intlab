package com.zju.api.model;

import java.io.Serializable;

public class FormulaItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8235741122305305420L;
	
	private int type;
	private String testId;
	private char sampleType;
	private String describe;
	private String formula;
	private String formulaItem;
	private String excludeItem;
	private int isPrint;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTestId() {
		return testId;
	}

	public void setTestId(String testId) {
		this.testId = testId;
	}

	public char getSampleType() {
		return sampleType;
	}

	public void setSampleType(char sampleType) {
		this.sampleType = sampleType;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public String getFormulaItem() {
		return formulaItem;
	}

	public void setFormulaItem(String formulaItem) {
		this.formulaItem = formulaItem;
	}

	public String getExcludeItem() {
		return excludeItem;
	}

	public void setExcludeItem(String excludeItem) {
		this.excludeItem = excludeItem;
	}

	public int getIsPrint() {
		return isPrint;
	}

	public void setIsPrint(int isPrint) {
		this.isPrint = isPrint;
	}

}
