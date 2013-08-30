package com.zju.catcher.service.local;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zju.catcher.dao.local.UserDao;
import com.zju.catcher.entity.local.Distribute;
import com.zju.catcher.entity.User;

@Service
public class UserServiceManager implements UserService {

	@Autowired
	private UserDao userDao;

	public List<String> exsitUserList() {
		return userDao.exsitUserList();
	}
	
	public void insertUser(User user) {
		userDao.insertUser(user);
	}

	public List<User> getAllUser() {
		return userDao.getAllUser();
	}
	
	public void saveDistribute(List<Distribute> list) {
		userDao.saveDistribute(list);
	}

	public List<Distribute> getDistribute(String testId) {
		return userDao.getDistribute(testId);
	}

	public void updateDistribute(List<Distribute> list) {
		userDao.updateDistribute(list);
	}
}
