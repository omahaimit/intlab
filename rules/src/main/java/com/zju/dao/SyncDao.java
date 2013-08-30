package com.zju.dao;

import java.util.List;

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

public interface SyncDao {

    List<SyncPatient> getPatientInfo(String date, String department, String code);

    List<SyncResult> getTestResult(String date, String department, String code);

    List<Long> getExsitPatientPK(String date, String department, String code, int status);
    
    List<SyncPK> getExsitResultPK(String date, String department, String code);
    
    List<SyncMapPK> getExsitMapPK(String date, String code);
    
    void updatePatients(List<SyncPatient> patients);

    void updateSamples(List<SyncSample> samples);
    
    void updateResults(List<SyncResult> results);
    
    void insertPatients(List<SyncPatient> patients);
    
    void insertSamples(List<SyncSample> samples);

    void insertResults(List<SyncResult> results);
    
    void insertPKMap(List<SyncMapPK> mapPK);
    
	List<Patient> getPatientList(String patientIds);
	
	List<Section> getSection();
	
	void updateStatus(PatientInfo info);
	
	List<Describe> getAllDescribe();
	
	List<ReferenceValue> getAllReferenceValue();
	
	List<Profile> getProfiles(String name);
	
	List<String> getProfileJYZ(String profileName, String deviceId);

	List<SyncResult> getZ1BySampleNo(String sampleNo);

	List<SyncResult> getLocalBySampleNo(String sampleNo);

	List<Describe> getDescribeByName(String name);
	
	List<User> getAllWriteBack(String sample, String labdepartment);

	List<ContactInfor> getContactInformation(String requester);

	List<FormulaItem> getFormulaItem(String labdepartment);
	
	void updateFormula(TestResult testResult, String formula);
	
	TestResult getTestResult(String testId, String sampleNo);
	
	void saveTestResult(TestResult testResult);

	List<TestResult> getTestBySampleNo(String sampleNo);
	
	List<SyncLabGroupInfo> getSyncLabGroupInfo();
	
	List<Diagnostic> getDiagnostic();
}
