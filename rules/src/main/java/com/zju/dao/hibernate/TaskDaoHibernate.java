package com.zju.dao.hibernate;

import com.zju.dao.TaskDao;
import com.zju.model.Task;

public class TaskDaoHibernate extends GenericDaoHibernate<Task, Long> implements TaskDao {

	public TaskDaoHibernate() {
		super(Task.class);
	}

}
