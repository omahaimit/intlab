package com.zju.drools;

public class P {
	private String id;
	private char s;
	private char l;

	public char getS() {
		return s;
	}

	public void setS(int i) {
		if (i == 1) {
			this.s = 'M';
		} else if (i == 2) {
			this.s = 'F';
		} else {
			this.s = ' ';
		}
	}

	public char getL() {
		return l;
	}

	public void setL(char l) {
		this.l = l;
	}

	public void setA(int a) {
		if (a >= 4 && a < 18) {
			setL('C');
		} else if (a >= 18) {
			setL('O');
		} else {
			setL('I');
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
