package com.zju.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.zju.dao.InvalidSamplesDao;
import com.zju.dao.PatientsDao;
import com.zju.dao.UserDao;
import com.zju.model.InvalidSamples;
import com.zju.model.LabelValue;
import com.zju.model.User;
import com.zju.service.InvalidSamplesManager;

public class InvalidSamplesManagerImpl implements InvalidSamplesManager {
	
	InvalidSamplesDao invalidSamplesDao;
	PatientsDao patientsDao;
	UserDao userDao;

	@Autowired
	public void setPatientsDao(PatientsDao patientsDao) {
		this.patientsDao = patientsDao;
	}
	
	@Autowired
	public void setInvalidSamplesDao(InvalidSamplesDao invalidSamplesDao) {
		this.invalidSamplesDao = invalidSamplesDao;
	}

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public InvalidSamples getSample(String barCode) {
		InvalidSamples invalidSamples = new InvalidSamples();
		if (invalidSamplesDao.exists(barCode)) {
			invalidSamples = invalidSamplesDao.get(barCode);
		}
		invalidSamples.setBarCode(barCode);
		invalidSamples.setPatient(patientsDao.get(Long.parseLong(barCode)));

		return invalidSamples;
	}

	public boolean barCodeExist(String barCode) {

		if (patientsDao.exists(Long.parseLong(barCode))) {
			return true;
		}
		return false;
	}

	public List<InvalidSamples> getList(String username, String fromdate, String todate, String labdepartment)
			throws Exception {
		User user = userDao.findByUsername(username);
		List<InvalidSamples> returnList = new ArrayList<InvalidSamples>();
		Date today = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String date = formatter.format(today);
		if (fromdate == "" || todate == "") {
			if (user.getRoleList().contains(new LabelValue("ROLE_ADMIN", "ROLE_ADMIN"))) {
				returnList = invalidSamplesDao.findByParamter("", date, date, labdepartment);
			} else {
				returnList = invalidSamplesDao.findByParamter(username, date, date, labdepartment);
			}
		} else {
			if (user.getRoleList().contains(new LabelValue("ROLE_ADMIN", "ROLE_ADMIN"))) {
				returnList = invalidSamplesDao.findByParamter("", fromdate, todate, labdepartment);
			} else {
				returnList = invalidSamplesDao.findByParamter(username, fromdate, todate, labdepartment);
			}
		}
		return returnList;
	}

	public List<InvalidSamples> getPatients(List<InvalidSamples> returnList) {

		String barCode = null;
		for (int i = 0; i < returnList.size(); i++) {
			barCode = returnList.get(i).getBarCode();
			returnList.get(i).setPatient(patientsDao.get(Long.parseLong(barCode)));
		}
		return returnList;
	}

	@Override
	public void remove(String barCode) {
		invalidSamplesDao.remove(barCode);
	}

	@Override
	public void save(InvalidSamples sample) {
		invalidSamplesDao.save(sample);
	}

}