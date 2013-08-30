package com.zju.model;

/**
 * bayes分段
 */
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
	
	public void removePass() {
		if (PASSCOUNT > 0) {
			PASSCOUNT -= 1;
		}
	}

	public void removeUnpass() {
		if (UNPASSCOUNT > 0) {
			UNPASSCOUNT -= 1;
		}
	}

	public boolean isIn(float value) {
		if (value > SCOPELO && value <= SCOPEHI) {
			return true;
		} else {
			return false;
		}
	}

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
	 * 分段的低值
	 */
	public float getSCOPELO() {
		return SCOPELO;
	}

	public void setSCOPELO(float sCOPELO) {
		SCOPELO = sCOPELO;
	}

	/**
	 * 分段的高值
	 */
	public float getSCOPEHI() {
		return SCOPEHI;
	}

	public void setSCOPEHI(float sCOPEHI) {
		SCOPEHI = sCOPEHI;
	}

	/**
	 * 处在该分段中样本的通过数量
	 */
	public int getPASSCOUNT() {
		return PASSCOUNT;
	}

	public void setPASSCOUNT(int pASSCOUNT) {
		PASSCOUNT = pASSCOUNT;
	}

	/**
	 * 处在该分段中样本的未通过数量
	 */
	public int getUNPASSCOUNT() {
		return UNPASSCOUNT;
	}

	public void setUNPASSCOUNT(int uNPASSCOUNT) {
		UNPASSCOUNT = uNPASSCOUNT;
	}

	/**
	 * 性别
	 */
	public int getSEX() {
		return SEX;
	}

	public void setSEX(int sEX) {
		SEX = sEX;
	}

	/**
	 * 分段编号
	 */
	public int getSCOPENO() {
		return SCOPENO;
	}

	public void setSCOPENO(int sCOPENO) {
		SCOPENO = sCOPENO;
	}

}