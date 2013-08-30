package com.zju.catcher.service.z1;

import java.util.Date;
import java.util.List;

import com.zju.catcher.entity.local.PatientSample;
import com.zju.catcher.entity.z1.PatientAndResult;
import com.zju.catcher.entity.z1.PatientInfo;

public interface PatientInfoService {

	/**
	 *  获取从from至to时间段内的数据
	 * @param from
	 * @param to	空为当前时间
	 * @return
	 */
	List<PatientInfo> getListBetween(Date from, Date to);
	
	/**
	 * 
	 * @return
	 */
	List<PatientInfo> getAllChanged();
	
	/**
	 *  
	 * @param sampleNoList
	 * @return
	 */
	List<PatientInfo> getList(List<String> sampleNoList);
	
	List<PatientAndResult> getAll(Date date);
	
	List<PatientAndResult> getNotToday(Date date);
	
	List<PatientInfo> getPatientInfo(Date date);
	
	/**
	 *  将已通过的样本状态更新到z1服务器
	 * @param docIds
	 */
	void UpdateSampleHasPass(List<PatientSample> infos);
}
