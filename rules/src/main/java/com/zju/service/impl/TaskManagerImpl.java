package com.zju.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.zju.dao.TaskDao;
import com.zju.model.Task;
import com.zju.service.TaskManager;

public class TaskManagerImpl extends GenericManagerImpl<Task, Long> implements TaskManager {

	@SuppressWarnings("unused")
	private TaskDao taskDao;

	@Autowired
	public void setTaskDao(TaskDao taskDao) {
		this.dao = taskDao;
		this.taskDao = taskDao;
	}
	
	
}
