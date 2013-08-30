package com.zju.catcher.service.z1;

import java.util.Date;
import java.util.List;

import com.zju.catcher.entity.local.PatientSample;
import com.zju.catcher.entity.z1.PatientAndResult;
import com.zju.catcher.entity.z1.PatientInfo;

public interface PatientInfoService {

	/**
	 *  ��ȡ��from��toʱ����ڵ�����
	 * @param from
	 * @param to	��Ϊ��ǰʱ��
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
	 *  ����ͨ��������״̬���µ�z1������
	 * @param docIds
	 */
	void UpdateSampleHasPass(List<PatientSample> infos);
}
