package com.zju.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class Patient implements UserDetails, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7366147420555601491L;
	
	public static final int STATUS_UNSENDSMS = 0;
	public static final int STATUS_HASSENDSMS = 1;
	public static final int STATUS_HASLOGIN = 2;
	public static final int STATUS_REQUIRESMS = 3;
	public static final int STATUS_PUSHMESSAGE = 4;
		
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
	private int STATUS;
	private String LASTSAMPLENO;

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
	
	public String getSex() {
		if (XB == 1) {
			return "男";
		} else if (XB == 2) {
			return "女";
		} else {
			return "未知";
		}
	}

	public Date getCSRQ() {
		return CSRQ;
	}

	public void setCSRQ(Date cSRQ) {
		CSRQ = cSRQ;
	}
	
	public int getAge() {
		Date birthday = this.getCSRQ();
		if (birthday != null) {
			Calendar calendar = Calendar.getInstance();
			int nowYear = calendar.get(Calendar.YEAR);
			calendar.setTime(birthday);
			int birthYear = calendar.get(Calendar.YEAR);
			return nowYear - birthYear;
		} else {
			return -1;
		}
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

	public int getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(int sTATUS) {
		STATUS = sTATUS;
	}
	
	public void updateStatus() {
		if (STATUS == 0) {
			this.STATUS = 1;
		}
	}

	public String getLASTSAMPLENO() {
		return LASTSAMPLENO;
	}

	public void setLASTSAMPLENO(String lASTSAMPLENO) {
		LASTSAMPLENO = lASTSAMPLENO;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
		list.add(new UserRole());
		if ("ejianyan".equals(JZKH)) {
			list.add(new AdminRole());
		}
		return list;
	}

	@Override
	public String getPassword() {	
		if (SFZH != null && SFZH.length() > 6) {
			return SFZH.substring(SFZH.length() - 6);
		} else {
			return LXDH;
		}
	}

	@Override
	public String getUsername() {
		return JZKH;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}

class UserRole implements GrantedAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1789639676214688245L;

	@Override
	public String getAuthority() {
		return "ROLE_USER";
	}
	
}

class AdminRole implements GrantedAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1789639676214688245L;

	@Override
	public String getAuthority() {
		return "ROLE_ADMIN";
	}
	
}
