package com.zju.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.compass.annotations.SearchableId;

/**
 * 用户权限
 */
@Entity
@Table(name = "intlab_permission")
public class Permission extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Primary Key
	private long id;

	private String item;
	private boolean bAdmin;
	private boolean bDoctor;
	private boolean bOperator;
	private boolean bPatient;
	private boolean bUser;

	/**
	 * Default constructor - creates a new instance with no values set.
	 */
	public Permission() {
	}

	/*@Id
	@GeneratedValue(strategy = GenerationType.AUTO)*/
	/**
	 * 主键、自增
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PERMISSION")
	@SequenceGenerator(name = "SEQ_PERMISSION", sequenceName = "permission_sequence", allocationSize = 1)
	@SearchableId
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	/**
	 * 用户
	 */
	@Column
	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	/**
	 * 是否为管理员
	 */
	@Column
	public boolean isbAdmin() {
		return bAdmin;
	}

	public void setbAdmin(boolean bAdmin) {
		this.bAdmin = bAdmin;
	}

	/**
	 * 是否为医生
	 */
	@Column
	public boolean isbDoctor() {
		return bDoctor;
	}

	public void setbDoctor(boolean bDoctor) {
		this.bDoctor = bDoctor;
	}

	/**
	 * 是否为审核人员
	 */
	@Column
	public boolean isbOperator() {
		return bOperator;
	}

	public void setbOperator(boolean bOperator) {
		this.bOperator = bOperator;
	}

	/**
	 * 是否为患者
	 */
	@Column
	public boolean isbPatient() {
		return bPatient;
	}

	public void setbPatient(boolean bPatient) {
		this.bPatient = bPatient;
	}

	/**
	 * 是否为普通用户
	 */
	@Column
	public boolean isbUser() {
		return bUser;
	}

	public void setbUser(boolean bUser) {
		this.bUser = bUser;
	}

	@Override
	public String toString() {
		return null;
	}

	@Override
	public boolean equals(Object o) {
		return false;
	}

	@Override
	public int hashCode() {
		return 0;
	}

}
