package com.zju.service;

import java.util.Date;
import java.util.List;

import com.zju.model.TestResult;
import com.zju.model.TestResultPK;

/**
 * 检验项目结果的接口
 */
public interface TestResultManager extends GenericManager<TestResult, TestResultPK>{

	/**
     * 获得某一样本的所有检验项目结果
     *
     * @param sampleNo 样本号
     * @return List 该样本的检验项目结果列表
     */
	List<TestResult> getBySampleNo(String sampleNo);
	
	
	/**
     * 获得某些样本的所有检验项目结果
     *
     * @param sampleList 样本号列表
     * @return List 检验项目结果列表
     */
	List<TestResult> getByList(String sampleList);

	/**
     * 获得某一病人特定的检验项目历史结果
     *
     * @param patientId 病人id
     * @param history 需要获得历史结果的项目列表
     * @return List 某一病人特定的检验项目历史结果
     */
	List<TestResult> getHistory(String patientId, String history);

	/**
     * 获得某病人的所有检验项目结果
     *
     * @param patientId 病人id
     * @return List 该病人的检验项目结果列表
     */
	List<TestResult> getByPatientId(String patientId);
	
	/**
     * 获得某一病人某一项目的历史结果
     *
     * @param patientId 病人id
     * @param testId 检验项目id
     * @param date 检验项目所做的时间，获得的检验项目结果应小于该时间
     * @return List 某一病人某一项目的历史结果
     */
	List<TestResult> getForDifCheck(String patientId, String testId, Date date);

	/**
     * 获得某一样本某些项目的检验结果
     *
     * @param sampleNo 样本号
     * @param testString 检验项目id列表
     * @return List 某一样本某些项目的检验结果列表
     */
	List<TestResult> getListByTestString(String sampleNo, String testString);

	/**
     * 获得某一病人某一项目的历史结果
     *
     * @param patientName 病人姓名
     * @param birthday 病人出生日期，防止获得同一名字的不同病人的检验结果
     * @param testid 检验项目id
     * @return List 某一病人某一项目的历史结果
     */
	List<TestResult> getSingleHistory(String testid, String patientName,
			String birthday);

}
