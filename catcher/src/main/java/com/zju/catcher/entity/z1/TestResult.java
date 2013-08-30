package com.zju.catcher.entity.z1;

import java.util.Date;

public class TestResult {

	private String SAMPLENO;
	private String TESTID;
	private String TESTRESULT;
	private String RESULTFLAG;
	private int TESTSTATUS;
	private String CORRECTFLAG;
	private char SAMPLETYPE;
	private String REFLO;
	private String REFHI;
	private String DEVICEID;
	private Date MEASURETIME;
	private String OPERATOR;
	// private String OPERATORNOTES;
	private String UNIT;
	private int ISPRINT;
	private int EDITMARK;	
	// 2:适配器,3:改,5:增,7:删,0:未改动; 通过相乘可以自由组合
	// private Date EDITTIME;

	public String getSAMPLENO() {
		return SAMPLENO;
	}

	public void setSAMPLENO(String sAMPLENO) {
		SAMPLENO = sAMPLENO;
	}

	public String getTESTID() {
		return TESTID;
	}

	public void setTESTID(String tESTID) {
		TESTID = tESTID;
	}

	public String getTESTRESULT() {
		return TESTRESULT;
	}

	public void setTESTRESULT(String tESTRESULT) {
		TESTRESULT = tESTRESULT;
	}

	public String getRESULTFLAG() {
		return RESULTFLAG;
	}

	public void setRESULTFLAG(String rESULTFLAG) {
		RESULTFLAG = rESULTFLAG;
	}

	public int getTESTSTATUS() {
		return TESTSTATUS;
	}

	public void setTESTSTATUS(int tESTSTATUS) {
		TESTSTATUS = tESTSTATUS;
	}

	public String getCORRECTFLAG() {
		return CORRECTFLAG;
	}

	public void setCORRECTFLAG(String cORRECTFLAG) {
		CORRECTFLAG = cORRECTFLAG;
	}

	public char getSAMPLETYPE() {
		return SAMPLETYPE;
	}

	public void setSAMPLETYPE(char sAMPLETYPE) {
		SAMPLETYPE = sAMPLETYPE;
	}

	public String getREFLO() {
		return REFLO;
	}

	public void setREFLO(String rEFLO) {
		REFLO = rEFLO;
	}

	public String getREFHI() {
		return REFHI;
	}

	public void setREFHI(String rEFHI) {
		REFHI = rEFHI;
	}

	public String getDEVICEID() {
		return DEVICEID;
	}

	public void setDEVICEID(String dEVICEID) {
		DEVICEID = dEVICEID;
	}

	public Date getMEASURETIME() {
		return MEASURETIME;
	}

	public void setMEASURETIME(Date mEASURETIME) {
		MEASURETIME = mEASURETIME;
	}

	public String getOPERATOR() {
		return OPERATOR;
	}

	public void setOPERATOR(String oPERATOR) {
		OPERATOR = oPERATOR;
	}

	/*
	 * public String getOPERATORNOTES() { return OPERATORNOTES; }
	 * 
	 * public void setOPERATORNOTES(String oPERATORNOTES) { OPERATORNOTES = oPERATORNOTES; }
	 */

	public String getUNIT() {
		return UNIT;
	}

	public void setUNIT(String uNIT) {
		UNIT = uNIT;
	}

	public int getISPRINT() {
		return ISPRINT;
	}

	public void setISPRINT(int iSPRINT) {
		ISPRINT = iSPRINT;
	}

	public int getEDITMARK() {
		return EDITMARK;
	}

	public void setEDITMARK(int eDITMARK) {
		EDITMARK = eDITMARK;
	}

	/*
	 * public Date getEDITTIME() { return EDITTIME; }
	 * 
	 * public void setEDITTIME(Date eDITTIME) { EDITTIME = eDITTIME; }
	 */

}
