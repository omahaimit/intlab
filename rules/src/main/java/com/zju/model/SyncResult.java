package com.zju.model;

import java.util.Date;

public class SyncResult {

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

    public String getUNIT() {
        return UNIT;
    }

    public void setUNIT(String uNIT) {
        UNIT = uNIT;
    }

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SyncResult other = (SyncResult) obj;
		if (REFHI == null) {
			if (other.REFHI != null)
				return false;
		} else if (!REFHI.equals(other.REFHI))
			return false;
		if (REFLO == null) {
			if (other.REFLO != null)
				return false;
		} else if (!REFLO.equals(other.REFLO))
			return false;
		if (RESULTFLAG == null) {
			if (other.RESULTFLAG != null)
				return false;
		} else if (!RESULTFLAG.equals(other.RESULTFLAG))
			return false;
		if (TESTRESULT == null) {
			if (other.TESTRESULT != null)
				return false;
		} else if (!TESTRESULT.equals(other.TESTRESULT))
			return false;
		return true;
	}
}
