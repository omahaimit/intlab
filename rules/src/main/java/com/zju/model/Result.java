package com.zju.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.compass.annotations.SearchableId;

/**
 * 规则的结果
 */
@Entity
@Table(name = "intlab_result")
public class Result extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Primary Key
	private Long id;

	private String content;
	private String level;
	private String reject;
	private String category;
	private User createUser;
	private Date createTime;
	private User modifyUser;
	private Date modifyTime;
	private String percent;
	private String modifyContent;
	private Set<Rule> rules = new HashSet<Rule>(); // 使用该结果的规则

	/**
	 * Default constructor - creates a new instance with no values set.
	 */
	public Result() {
	}

	/*@Id
	@GeneratedValue(strategy = GenerationType.AUTO)*/
	/**
	 * 主键、自增
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_RESULT")
	@SequenceGenerator(name = "SEQ_RESULT", sequenceName = "result_sequence", allocationSize = 1)
	@SearchableId
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 结果内容
	 */
	@Column(nullable = false, length = 50)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 结果的等级
	 */
	@Column(length = 20, name= "result_level")
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	/**
	 * 结果的类别
	 */
	@Column(length = 20)
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * 与该结果冲突的其他结果
	 */
	@Column(length = 50)
	public String getReject() {
		return reject;
	}

	public void setReject(String reject) {
		this.reject = reject;
	}

	/**
	 * 结果创建者
	 */
	@ManyToOne
	@JoinColumn(name = "create_user_id")
	public User getCreateUser() {
		return createUser;
	}

	public void setCreateUser(User createUser) {
		this.createUser = createUser;
	}

	/**
	 * 结果创建时间
	 */
	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 结果修改者
	 */
	@ManyToOne
	@JoinColumn(name = "modify_user_id")
	public User getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(User modifyUser) {
		this.modifyUser = modifyUser;
	}

	/**
	 * 结果修改时间
	 */
	@Column(name = "modify_time")
	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	/**
	 * 结果修改内容
	 */
	@Column(name = "modify_content")
	public String getModifyContent() {
		return modifyContent;
	}

	public void setModifyContent(String modifyContent) {
		this.modifyContent = modifyContent;
	}

	/**
	 * 包含该结果的规则
	 */
	@ManyToMany(targetEntity = Rule.class, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinTable(
			name = "intlab_rule_result", 
			joinColumns = { @JoinColumn(name = "result_id", referencedColumnName = "id") }, 
			inverseJoinColumns = @JoinColumn(name = "rule_id", referencedColumnName = "id"))
	public Set<Rule> getRules() {
		return rules;
	}

	public void setRules(Set<Rule> rules) {
		this.rules = rules;
	}

	/**
	 * 推出该结果的可能性
	 */
	@Column(length = 50)
	public String getPercent() {
		return percent;
	}

	public void setPercent(String percent) {
		this.percent = percent;
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
