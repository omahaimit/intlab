package com.zju.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.zju.dao.PermissionDao;
import com.zju.model.Permission;
import com.zju.service.PermissionManager;

public class PermissionManagerImpl extends GenericManagerImpl<Permission, Long> implements PermissionManager {

	@SuppressWarnings("unused")
	private PermissionDao permissionDao;

	@Autowired
	public void setPermissionDao(PermissionDao permissionDao) {
		this.permissionDao = permissionDao;
		this.dao = permissionDao;
	}
}
