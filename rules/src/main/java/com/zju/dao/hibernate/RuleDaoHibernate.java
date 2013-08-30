package com.zju.dao.hibernate;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

import com.zju.dao.RuleDao;
import com.zju.model.Constants;
import com.zju.model.Index;
import com.zju.model.Item;
import com.zju.model.Rule;

public class RuleDaoHibernate extends GenericDaoHibernate<Rule, Long> implements RuleDao {

	
	public RuleDaoHibernate() {
		super(Rule.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Rule> getRules(int pageNum, String field, boolean isAsc) {
		
		//获取从pageSize * (pageNum - 1)开始的最多pageSize个规则
		String dir = "";
		if (isAsc) { dir = " asc"; } 
		else { dir = " desc"; }
		Query q = this.getSessionFactory().getCurrentSession().createQuery("from Rule order by " + field + dir);
		q.setFirstResult(Constants.PAGE_SIZE * (pageNum - 1));
		q.setMaxResults(Constants.PAGE_SIZE);
		
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<Rule> getRulesByBagID(Long bagId) {
		
		// 根据包id，获取所属包下的规则
		String sql = "select r from Rule r inner join r.bags b where b.id=:bagId";
		Query q = this.getSessionFactory().getCurrentSession().createQuery(sql).setLong("bagId", bagId);
		
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<Rule> getRulesByBagID(Long bagId, int pageNum, String field, boolean isAsc) {

		// 根据包id，分页获取所属包下的规则
		String dir = "";
		if (isAsc) { dir = " asc"; } 
		else {dir = " desc";}
		String sql = "select r from Rule r inner join r.bags b where b.id=" + bagId +" order by r." + field + dir;
		Query q = this.getSessionFactory().getCurrentSession().createQuery(sql);
		q.setFirstResult(Constants.PAGE_SIZE * (pageNum - 1));
		q.setMaxResults(Constants.PAGE_SIZE);
		
		return q.list();
	}
	
	public int getRulesCount() {
		return ((Long)this.getHibernateTemplate().iterate("select count(*) from Rule").next()).intValue();
	}

	public int getRulesCount(Long bagId) {
		// 根据包id，获取所属包下的规则
		String sql = "select Count(r) from Rule r inner join r.bags b where b.id=:bagId";
		Query q = this.getSessionFactory().getCurrentSession().createQuery(sql).setLong("bagId", bagId);

		return ((Long)q.uniqueResult()).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<Rule> getRulesByName(String ruleName) {
		List<Rule> rules = getHibernateTemplate().find("from Rule where name like '%" + ruleName + "%' order by name");
		return rules;
	}

	@SuppressWarnings("unchecked")
	public List<Rule> getRuleByType(int type) {	
		List<Rule> rules = getHibernateTemplate().find("from Rule where type=" + type);
		for (Rule r : rules) {
			r.getResults().size();
			r.getItems().size();
			for (Item i : r.getItems()) {
				i.getIndex().getIndexId();
			}
		}
		return rules;
	}

	@SuppressWarnings("unchecked")
	public List<Rule> getRuleList(String ruleIds) {
		List<Rule> rules = getHibernateTemplate().find("from Rule where id in (" + ruleIds +")");
		return rules;
	}
	
	@SuppressWarnings("unchecked")
	public List<Rule> getRuleManual(String ruleIds) {
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		Query query = session.createQuery("from Rule where id in (" + ruleIds +")");
		List<Rule> rules = query.list();
		//List<Rule> rules = getHibernateTemplate().find("from Rule where id in (" + ruleIds +")");
		/*for (Rule r : rules) {
			r.getResults().size();
			r.getItems().size();
			for (Item i : r.getItems()) {
				i.getIndex().getIndexId();
			}
		}*/
		session.close();
		return rules;
	}

	@SuppressWarnings("unchecked")
	public List<Rule> getRuleByTypes(String type) {
		List<Rule> rules = getHibernateTemplate().find("from Rule where type in (" + type + ")");
		for (Rule r : rules) {
			r.getResults().size();
			r.getItems().size();
		}
		return rules;
	}

	@Override
	public List<Index> getUsedIndex(long id) {
		List<Index> indexs = new ArrayList<Index>();
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		Query query = session.createQuery("from Rule where id=" + id);
		Rule rule = (Rule) query.uniqueResult();
		for (Item item : rule.getItems()) {
			Index index = item.getIndex();
			index.getName();
			indexs.add(index);
		}
		session.close();
		return indexs;
	}

	@SuppressWarnings("unchecked")
	public List<Rule> getDiffRule(int type, String mode) {
		List<Rule> rules = getHibernateTemplate().find("from Rule where type=" + type +" and hospitalmode in "+ mode);
		for (Rule r : rules) {
			r.getResults().size();
			r.getItems().size();
			for (Item i : r.getItems()) {
				i.getIndex().getIndexId();
			}
		}
		return rules;
	}
}