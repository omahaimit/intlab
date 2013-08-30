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
import javax.persistence.Transient;

import org.compass.annotations.SearchableId;

/**
 * 审核踪迹，一个审核踪迹对象代表一次审核记录
 */
@Entity
@Table(name = "audittrace")
public class AuditTrace extends BaseObject implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String sampleno;
	private Date checktime;
	private String checker;
	private int type;	//审核类型； 1：自动审核，2：人工审核
	private int status;
	
	/**
	 * 主键id，自增
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TRACE")
	@SequenceGenerator(name = "SEQ_TRACE", sequenceName = "trace_sequence", allocationSize = 1)
	@SearchableId
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * 被审核样本的样本号
	 */
	@Column
	public String getSampleno() {
		return sampleno;
	}

	public void setSampleno(String sampleno) {
		this.sampleno = sampleno;
	}

	/**
	 * 审核时间
	 */
	@Column
	public Date getChecktime() {
		return checktime;
	}

	public void setChecktime(Date checktime) {
		this.checktime = checktime;
	}

	/**
	 * 审核者
	 */
	@Column
	public String getChecker() {
		return checker;
	}

	public void setChecker(String checker) {
		this.checker = checker;
	}

	/**
	 * 审核类型； 1：自动审核，2：人工审核
	 */
	@Column
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	/**
	 * 审核状态； 1：已通过，2：未通过
	 */
	@Column
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	@Transient
	public String getStatusValue() {
		String value = "";
		switch (getStatus()) {
		case -1:
			value = "无结果";
			break;
		case 0:
			value = "未审核";
			break;
		case 1:
			value = "已通过";
			break;
		case 2:
			value = "未通过";
			break;
		default:
			value = "未审核";
			break;
		}
		return value;
	}
	
	@Transient
	public String getTypeValue() {
		String value = "";
		switch (getType()) {
		case 1:
			value = "自动审核";
			break;
		case 2:
			value = "人工审核";
			break;
		}
		return value;
	}
}
