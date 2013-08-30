package com.zju.drools;

public class Diff {
	private String testid;
	private float reflo;
	private float refhi;
	private int algorithm;

	public String getTestid() {
		return testid;
	}

	public void setTestid(String testid) {
		this.testid = testid;
	}

	public float getReflo() {
		return reflo;
	}

	public void setReflo(float reflo) {
		this.reflo = reflo;
	}

	public float getRefhi() {
		return refhi;
	}

	public void setRefhi(float refhi) {
		this.refhi = refhi;
	}

	public int getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(int algorithm) {
		this.algorithm = algorithm;
	}

}
