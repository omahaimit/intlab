package com.zju.service;

import java.util.List;

import com.zju.model.PatientInfo;



/**
 * Business Service Interface to handle communication between web and
 * persistence layer.
 */
public interface PatientInfoManager extends GenericManager<PatientInfo, Long>{

	/**
	 *  获取患者一段时间内的样本
	 * @param patientId	就诊卡号
	 * @param fromDate	起始日期
	 * @param toDate	结束日期
	 * @return
	 */
	List<PatientInfo> getForPatient(String patientId, String fromDate, String toDate);

	
	/**
     * 通过样本号获取唯一样本
     * @return List
     */
	PatientInfo getBySampleNo(String sampleNo);

	/**
	 *  通过患者就诊卡号查找该患者的样本列表
	 * @param patientId
	 * @return
	 */
	List<PatientInfo> getSampleById(String patientId);

	/**
	 *  为用户医生获取患者数据
	 * @param doctorId  医生姓名
	 * @param fromDate  起始时间
	 * @param toDate   结束时间
	 * @return
	 */
	List<PatientInfo> getForDoctor(String doctorId, 
			String fromDate, String toDate);

	/**
	 *  获取最近半年历史数据，用于差值校验
	 * @param info	当前的样本
	 * @return	该患者之前的样本列表
	 */
	List<PatientInfo> getDiffCheckHistory(PatientInfo info);
	
	/**
	 *  获取所有历史数据
	 * @param patientId		就诊卡号
	 * @param laboratory	实验室
	 * @return
	 */
	List<PatientInfo> getHistorySample(String patientId, String laboratory);
	
	/**
	 *   检验员获取被分配的样本数据
	 * @param date	日期  such as : 20120829
	 * @param department	部门编号
	 * @param code	3个字符的部门代号
	 * @param mark	样本自动审核的标记
	 * @param status 样本自动审核的状态
	 * @return
	 */
	List<PatientInfo> getSampleList(String date, String department, String code, int mark, int status);
	
	/**
	 * 	 样本数统计
	 * @param date	日期，样本号前缀，如20130813
	 * @param department	部门
	 * @param code	代码，如CBC
	 * @return	返回今日未审核数，今日未通过数、未处理的危急样本数，需写回数
	 */
	List<Integer> getAuditInfo(String date, String department, String code, String user);

	/**
	 *  根据病历号获取该患者的样本列表
	 * @param blh	病历号
	 * @return
	 */
	List<PatientInfo> getSampleByBLH(String blh);

	/**
	 *  获取该样本号的样本
	 * @param sampleNo 样本号
	 * @return
	 */
	List<PatientInfo> getListBySampleNo(String sampleNo);

	/**
	 *   获取某一时间段中某患者的样本
	 * @param fromDate	起始日期 如：2013-08-13
	 * @param toDate	结束日期
	 * @param patientName	患者姓名
	 * @return
	 */
	List<PatientInfo> getSampleByPatientName(String fromDate, String toDate, String patientName);
	
	/**
	 *  获取已code为前缀的样本
	 * @param prefix 样本前缀
	 * @return
	 */
	List<PatientInfo> getSampleByPrefix(String prefix);
}
