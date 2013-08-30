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
 * 解释修改
 */
@Entity
@Table(name = "reasoning_modify")
public class ReasoningModify extends BaseObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Primary Key
	private Long id;
	
	private String modifyId;
	private String oldResult;
	private String newResult;
	private String modifyUser;
	private Date modifyTime;
	private String docNo;
	private String type;
	private String content;
	
	/**
	 * 主键、自增
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ReasoningModify")
	@SequenceGenerator(name = "SEQ_ReasoningModify", sequenceName = "reasoningmodify_sequence", allocationSize = 1)
	@SearchableId
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 修改的id
	 */
	@Column(name = "modify_id")
	public String getModifyId() {
		return modifyId;
	}

	public void setModifyId(String modifyId) {
		this.modifyId = modifyId;
	}

	/**
	 * 修改前的结果
	 */
	@Column(name = "old_result")
	public String getOldResult() {
		return oldResult;
	}

	public void setOldResult(String oldResult) {
		this.oldResult = oldResult;
	}

	/**
	 * 修改后的结果
	 */
	@Column(name = "new_result")
	public String getNewResult() {
		return newResult;
	}

	public void setNewResult(String newResult) {
		this.newResult = newResult;
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
	 * 修改解释的样本医嘱号
	 */
	@Column(name = "doc_no")
	public String getDocNo() {
		return docNo;
	}

	public void setDocNo(String docNo) {
		this.docNo = docNo;
	}
	
	/**
	 * 解释类型
	 */
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * 解释的原因
	 */
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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
