package com.zju.model;

/**
 * 检验结果标记所需的信息
 */
public class Describe {

	private String TESTID;
	private String CHINESENAME;
	private String ENGLISHAB;
	private char SAMPLETYPE;
	private String UNIT;
	private int PRINTORD;
	private int YLXH;
	private int ISPRINT;
	private String WARNLO1;
	private String WARNHI1;
	private String WARNLO2;
	private String WARNHI2;
	private String WARNLO3;
	private String WARNHI3;

	/**
	 * 检验项目id
	 */
	public String getTESTID() {
		return TESTID;
	}

	public void setTESTID(String tESTID) {
		TESTID = tESTID;
	}

	/**
	 * 检验项目名称
	 */
	public String getCHINESENAME() {
		return CHINESENAME;
	}

	public void setCHINESENAME(String cHINESENAME) {
		CHINESENAME = cHINESENAME;
	}

	/**
	 * 检验项目样本来源
	 */
	public char getSAMPLETYPE() {
		return SAMPLETYPE;
	}

	public void setSAMPLETYPE(char sAMPLETYPE) {
		SAMPLETYPE = sAMPLETYPE;
	}

	/**
	 * 检验项目单位
	 */
	public String getUNIT() {
		return UNIT;
	}

	public void setUNIT(String uNIT) {
		UNIT = uNIT;
	}

	/**
	 * 检验项目打印顺序
	 */
	public int getPRINTORD() {
		return PRINTORD;
	}

	public void setPRINTORD(int pRINTORD) {
		PRINTORD = pRINTORD;
	}

	/**
	 * 检验项目医疗序号
	 */
	public int getYLXH() {
		return YLXH;
	}

	public void setYLXH(int yLXH) {
		YLXH = yLXH;
	}

	/**
	 * 检验项目是否打印
	 */
	public int getISPRINT() {
		return ISPRINT;
	}

	public void setISPRINT(int iSPRINT) {
		ISPRINT = iSPRINT;
	}

	/**
	 * 检验项目警戒低值1
	 */
	public String getWARNLO1() {
		return WARNLO1;
	}

	public void setWARNLO1(String wARNLO1) {
		WARNLO1 = wARNLO1;
	}

	/**
	 * 检验项目警戒高值1
	 */
	public String getWARNHI1() {
		return WARNHI1;
	}

	public void setWARNHI1(String wARNHI1) {
		WARNHI1 = wARNHI1;
	}

	/**
	 * 检验项目警戒低值2
	 */
	public String getWARNLO2() {
		return WARNLO2;
	}

	public void setWARNLO2(String wARNLO2) {
		WARNLO2 = wARNLO2;
	}

	/**
	 * 检验项目警戒高值2
	 */
	public String getWARNHI2() {
		return WARNHI2;
	}

	public void setWARNHI2(String wARNHI2) {
		WARNHI2 = wARNHI2;
	}

	/**
	 * 检验项目警戒低值3
	 */
	public String getWARNLO3() {
		return WARNLO3;
	}

	public void setWARNLO3(String wARNLO3) {
		WARNLO3 = wARNLO3;
	}

	/**
	 * 检验项目警戒高值3
	 */
	public String getWARNHI3() {
		return WARNHI3;
	}

	public void setWARNHI3(String wARNHI3) {
		WARNHI3 = wARNHI3;
	}

	/**
	 * 检验项目英文缩写
	 */
	public String getENGLISHAB() {
		return ENGLISHAB;
	}

	public void setENGLISHAB(String eNGLISHAB) {
		ENGLISHAB = eNGLISHAB;
	}
}
