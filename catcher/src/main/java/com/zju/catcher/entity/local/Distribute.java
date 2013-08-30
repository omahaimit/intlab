package com.zju.catcher.entity.local;

public class Distribute {

	private String TESTID;
	private int SCOPENO;
	private float SCOPELO;
	private float SCOPEHI;
	private int PASSCOUNT;
	private int UNPASSCOUNT;
	private int SEX;

	public void addPass() {
		PASSCOUNT += 1;
	}
	
	public void addUnpass() {
		UNPASSCOUNT += 1;
	}
	
	public boolean isIn(float value) {
		if (value > SCOPELO && value <= SCOPEHI) {
			return true;
		} else { 
			return false;
		}
	}
	
	public String getTESTID() {
		return TESTID;
	}

	public void setTESTID(String tESTID) {
		TESTID = tESTID;
	}

	public float getSCOPELO() {
		return SCOPELO;
	}

	public void setSCOPELO(float sCOPELO) {
		SCOPELO = sCOPELO;
	}

	public float getSCOPEHI() {
		return SCOPEHI;
	}

	public void setSCOPEHI(float sCOPEHI) {
		SCOPEHI = sCOPEHI;
	}

	public int getPASSCOUNT() {
		return PASSCOUNT;
	}

	public void setPASSCOUNT(int pASSCOUNT) {
		PASSCOUNT = pASSCOUNT;
	}

	public int getUNPASSCOUNT() {
		return UNPASSCOUNT;
	}

	public void setUNPASSCOUNT(int uNPASSCOUNT) {
		UNPASSCOUNT = uNPASSCOUNT;
	}

	public int getSEX() {
		return SEX;
	}

	public void setSEX(int sEX) {
		SEX = sEX;
	}

	public int getSCOPENO() {
		return SCOPENO;
	}

	public void setSCOPENO(int sCOPENO) {
		SCOPENO = sCOPENO;
	}

}
