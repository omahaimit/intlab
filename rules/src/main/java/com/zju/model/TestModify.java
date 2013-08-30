package com.zju.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.compass.annotations.SearchableId;

/**
 * 检验项目修改记录
 */
@Entity
@Table(name = "test_modify")
public class TestModify extends BaseObject implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Primary Key
	private Long id;
	
	private String sampleNo;
	private String testId;
	private String oldValue;
	private String newValue;
	private Date modifyTime;
	private String modifyUser;
	private String type;
	
	/**
	 * 主键、自增
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_test_modify")
	@SequenceGenerator(name = "SEQ_test_modify", sequenceName = "test_modify_sequence", allocationSize = 1)
	@SearchableId
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 修改的样本号
	 */
	@Column(name = "sample_no")
	public String getSampleNo() {
		return sampleNo;
	}

	public void setSampleNo(String sampleNo) {
		this.sampleNo = sampleNo;
	}

	/**
	 * 修改的项目id
	 */
	@Column(name = "test_id")
	public String getTestId() {
		return testId;
	}

	public void setTestId(String testId) {
		this.testId = testId;
	}

	/**
	 * 修改前的值
	 */
	@Column(name = "old_value")
	public String getOldValue() {
		return oldValue;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	/**
	 * 修改后的值
	 */
	@Column(name = "new_value")
	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	/**
	 * 修改时间
	 */
	@Column(name = "modify_time")
	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * 修改者
	 */
	@Column(name = "modify_user")
	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}
	
	/**
	 * 修改的方式
	 */
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
