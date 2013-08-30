package com.zju.service;

import java.util.List;

import com.zju.model.ContactInfor;
import com.zju.model.Describe;
import com.zju.model.Diagnostic;
import com.zju.model.FormulaItem;
import com.zju.model.Patient;
import com.zju.model.Profile;
import com.zju.model.ReferenceValue;
import com.zju.model.Section;
import com.zju.model.SyncLabGroupInfo;
import com.zju.model.SyncResult;
import com.zju.model.TestResult;
import com.zju.model.User;

/**
 *  直接跟医院数据库的数据同步
 * @author Winstar
 *
 */
public interface SyncManager {

	/**
	 * 获取多个患者信息
	 * @param patientIds	患者就诊卡号，用逗号分割
	 * @return
	 */
	List<Patient> getPatientList(String patientIds);
	
	/**
	 *  获取医院所有科室
	 * @return
	 */
	List<Section> getSection();
	
	/**
	 *  获取所有指标的描述信息
	 * @return
	 */
	List<Describe> getAllDescribe();
	
	/**
	 *  获取所有指标参考范围数据
	 * @return
	 */
	List<ReferenceValue> getAllReferenceValue();
	
	/**
	 *  获取检验套餐
	 * @param name
	 * @return
	 */
	List<Profile> getProfiles(String name);
	
	/**
	 *  根据套餐名和设备号获取套餐信息
	 * @param profileName
	 * @param deviceId
	 * @return
	 */
	List<String> getProfileJYZ(String profileName, String deviceId);

	List<SyncResult> getLocalBySampleNo(String sampleNo);

	List<SyncResult> getZ1BySampleNo(String sampleNo);

	/**
	 *  通过指标的中文名称，获取指标信息
	 * @param name	部分中文名称
	 * @return
	 */
	List<Describe> getDescribeByName(String name);
	
	/**
	 *  获取需写回的用户
	 * @param prefix	样本号前缀
	 * @param labdepartment 实验室
	 * @return
	 */
	List<User> getAllWriteBack(String prefix, String labdepartment);

	/**
	 *  获取联系方式
	 * @param requester 工号
	 * @return
	 */
	List<ContactInfor> getContactInformation(String requester);
	
	/**
	 *  获取计算项
	 * @param labdepartment
	 * @return
	 */
	List<FormulaItem> getFormulaItem(String labdepartment);

	/**
	 * 更新计算值
	 * @param testresult
	 * @param formula
	 */
	void updateFormula(TestResult testresult, String formula);
	
	/**
	 *  获取一个testresult
	 * @param testId
	 * @param sampleNo
	 * @return
	 */
	TestResult getTestResult(String testId, String sampleNo);
	
	/**
	 *  保存testreuslt
	 * @param testResult
	 */
	void saveTestResult(TestResult testResult);

	/**
	 *  获取一个样本的testresult列表
	 * @param sampleNo
	 * @return
	 */
	List<TestResult> getTestBySampleNo(String sampleNo);
	
	/**
	 *  获取期望的样本周转时间
	 * @return
	 */
	List<SyncLabGroupInfo> getSyncLabGroupInfo();

	/**
	 * 获取样本初步诊断与知识库疾病知识条目的映射
	 * @return
	 */
	List<Diagnostic> getDiagnostic();
}
