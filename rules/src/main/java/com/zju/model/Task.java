package com.zju.model;

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
 * 审核任务
 */
@Entity
@Table(name = "intlab_task")
public class Task {

	// Primary Key
	private Long id;
		
	private String startBy;
	private Date startTime;
	private Date endTime;
	private int status;
	private int finishCount;
	private int sampleCount;
	private int hasResultNum;
	private String searchText;
	
	//private final static int THREAD_RUNNING = 1;
	//private final static int THREAD_FINISHED = 2;
	private final static int THREAD_STOPPED = 3;

	/*@Id
	@GeneratedValue(strategy = GenerationType.AUTO)*/
	/**
	 * 主键、自增
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TASK")
	@SequenceGenerator(name = "SEQ_TASK", sequenceName = "task_sequence", allocationSize = 1)
	@SearchableId
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * 审核开启者
	 */
	@Column
	public String getStartBy() {
		return startBy;
	}

	public void setStartBy(String startBy) {
		this.startBy = startBy;
	}

	/**
	 * 开启时间
	 */
	@Column
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * 结束时间
	 */
	@Column
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * 审核状态
	 */
	@Column
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	@Transient
	public boolean hasStopped() {
		return this.getStatus() == THREAD_STOPPED;
	}

	/**
	 * 样本数目
	 */
	@Column
	public int getSampleCount() {
		return sampleCount;
	}

	public void setSampleCount(int sampleCount) {
		this.sampleCount = sampleCount;
	}

	/**
	 * 有结果的样本数量
	 */
	@Column
	public int getHasResultNum() {
		return hasResultNum;
	}

	public void setHasResultNum(int hasResultNum) {
		this.hasResultNum = hasResultNum;
	}

	/**
	 * 已审核的样本数目
	 */
	@Column
	public int getFinishCount() {
		return finishCount;
	}

	public void setFinishCount(int finishCount) {
		this.finishCount = finishCount;
	}

	@Transient
	public Long getIdAndStatus() {
		return id * 10 + status;
	}
	
	@Transient
	public double getProValue() {
		if (this.getFinishCount() == 0 || this.getSampleCount() == 0) {
			return 0;
		} else {
			return 1.00 * this.getFinishCount() / this.getSampleCount();
		}
		
	}

	/**
	 * 审核对象
	 */
	@Column
	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
}
