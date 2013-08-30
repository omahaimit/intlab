package com.zju.catcher.service.local;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zju.catcher.dao.local.PatientSampleDao;
import com.zju.catcher.entity.local.PatientSample;
import com.zju.catcher.entity.z1.PatientInfo;

@Service
public class PatientSampleServiceManager implements PatientSampleService {

	@Autowired
	private PatientSampleDao patientSampleDao;
	
	public void insert(List<PatientSample> patientInfos) {
		patientSampleDao.insert(patientInfos);
	}

    public void update(List<PatientInfo> patientInfos) {
        patientSampleDao.update(patientInfos);
    }

    public List<Long> getPatientId(Date date) {
        return patientSampleDao.getPatientId(date);
    }

	public void updateWithStatus(List<PatientSample> patientInfos) {
		patientSampleDao.updateWithStatus(patientInfos);
	}

	public List<PatientSample> getPatientInfo(String sample, String lib, int status, String operator) {
		return patientSampleDao.getPatientInfo(sample, lib, status, operator);
	}

	public List<Long> getPatientId(Date date, int status) {
		return patientSampleDao.getPatientId(date, status);
	}

	public PatientSample getPatientInfo(String sampleNo) {
		return patientSampleDao.getPatientInfo(sampleNo);
	}

	public void updateEditPatientInfo(List<String> sampleNoList) {
		patientSampleDao.updateEditPatientInfo(sampleNoList);
	}
	
	public List<String> exsitSampleList(List<PatientInfo> sampleNoList) {
		return patientSampleDao.exsitSampleList(sampleNoList);
	}
}
