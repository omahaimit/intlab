package com.zju.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.persistence.*;

import org.compass.annotations.SearchableId;

/**
 * 检验项目
 */
@Entity
@Table(name = "intlab_index")
public class Index extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Primary Key
	private Long id;

	private String indexId;
	private String name;
	private String sampleFrom;
	private String unit;
	private String type;
	private int diffAlgo;
	private String description;
	private String dataInfo; // 历史数据统计信息
	private String enumData;
	private User createUser;
	private Date createTime;
	private User modifyUser;
	private Date modifyTime;
	private String currentHosp;
	private String currentHospId;
	private String importance;
	private Set<Item> item = new HashSet<Item>(); // 该指标的知识点列表
	private Set<IDMap> idMap = new HashSet<IDMap>(); // 医院ID映射

	/**
	 * Default constructor - creates a new instance with no values set.
	 */
	public Index() {
	}

	/**
	 * 主键、自增
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_INDEX")
	@SequenceGenerator(name = "SEQ_INDEX", sequenceName = "index_sequence", allocationSize = 1)
	@SearchableId
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 医院对应的项目id
	 */
	@Column(name = "index_id", nullable = false, length = 10, unique = true)
	public String getIndexId() {
		return indexId;
	}

	public void setIndexId(String indexId) {
		this.indexId = indexId;
	}

	/**
	 * 检验项目名称
	 */
	@Column(nullable = false, length = 50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 检验项目样本来源
	 */
	@Column(name = "sample_from", length = 20)
	public String getSampleFrom() {
		return sampleFrom;
	}

	public void setSampleFrom(String sampleFrom) {
		this.sampleFrom = sampleFrom;
	}

	/**
	 * 检验项目单位
	 */
	@Column(length = 20)
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * 检验项目类型，0：枚举型；1：数字型；2：字符型；
	 */
	@Column(length = 2)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * 检验项目的差值算法。1：差值；2：差值百分率；3：差值变化；4：差值变化率
	 */
	@Column(name = "algorithm")
	public int getDiffAlgo() {
		return diffAlgo;
	}

	public void setDiffAlgo(int diffAlgo) {
		this.diffAlgo = diffAlgo;
	}

	/**
	 * 检验项目描述
	 */
	@Column
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 数据信息；
	 */
	@Column(name = "data_info")
	public String getDataInfo() {
		return dataInfo;
	}

	public void setDataInfo(String dataInfo) {
		this.dataInfo = dataInfo;
	}

	/**
	 * 枚举数据
	 */
	@Column(name = "enum_data")
	public String getEnumData() {
		return enumData;
	}

	public void setEnumData(String enumData) {
		this.enumData = enumData;
	}

	/**
	 * 指标创建者
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
	 * 指标创建时间
	 */
	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 指标修改者
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
	 * 指标修改时间
	 */
	@Column(name = "modify_time")
	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	/**
	 * 指标重要性
	 */
	@Column
	public String getImportance() {
		return importance;
	}

	public void setImportance(String importance) {
		this.importance = importance;
	}

	/**
	 * 使用该指标的规则集合
	 */
	@OneToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY, targetEntity = Item.class, mappedBy = "index")
	public Set<Item> getItem() {
		return item;
	}

	public void setItem(Set<Item> item) {
		this.item = item;
	}

	/**
	 * 指标的医院映射
	 */
	@OneToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY, targetEntity = IDMap.class, mappedBy = "index")
	public Set<IDMap> getIdMap() {
		return idMap;
	}

	public void setIdMap(Set<IDMap> idMap) {
		this.idMap = idMap;
	}
	
	@Transient
	public int getRuleCount() {
		HashMap<Long, Rule> map = new HashMap<Long, Rule>();
		for (Item item : this.getItem()) {
			for (Rule rule : item.getRules()) {
				if (!map.containsKey(rule.getId())) {
					map.put(rule.getId(), rule);
				}
			}
		}
		return map.size();
	}
	
	@Transient
	public Map<String, String> getRules() {
		
		Map<String, String> map = new HashMap<String, String>();
		for (Item item : this.getItem()) {
			for (Rule rule : item.getRules()) {
				String id = rule.getId().toString();
				if (!map.containsKey(id)) {
					map.put(id, rule.getName());
				}
			}
		}
		return map;
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
	public String getCurrentHosp() {
		return currentHosp;
	}

	public void setCurrentHosp(String currentHosp) {
		this.currentHosp = currentHosp;
	}
	
	@Transient
	public String getCurrentHospId() {
		return currentHospId;
	}

	public void setCurrentHospId(String currentHospId) {
		this.currentHospId = currentHospId;
	}
	
	@Transient
	public String getAlgorithm() {

		String algorithm = "";

		switch (this.getDiffAlgo()) {
		case 1:
			algorithm = "差值";
			break;
		case 2:
			algorithm = "差值百分率";
			break;
		case 3:
			algorithm = "差值变化";
			break;
		case 4:
			algorithm = "差值变化率";
			break;
		default:
			algorithm = "差值百分率";
			break;
		}
		return algorithm;
	}
}
