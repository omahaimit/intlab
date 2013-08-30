package com.zju.webapp.model;

import java.io.Serializable;
import java.util.Date;

public class Patient implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7366147420555601491L;
	
	private String JZKH;
	private int JZCS;
	private String XM;
	private int XB;
	private Date CSRQ;
	private String SFZH;
	private String LXDH;
	private Date LDRQ;
	private String YBKH;
	private String BAH;

	public String getJZKH() {
		return JZKH;
	}

	public void setJZKH(String jZKH) {
		JZKH = jZKH;
	}

	public int getJZCS() {
		return JZCS;
	}

	public void setJZCS(int jZCS) {
		JZCS = jZCS;
	}

	public String getXM() {
		return XM;
	}

	public void setXM(String xM) {
		XM = xM;
	}

	public int getXB() {
		return XB;
	}

	public void setXB(int xB) {
		XB = xB;
	}

	public Date getCSRQ() {
		return CSRQ;
	}

	public void setCSRQ(Date cSRQ) {
		CSRQ = cSRQ;
	}

	public String getSFZH() {
		return SFZH;
	}

	public void setSFZH(String sFZH) {
		SFZH = sFZH;
	}

	public String getLXDH() {
		return LXDH;
	}

	public void setLXDH(String lXDH) {
		LXDH = lXDH;
	}

	public Date getLDRQ() {
		return LDRQ;
	}

	public void setLDRQ(Date lDRQ) {
		LDRQ = lDRQ;
	}

	public String getYBKH() {
		return YBKH;
	}

	public void setYBKH(String yBKH) {
		YBKH = yBKH;
	}

	public String getBAH() {
		return BAH;
	}

	public void setBAH(String bAH) {
		BAH = bAH;
	}

}
