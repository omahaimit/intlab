package com.zju.service;

import java.util.List;
import com.zju.model.Index;
import com.zju.model.Rule;

/**
 *  规则的Service接口
 * @author Winstar
 *
 */
public interface RuleManager extends GenericManager<Rule, Long> {

	/**
	 *  分页获取规则
	 * @param pageNum	页号
	 * @param field		排序字段
	 * @param isAsc		排序方式
	 * @return
	 */
	List<Rule> getRules(int pageNum, String field, boolean isAsc);
	
	/**
	 *  规则总是
	 * @return
	 */
	int getRulesCount();
	
	/**
	 *  获取包内所有规则, 不包括子包内的规则
	 * @param bagId
	 * @return
	 */
	List<Rule> getRules(String bagId);
	
	/**
	 *  分页获取包内规则
	 * @param bagId		包的ID
	 * @param pageNum	页号
	 * @param field		排序字段
	 * @param isAsc		排序方式
	 * @return
	 */
	List<Rule> getRules(String bagId, int pageNum, String field, boolean isAsc);
	
	/**
	 *  包内规则数
	 * @param bagId
	 * @return
	 */
	int getRulesCount(String bagId);
	
	/**
	 *  规则名称模糊搜索
	 * @param ruleName  规则的部分名称
	 * @return
	 */
	List<Rule> searchRule(String ruleName);

	/**
	 *  添加规则
	 * @param rule
	 * @return
	 */
	Rule addRule(Rule rule);
	
	/**
	 *  更新规则
	 * @param rule
	 * @return
	 */
	Rule updateRule(Rule rule);
	
	/**
	 *  通过规则类型获取规则
	 * @param type	规则类型
	 * @return
	 */
	List<Rule> getRuleByType(int type);

	/**
	 *  通过规则ID列表获取规则
	 * @param ruleIds	如0,1,2
	 * @return
	 */
	List<Rule> getRuleList(String ruleIds);
	
	/**
	 *  添加了session关闭
	 * @param ruleIds
	 * @return
	 */
	List<Rule> getRuleManual(String ruleIds);

	/**
	 *  获取几类规则
	 * @param type
	 * @return
	 */
	List<Rule> getRuleByTypes(String type);
	
	/**
	 * 获取规则使用的指标
	 * @param id	规则ID
	 * @return
	 */
	List<Index> getUsedIndex(long id);

	/**
	 *  获取差值规则
	 * @param i		规则类别
	 * @param mode		就诊类型
	 * @return
	 */
	List<Rule> getDiffRule(int i, String mode);
}
