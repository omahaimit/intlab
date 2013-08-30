package com.zju.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.zju.dao.SyncDao;
import com.zju.model.ContactInfor;
import com.zju.model.Describe;
import com.zju.model.Diagnostic;
import com.zju.model.FormulaItem;
import com.zju.model.Patient;
import com.zju.model.PatientInfo;
import com.zju.model.Profile;
import com.zju.model.ReferenceValue;
import com.zju.model.Section;
import com.zju.model.SyncLabGroupInfo;
import com.zju.model.SyncMapPK;
import com.zju.model.SyncPK;
import com.zju.model.SyncPatient;
import com.zju.model.SyncResult;
import com.zju.model.SyncSample;
import com.zju.model.TestResult;
import com.zju.model.User;
import com.zju.service.SyncManager;

public class SyncManagerImpl implements SyncManager {

    private SyncDao syncDao;

    public List<SyncPatient> getPatientInfo(String date, String department, String code) {
        return syncDao.getPatientInfo(date, department, code);
    }

    public List<SyncResult> getTestResult(String date, String department, String code) {
        return syncDao.getTestResult(date, department, code);
    }

    public void updatePatients(List<SyncPatient> patients) {
        syncDao.updatePatients(patients);
    }

    public List<Long> getExsitPatientPK(String date, String department, String code, int status) {
        return syncDao.getExsitPatientPK(date, department, code, status);
    }

    public List<SyncPK> getExsitResultPK(String date, String department, String code) {
        return syncDao.getExsitResultPK(date, department, code);
    }

    public List<SyncMapPK> getExsitMapPK(String date, String code) {
        return syncDao.getExsitMapPK(date, code);
    }

    public void insertPatients(List<SyncPatient> patients) {
        syncDao.insertPatients(patients);
    }

    public void insertResults(List<SyncResult> results) {
        syncDao.insertResults(results);
    }

    public void insertPKMap(List<SyncMapPK> mapPK) {
        syncDao.insertPKMap(mapPK);
    }

    public void updateResults(List<SyncResult> results) {
        syncDao.updateResults(results);
    }

    @Autowired
    public void setSyncDao(SyncDao syncDao) {
        this.syncDao = syncDao;
    }

	@Override
	public List<Patient> getPatientList(String patientIds) {
		return syncDao.getPatientList(patientIds);
	}

	public void updateSamples(List<SyncSample> samples) {
		syncDao.updateSamples(samples);
	}

	public void insertSamples(List<SyncSample> samples) {
		syncDao.insertSamples(samples);
	}

	@Override
	public List<Section> getSection() {
		return syncDao.getSection();
	}

	public void updateStatus(PatientInfo info) {
		syncDao.updateStatus(info);
	}

	@Override
	public List<Describe> getAllDescribe() {
		return syncDao.getAllDescribe();
	}

	@Override
	public List<ReferenceValue> getAllReferenceValue() {
		return syncDao.getAllReferenceValue();
	}

	@Override
	public List<Profile> getProfiles(String name) {
		return syncDao.getProfiles(name);
	}

	@Override
	public List<SyncResult> getLocalBySampleNo(String sampleNo) {
		return syncDao.getLocalBySampleNo(sampleNo);
	}

	@Override
	public List<SyncResult> getZ1BySampleNo(String sampleNo) {
		return syncDao.getZ1BySampleNo(sampleNo);
	}

	@Override
	public List<String> getProfileJYZ(String profileName, String deviceId) {
		return syncDao.getProfileJYZ(profileName, deviceId);
	}

	@Override
	public List<Describe> getDescribeByName(String name) {
		return syncDao.getDescribeByName(name);
	}

	@Override
	public List<User> getAllWriteBack(String sample, String labdepartment) {
		return syncDao.getAllWriteBack(sample, labdepartment);
	}

	@Override
	public List<ContactInfor> getContactInformation(String requester) {
		return syncDao.getContactInformation(requester);
	}

	@Override
	public List<FormulaItem> getFormulaItem(String labdepartment) {
		return syncDao.getFormulaItem(labdepartment);
	}

	@Override
	public void updateFormula(TestResult testresult, String formula) {
		syncDao.updateFormula(testresult, formula);
	}

	@Override
	public TestResult getTestResult(String testId, String sampleNo) {
		return syncDao.getTestResult(testId, sampleNo);
	}

	@Override
	public void saveTestResult(TestResult testResult) {
		syncDao.saveTestResult(testResult);
	}

	@Override
	public List<TestResult> getTestBySampleNo(String sampleNo) {
		return syncDao.getTestBySampleNo(sampleNo);
	}

	@Override
	public List<SyncLabGroupInfo> getSyncLabGroupInfo() {
		return syncDao.getSyncLabGroupInfo();
	}

	@Override
	public List<Diagnostic> getDiagnostic() {
		return syncDao.getDiagnostic();
	}
	
}
