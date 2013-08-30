package com.zju.service;

import java.util.List;
import com.zju.model.Result;

/**
 *  规则的结果
 * @author Winstar
 *
 */
public interface ResultManager extends GenericManager<Result, Long> {

	List<Result> getResults(String name);
	
	List<Result> getResults(int pageNum, String field, boolean isAsc);
	
	int getResultsCount();
	
	List<Result> getResults(String category, int pageNum, String field, boolean isAsc);
	
	Result addResult(Result result);
	
	Result updateResult(Result result);
}
