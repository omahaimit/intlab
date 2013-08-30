package com.zju.dao;

import java.util.List;
import com.zju.model.IDMap;

public interface IDMapDao extends GenericDao<IDMap, Long> {

	/**
	 *   获取某个医院的指标ID映射
	 * @param hosp
	 * @return
	 */
	List<IDMap> getIDsByHos(String hosp);
	
	/**
	 *   获取某个指标的指标ID映射
	 * @param indexID
	 * @return
	 */
	List<IDMap> getIDsByIndex(String indexID);
}
