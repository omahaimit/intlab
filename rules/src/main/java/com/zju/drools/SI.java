package com.zju.drools;

public class SI extends I {

	public SI(String testId, char type, String unit, String value) {
		super(testId, type, unit, value);
	}

	public SI(String testId, String result) {
		super(testId, 'c', "", "");
		this.setSv(result);
	}

	private String sv;

	public String getSv() {
		return sv;
	}

	public void setSv(String v) {
		this.sv = v;
	}
}
