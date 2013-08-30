package com.zju.catcher.service.z1;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zju.catcher.dao.z1.PatientInfoDao;
import com.zju.catcher.entity.local.PatientSample;
import com.zju.catcher.entity.z1.PatientAndResult;
import com.zju.catcher.entity.z1.PatientInfo;

@Service
public class PatientInfoServiceManager implements PatientInfoService {

	@Autowired
	private PatientInfoDao patientDao;

	public List<PatientInfo> getListBetween(Date from, Date to) {

		if (from == null)
			return null;
		else if (to == null)
			to = new Date();

		return patientDao.getListBetween(from, to);
	}

	public List<PatientInfo> getAllChanged() {
		return patientDao.getAllChanged();
	}

	public List<PatientInfo> getList(List<String> sampleNoList) {
		return patientDao.getList(sampleNoList);
	}

    public List<PatientAndResult> getAll(Date date) {
        return patientDao.getAll(date);
    }
    
    public List<PatientAndResult> getNotToday(Date date) {
    	return patientDao.getNotToday(date);
    }

    public List<PatientInfo> getPatientInfo(Date date) {
        return patientDao.getPatientInfo(date);
    }

	public void UpdateSampleHasPass(List<PatientSample> infos) {
		patientDao.UpdateSampleHasPass(infos);
	}

}
