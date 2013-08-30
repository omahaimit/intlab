package com.zju.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.persistence.*;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.compass.annotations.SearchableId;

/**
 * 规则
 */
@Entity
@Table(name = "intlab_rule")
public class Rule extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Primary Key
	private Long id;

	private String name;
	// private String belongToID; //多个隶属包ID用","分割
	private Set<Bag> bags = new HashSet<Bag>();
	private Set<Result> results = new HashSet<Result>();
	private Set<Item> items = new HashSet<Item>();
	// 逻辑关系式，使用知识点ID，关系用and、or
	private String relation1;
	private String relation2;
	private String relation3;
	private String relation4;
	private String bagId;
	private String resultId;
	private String itemId;
	private String relationJson;
	private double credibility = 0; // 规则可信度，0~1
	private String description;
	private int type;
	private boolean isCore = false; // 是否为核心规则
	private boolean isActivate = false; // 是否激活
	private boolean isSelfCreate = false;
	private String reReasoning;
	private User createUser;
	private Date createTime;
	private User modifyUser;
	private Date modifyTime;
	
	private int hospitalmode;	//规则使用的对象，包括门诊、病房
	private int algorithm;		//差值算法：1。差值；2.差值百分率；3.差值变化；4.差值变化率；

	/**
	 * Default constructor - creates a new instance with no values set.
	 */
	public Rule() {
	}

	/*@Id
	@GeneratedValue(strategy = GenerationType.AUTO)*/
	/**
	 * 主键、自增
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_RULE")
	@SequenceGenerator(name = "SEQ_RULE", sequenceName = "rule_sequence", allocationSize = 1)
	@SearchableId
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 规则名称
	 */
	@Column(nullable = false, length = 50, unique = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 规则的内容
	 */
	@Transient
	public String getRelation() {
		if (StringUtils.isEmpty(relation2)) {
			return relation1;
		} else {
			StringBuilder builder = new StringBuilder();
			builder.append(relation1);
			builder.append(relation2);
			if (relation3 != null) builder.append(relation3);
			if (relation4 != null) builder.append(relation4);
			return builder.toString();
		}
	}

	public void setRelation(String relation) {
		
		relation1 = relation2 = relation3 = relation4 = null;
		int length = relation.length();
		if (length <= 250) {
			this.relation1 = relation;
		} else if (length <= 500) {
			this.relation1 = relation.substring(0, 250);
			this.relation2 = relation.substring(250);
		} else if (length <= 750) {
			this.relation1 = relation.substring(0, 250);
			this.relation2 = relation.substring(250, 500);
			this.relation3 = relation.substring(500);
		} else if (length <= 1000) {
			this.relation1 = relation.substring(0, 250);
			this.relation2 = relation.substring(250, 500);
			this.relation3 = relation.substring(500, 750);
			this.relation4 = relation.substring(750);
		} else {
			//throw new Exception("关系字符过长！");
		}
	}
	
	@Column
	public String getRelation1() {
		return relation1;
	}

	public void setRelation1(String relation1) {
		this.relation1 = relation1;
	}

	@Column
	public String getRelation2() {
		return relation2;
	}

	public void setRelation2(String relation2) {
		this.relation2 = relation2;
	}

	@Column
	public String getRelation3() {
		return relation3;
	}

	public void setRelation3(String relation3) {
		this.relation3 = relation3;
	}

	@Column
	public String getRelation4() {
		return relation4;
	}

	public void setRelation4(String relation4) {
		this.relation4 = relation4;
	}

	/**
	 * 规则可信度
	 */
	@Column
	public double getCredibility() {
		return credibility;
	}

	public void setCredibility(double credibility) {
		this.credibility = credibility;
	}

	/**
	 * 规则描述
	 */
	@Column
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 是否为核心规则
	 */
	@Column(name = "is_core")
	public boolean isCore() {
		return isCore;
	}

	public void setCore(boolean isCore) {
		this.isCore = isCore;
	}
	
	@Transient
	public boolean isSelfCreate() {
		return this.isSelfCreate;
	}

	public void setSelfCreate(boolean isSelfCreate) {
		this.isSelfCreate = isSelfCreate;
	}
	
	/**
	 * 是否激活
	 */
	@Column(name = "is_activate")
	public boolean isActivate() {
		return isActivate;
	}

	public void setActivate(boolean isActivate) {
		this.isActivate = isActivate;
	}

	/**
	 * 结果的id
	 */
	@Column(name = "reasoning_id")
	public String getReReasoning() {
		return reReasoning;
	}

	public void setReReasoning(String reReasoning) {
		this.reReasoning = reReasoning;
	}
	
	/**
	 * 规则类型
	 */
	@Column
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	/**
	 * 规则创建者
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
	 * 规则创建时间
	 */
	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 规则修改者
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
	 * 规则修改时间
	 */
	@Column(name = "modify_time")
	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * 规则所在的规则包
	 */
	@ManyToMany(targetEntity = Bag.class, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinTable(
			name = "intlab_bag_rule", 
			joinColumns = { @JoinColumn(name = "rule_id", referencedColumnName = "id") }, 
			inverseJoinColumns = @JoinColumn(name = "bag_id", referencedColumnName = "id"))
	public Set<Bag> getBags() {
		return bags;
	}

	public void setBags(Set<Bag> bags) {
		this.bags = bags;
	}

	public void addBag(Bag bag) {
		this.getBags().add(bag);
	}

	/**
	 * 规则推出的结果
	 */
	@ManyToMany(targetEntity = Result.class, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinTable(
			name = "intlab_rule_result", 
			joinColumns = { @JoinColumn(name = "rule_id", referencedColumnName = "id") }, 
			inverseJoinColumns = @JoinColumn(name = "result_id", referencedColumnName = "id"))
	public Set<Result> getResults() {
		return results;
	}

	public void setResults(Set<Result> results) {
		this.results = results;
	}

	public void addResult(Result result) {
		this.getResults().add(result);
	}

	/**
	 * 规则包含的条目
	 */
	@ManyToMany(targetEntity = Item.class, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinTable(
			name = "intlab_rule_item", 
			joinColumns = { @JoinColumn(name = "rule_id", referencedColumnName = "id") }, 
			inverseJoinColumns = @JoinColumn(name = "item_id", referencedColumnName = "id"))
	public Set<Item> getItems() {
		return items;
	}

	public void setItems(Set<Item> items) {
		this.items = items;
	}

	public void addItem(Item item) {
		this.getItems().add(item);
	}
	
	/**
	 * 就医类型
	 */
	@Column(name = "hospital_mode")
	public int getHospitalmode() {
		return hospitalmode;
	}

	public void setHospitalmode(int hospitalmode) {
		this.hospitalmode = hospitalmode;
	}
	
	@Column(name = "algorithm")
	public int getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(int algorithm) {
		this.algorithm = algorithm;
	}

	@Transient
	public String getBagId() {
		if (this.bagId == null) {
			StringBuilder bagStr = new StringBuilder();
			for (Bag b : getBags()) {
				if (bagStr.length() != 0) {
					bagStr.append(',');
				}
				bagStr.append(b.getId());
			}
			return bagStr.toString();
		} else {
			return this.bagId;
		}
	}

	@Transient
	public String getBagName() {
		StringBuilder bagStr = new StringBuilder();
		for (Bag b : getBags()) {
			if (bagStr.length() != 0) {
				bagStr.append(',');
			}
			bagStr.append(b.getName());
		}
		return bagStr.toString();
	}

	public void setBagId(String bagId) {
		// System.out.println("规则包ID列表：" + bagId);
		this.bagId = bagId;
	}

	@Transient
	public String getResultId() {
		if (this.resultId == null) {
			StringBuilder resultStr = new StringBuilder();
			for (Result r : getResults()) {
				if (resultStr.length() != 0) {
					resultStr.append(',');
				}
				resultStr.append(r.getId());
			}
			return resultStr.toString();
		} else {
			return this.resultId;
		}
	}

	@Transient
	public String getResultName() {
		StringBuilder resultStr = new StringBuilder();
		for (Result r : getResults()) {
			if (resultStr.length() != 0) {
				resultStr.append(',');
			}
			resultStr.append(r.getContent());
		}
		return resultStr.toString();
	}

	public void setResultId(String resultId) {
		// System.out.println("结果ID列表：" + resultId);
		this.resultId = resultId;
	}

	@Transient
	public String getItemId() {
		if (itemId == null) {
			StringBuilder itemStr = new StringBuilder();
			for (Item i : getItems()) {
				if (itemStr.length() != 0) {
					itemStr.append(',');
				}
				itemStr.append(i.getId());
			}
			return itemStr.toString();
		} else {
			return itemId;
		}
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	@Transient
	public String getRelationJson() {
		return relationJson;
	}
	
	@Transient
	public String getTypeName() {
		String name = "";
		switch (this.getType()) {
		case 1:
			name = "差值校验";
			break;
		case 2:
			name = "比值校验";
			break;
		case 3:
			name = "复检";
			break;
		case 4:
			name = "危急";
			break;
		case 5:
			name = "二级报警";
			break;
		case 6:
			name = "三级报警";
			break;
		case 7:
			name = "极值";
			break;
		case 10:
			name = "临时";
			break;
		default:
			name = "默认";
			break;
		}
		return name;
	}
	
	@Transient
	public String getMode() {

		String mode = "";

		switch (this.getHospitalmode()) {
		case 0:
			mode = "默认";
			break;
		case 1:
			mode = "门诊";
			break;
		case 2:
			mode = "病房";
			break;
		}
		return mode;
	}
	
	@Transient
	public String getAlgorithmName() {

		String algorithm = "";

		switch (this.getAlgorithm()) {
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
		}
		return algorithm;
	}

	public void setRelationJson(String relationJson) {
		
		// 将从规则树的json数据中除去id和children以外的属性,减少数据库存储的压力
		try {
			String json = relationJson.replace("\\", "");
			JSONArray array = new JSONArray(json);
			JSONObject root = array.getJSONObject(0);
			json = removeJsonData(root).toString();
			this.setRelation(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	private JSONObject removeJsonData(JSONObject obj) throws Exception {

		String id = obj.getJSONObject("metadata").get("id").toString();

		// 保留知识点id,用于知识点列表
		if (!StringUtils.isEmpty(id) && id.startsWith("I")) {
			String index = id.substring(1);
			if (StringUtils.isEmpty(this.getItemId())) {
				this.setItemId(index);
			} else {
				this.setItemId(this.getItemId() + "," + index);
			}
		} else if (!StringUtils.isEmpty(id) && id.startsWith("R")) {
			//结果的id,用于导出规则时，添加二次推理
			String result = id.substring(1);
			if (StringUtils.isEmpty(this.getReReasoning())) {
				this.setReReasoning(result);
			} else {
				this.setReReasoning(this.getReReasoning() + "," + result);
			}
		}

		// 移除除children外其余所有属性
		Iterator keys = obj.keys();
		while (keys.hasNext()) {
			Object key = keys.next();
			if (!key.equals("children")) {
				keys.remove();
			}
		}
		obj.put("id", id);

		// 递归遍历
		if (obj.has("children")) {
			JSONArray children = obj.getJSONArray("children");
			for (int i = 0; i < children.length(); i++) {
				JSONObject child = children.getJSONObject(i);
				this.removeJsonData(child);
			}
		}
		return obj;
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
