package com.zju.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.compass.annotations.SearchableId;

/**
 * 规则条目，一个规则可以包含多个条目
 */
@Entity
@Table(name = "intlab_item")
public class Item extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Primary Key
	private Long id;

	private Index index; // 指标
	private String value; // 数值为表达式，字符和枚举使用字符串
	private String unit;	//单位
	private int isStr;
	private Set<Rule> rules = new HashSet<Rule>();
	private User createUser;
	private Date createTime;

	/**
	 * Default constructor - creates a new instance with no values set.
	 */
	public Item() {}

	/*@Id
	@GeneratedValue(strategy = GenerationType.AUTO)*/
	/**
	 * 主键、自增
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ITEM")
	@SequenceGenerator(name = "SEQ_ITEM", sequenceName = "item_sequence", allocationSize = 1)
	@SearchableId
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 是否为字符串
	 */
	@Column(name = "is_str")
	public int getIsStr() {
		return isStr;
	}

	public void setIsStr(int isStr) {
		this.isStr = isStr;
	}

	/**
	 * 条目所涉及的指标，指标与条目的关系一对多
	 */
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "index_id")
	public Index getIndex() {
		return index;
	}

	public void setIndex(Index index) {
		this.index = index;
	}
	
	/**
	 * 条目的单位
	 */
	@Column(length = 20)
	public String getUnit() {
		return unit;
	}
	
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * 使用该条目的规则，关系多对多
	 */
	@ManyToMany(targetEntity = Rule.class, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinTable(
			name = "intlab_rule_item", 
			joinColumns = { @JoinColumn(name = "item_id", referencedColumnName = "id") }, 
			inverseJoinColumns = @JoinColumn(name = "rule_id", referencedColumnName = "id"))
	public Set<Rule> getRules() {
		return rules;
	}

	public void setRules(Set<Rule> rules) {
		this.rules = rules;
	}

	/**
	 * 条目的值
	 */
	@Column(length = 50)
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * 条目的创建者
	 */
	@ManyToOne
	@JoinColumn(name = "create_user_id", nullable = true)
	public User getCreateUser() {
		return createUser;
	}

	public void setCreateUser(User createUser) {
		this.createUser = createUser;
	}

	/**
	 * 条目的创建时间
	 */
	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
