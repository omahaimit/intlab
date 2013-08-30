package com.zju.dao.hibernate;

import java.util.List;

import com.zju.dao.ItemDao;
import com.zju.model.Item;
import com.zju.model.Library;

public class ItemDaoHibernate extends GenericDaoHibernate<Item, Long> implements ItemDao {

	public ItemDaoHibernate() {
		super(Item.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Library> getPatientInfo(String name) {
		return this.getHibernateTemplate().find("from Library where type=2 and value like '%" + name + "%'"); 
	}

	public Item getWithIndex(Long id) {
		Item item = (Item) this.getHibernateTemplate().find("from Item where id=?", id).get(0);
		item.getIndex().getIndexId();
		return item;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Library getInfo(String sign) {
		
		List<Library> libs = this.getHibernateTemplate().find("from Library where type=2 and sign=?", sign); 
		
		if (libs == null || libs.size() == 0) {
			throw null;
		} else {
			return libs.get(0);
		}
	}
	
	@SuppressWarnings("unchecked")
	public Item exsitItem(Long indexId, String value) {
		
		List<Item> items = this.getHibernateTemplate().find("from Item where index.id=? and value=?", indexId ,value); 
		
		if (items == null || items.size() == 0) {
			return null;
		} else {
			return items.get(0);
		}
	}
}
