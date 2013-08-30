package com.zju.dao;

import java.util.List;

import com.zju.model.Index;
import com.zju.model.Rule;

public interface RuleDao extends GenericDao<Rule, Long> {

	/**
	 *  搜索一页的规则
	 * @param pageNum 页号
	 * @param field 排序字段
	 * @param isAsc 排序方向
	 * @return
	 */
	List<Rule> getRules(int pageNum, String field, boolean isAsc);
	
	/**
	 *  返回规则数
	 * @return
	 */
	int getRulesCount();
	
	/**
	 *  根据类别，搜索规则
	 * @param category  类别
	 * @return
	 */
	List<Rule> getRulesByBagID(Long bagId);
	
	/**
	 *  根据类别，分页搜索规则
	 * @param bagId 包ID
	 * @param pageNum
	 * @param field
	 * @param isAsc
	 * @return
	 */
	List<Rule> getRulesByBagID(Long bagId, int pageNum, String field, boolean isAsc);
	
	/**
	 *  该包下的规则数
	 * @param bagId
	 * @return
	 */
	int getRulesCount(Long bagId);
	
	/**
	 *   根据规则的部分名称，模糊搜索匹配的规则
	 * @param ruleName
	 * @return
	 */
	List<Rule> getRulesByName(String ruleName);
	
	/**
	 * 
	 * @param type
	 * @return
	 */
	List<Rule> getRuleByType(int type);

	List<Rule> getRuleList(String ruleIds);
	
	List<Rule> getRuleManual(String ruleIds);

	List<Rule> getRuleByTypes(String type);
	
	List<Index> getUsedIndex(long id);

	List<Rule> getDiffRule(int i, String mode);
}
