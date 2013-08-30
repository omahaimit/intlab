package com.zju.model;

import java.io.Serializable;

import javax.persistence.*;

import org.compass.annotations.SearchableId;

/**
 * 医院映射表
 */
@Entity
@Table(name = "intlab_id_map")
public class IDMap extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//Primary Key
	private Long id;
	
	private Index index;
	private Bag bag;
	private Long HospitalID;
	private Long Hospital;
	
	/*@Id
    @GeneratedValue(strategy = GenerationType.AUTO)*/
	/**
	 * 主键，自增
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ID_MAP")
	@SequenceGenerator(name = "SEQ_ID_MAP", sequenceName = "id_map_sequence", allocationSize = 1)
    @SearchableId
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 该医院的指标，医院和指标的对应关系一对多
	 */
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},fetch = FetchType.LAZY)
	@JoinColumn(name="index_id")
	public Index getIndex() {
		return index;
	}
	public void setIndex(Index index) {
		this.index = index;
	}

	/**
	 * 该医院的规则包，医院和规则包的对应关系一对多
	 */
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},fetch = FetchType.LAZY)
	@JoinColumn(name="bag_id")
	public Bag getBag() {
		return bag;
	}
	public void setBag(Bag bag) {
		this.bag = bag;
	}

	/**
	 * 医院
	 */
	@Column
	public Long getHospital() {
		return Hospital;
	}
	public void setHospital(Long hospital) {
		Hospital = hospital;
	}

	/**
	 * 医院id
	 */
	@Column(name = "hospital_id")
	public Long getHospitalID() {
		return HospitalID;
	}
	public void setHospitalID(Long hospitalID) {
		HospitalID = hospitalID;
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
