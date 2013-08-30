package com.zju.model;

/**
 * 计算项目
 */
public class FormulaItem {
	private int FORMULATYPE;
	private String TESTID;   
	private char SAMPLETYPE;   
	private String FORMULADESCRIBE;   
	private String FORMULA;   
	private String FORMULAITEM;   
	private int TESTNUMB;   
	private String EXCLUDEDESCRIBE;   
	private String EXCLUDEITEM;
	private int ISPRINT;
	
	/**
	 * 计算项目的类型
	 */
	public int getFORMULATYPE() {
		return FORMULATYPE;
	}
	public void setFORMULATYPE(int fORMULATYPE) {
		FORMULATYPE = fORMULATYPE;
	}
	/**
	 * 计算项目的id
	 */
	public String getTESTID() {
		return TESTID;
	}
	public void setTESTID(String tESTID) {
		TESTID = tESTID;
	}
	/**
	 * 计算项目的样本来源
	 */
	public char getSAMPLETYPE() {
		return SAMPLETYPE;
	}
	public void setSAMPLETYPE(char sAMPLETYPE) {
		SAMPLETYPE = sAMPLETYPE;
	}
	/**
	 * 计算项目描述
	 */
	public String getFORMULADESCRIBE() {
		return FORMULADESCRIBE;
	}
	public void setFORMULADESCRIBE(String fORMULADESCRIBE) {
		FORMULADESCRIBE = fORMULADESCRIBE;
	}
	/**
	 * 计算项目公式
	 */
	public String getFORMULA() {
		return FORMULA;
	}
	public void setFORMULA(String fORMULA) {
		FORMULA = fORMULA;
	}
	/**
	 * 计算项目所涉及的项目
	 */
	public String getFORMULAITEM() {
		return FORMULAITEM;
	}
	public void setFORMULAITEM(String fORMULAITEM) {
		FORMULAITEM = fORMULAITEM;
	}
	/**
	 * 计算项目所涉及的项目个数
	 */
	public int getTESTNUMB() {
		return TESTNUMB;
	}
	public void setTESTNUMB(int tESTNUMB) {
		TESTNUMB = tESTNUMB;
	}
	public String getEXCLUDEDESCRIBE() {
		return EXCLUDEDESCRIBE;
	}
	public void setEXCLUDEDESCRIBE(String eXCLUDEDESCRIBE) {
		EXCLUDEDESCRIBE = eXCLUDEDESCRIBE;
	}
	public String getEXCLUDEITEM() {
		return EXCLUDEITEM;
	}
	public void setEXCLUDEITEM(String eXCLUDEITEM) {
		EXCLUDEITEM = eXCLUDEITEM;
	}
	/**
	 * 计算项目是否需要打印
	 */
	public int getISPRINT() {
		return ISPRINT;
	}
	public void setISPRINT(int iSPRINT) {
		ISPRINT = iSPRINT;
	}
}
