package com.zju.dao.hibernate;

import com.zju.dao.PermissionDao;
import com.zju.model.Permission;

public class PermissionDaoHibernate extends GenericDaoHibernate<Permission, Long> implements PermissionDao {

	/**
     * Constructor to create a Generics-based version using bag as the entity
     */
	public PermissionDaoHibernate() {
		super(Permission.class);
	}

}
