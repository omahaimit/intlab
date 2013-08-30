package com.zju.dao;

import java.util.List;
import com.zju.model.Result;

public interface ResultDao extends GenericDao<Result, Long> {

	/**
	 *  搜索一页的结果
	 * @param pageNum  	页号
	 * @param pageSize	每页显示条数 
	 * @return
	 */
	List<Result> getResults(int pageNum, String field, boolean isAsc);
	
	int getResultsCount();
	
	/**
	 *  根据类别，搜索一页结果
	 * @param category
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	List<Result> getResultsByCategory(String category, int pageNum, String field, boolean isAsc);
	
	/**
	 *   根据结果的部分内容，模糊搜索匹配的结果
	 * @param result
	 * @return
	 */
	List<Result> getResultsByName(String result);
	
	/**
	 *   更新编辑的结果
	 * @param reuslt  更新后的结果对象
	 * @return  更新成功的结果
	 */
	Result updateResult(Result result);
	
	/**
	 *   添加一条新的结果
	 * @param result
	 * @return
	 */
	Result addResult(Result result);
}
