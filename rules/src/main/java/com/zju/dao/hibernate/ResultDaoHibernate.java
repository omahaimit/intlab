package com.zju.dao.hibernate;

import java.util.List;

import org.hibernate.Query;
import com.zju.dao.ResultDao;
import com.zju.model.Constants;
import com.zju.model.Result;

public class ResultDaoHibernate extends GenericDaoHibernate<Result, Long> implements ResultDao {

	public ResultDaoHibernate() {
		super(Result.class);
	}

	@SuppressWarnings("unchecked")
	public List<Result> getResults(int pageNum, String field, boolean isAsc) {
		
		//获取从pageSize * (pageNum - 1)开始的最多pageSize个结果
		String dir = "";
		if (isAsc) { dir = " asc"; } 
		else { dir = " desc"; }
		Query q = this.getSessionFactory().getCurrentSession().createQuery("from Result order by " + field + dir);
		q.setFirstResult(Constants.PAGE_SIZE * (pageNum - 1));
		q.setMaxResults(Constants.PAGE_SIZE);
		
		return q.list();
	}
	
	// 获取结果的总数
	public int getResultsCount() {
		return ((Long)this.getHibernateTemplate().iterate("select count(*) from Result").next()).intValue();
	}

	// 增加对结果按类别分类搜索
	@SuppressWarnings("unchecked")
	public List<Result> getResultsByCategory(String category, int pageNum, String field, boolean isAsc) {

		//获取从pageSize * (pageNum - 1)开始的最多pageSize个结果
		Query q = this.getSessionFactory().getCurrentSession().createQuery("from Result");
		q.setFirstResult(Constants.PAGE_SIZE * (pageNum - 1));
		q.setMaxResults(Constants.PAGE_SIZE);
		
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<Result> getResultsByName(String result) {

		List<Result> results = this.getHibernateTemplate().find("from Result where content like '%" + result + "%'");
		return results;
	}

	public Result updateResult(Result result) {
		return this.save(result);
	}

	public Result addResult(Result result) {
		return this.save(result);
	}
}
