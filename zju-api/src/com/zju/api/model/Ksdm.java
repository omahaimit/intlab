package com.zju.api.model;

import java.io.Serializable;

public class Ksdm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4399738834466060287L;
	
	private String id;
	private String name;

	public Ksdm() {
	}

	public Ksdm(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
