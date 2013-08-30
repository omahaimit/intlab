package com.zju.dao.hibernate;

import java.util.List;
import org.hibernate.Query;
import com.zju.dao.IndexDao;
import com.zju.model.Constants;
import com.zju.model.Index;
import com.zju.model.Library;

public class IndexDaoHibernate extends GenericDaoHibernate<Index, Long> implements IndexDao {
	
	public IndexDaoHibernate() {
		super(Index.class);
	}

	@SuppressWarnings("unchecked")
	public List<Index> getIndexs(int pageNum, String field, boolean isAsc) {
		
		//获取从pageSize * (pageNum - 1)开始的最多pageSize个指标
		String dir = "";
		if (isAsc) { dir = " asc"; } 
		else { dir = " desc"; }
		Query q = this.getSessionFactory().getCurrentSession().createQuery("from Index order by " + field + dir);
		q.setFirstResult(Constants.PAGE_SIZE * (pageNum - 1));
		q.setMaxResults(Constants.PAGE_SIZE);
		
		return q.list();
	}

	// 待增加疾病种类和检验专业的搜索
	@SuppressWarnings("unchecked")
	public List<Index> getIndexsByCategory(String sample, int pageNum, String field, boolean isAsc) {

		// 根据包id，获取所属包下的规则
		String dir = "";
		if (isAsc) { dir = " asc"; } 
		else { dir = " desc"; }
		String sql = "from Index where sampleFrom='" + sample + "' order by " + field + dir;
		//System.out.println(sql);
		Query q = this.getSessionFactory().getCurrentSession().createQuery(sql);
		q.setFirstResult(Constants.PAGE_SIZE * (pageNum - 1));
		q.setMaxResults(Constants.PAGE_SIZE);
		
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<Index> getIndexs(String indexName) {
		List<Index> indexs = this.getHibernateTemplate().find("from Index where name like '" + indexName + "%' order by name,sampleFrom");
		return indexs;
	}

	public int getIndexsCount() {
		return ((Long)this.getHibernateTemplate().iterate("select count(*) from Index").next()).intValue();
	}

	public int getIndexsCount(String sample) {
		return ((Long)this.getHibernateTemplate().iterate("select count(*) from Index where sampleFrom='"+sample+"'").next()).intValue();
	}
	
	@SuppressWarnings("rawtypes")
	public Index getIndex(String indexId) {
		
		List indexs = getHibernateTemplate().find("from Index where indexId=?", indexId);
        if (indexs == null || indexs.isEmpty()) {
            return null;
        } else {
            return (Index)indexs.get(0);
        }
	}

	@SuppressWarnings("unchecked")
	public List<Library> getSampleList() {
		return this.getHibernateTemplate().find("from Library where type=1");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Index> getIndexsByName(String name, int pageNum, String field, boolean isAsc) {
		
		String dir = "";
		if (isAsc) { dir = " asc"; } 
		else { dir = " desc"; }
		String sql = "from Index where name like '" + name + "%' order by " + field + dir;
		//System.out.println(sql);
		Query q = this.getSessionFactory().getCurrentSession().createQuery(sql);
		q.setFirstResult(Constants.PAGE_SIZE * (pageNum - 1));
		q.setMaxResults(Constants.PAGE_SIZE);
		
		return q.list();
	}

	@Override
	public int getIndexsByNameCount(String name) {
		return ((Long)this.getHibernateTemplate().iterate("select count(*) from Index where name like '"+name+"%'").next()).intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Library> getLibrary(int type) {
		return this.getHibernateTemplate().find("from Library where type="+type);
	}
}
