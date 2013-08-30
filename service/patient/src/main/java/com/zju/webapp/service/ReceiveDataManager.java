package com.zju.webapp.service;

import javax.jws.WebService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zju.dao.MessageMapper;
import com.zju.dao.PatientInfoMapper;
import com.zju.dao.PatientMapper;
import com.zju.dao.TestResultMapper;
import com.zju.model.PushData;
import com.zju.util.DataUtil;

@Service("receiveDataManager")
@WebService(serviceName = "ReceiveDataService", endpointInterface = "com.zju.webapp.service.ReceiveDataService")
public class ReceiveDataManager implements ReceiveDataService {

	private Log log = LogFactory.getLog(ReceiveDataManager.class);
	
	@Autowired
	private MessageMapper messageMapper;
	
	@Autowired
	private PatientInfoMapper patientInfoMapper;
	
	@Autowired
	private TestResultMapper testResultMapper;
	
	@Autowired
	private PatientMapper patientMapper;
	
	static {
		// 避免innerElementCountThreshold超出阈值
		String intMax = String.valueOf(Integer.MAX_VALUE);
		System.setProperty("org.apache.cxf.stax.maxChildElements", intMax);
		System.setProperty("org.apache.cxf.staxutils.innerElementCountThreshold", intMax);
	}
	
	@Override
	public int receivePushData(PushData data) {
		
		int result = 1;
		try {
			DataUtil dataUtil = DataUtil.getInstance(patientInfoMapper, testResultMapper, patientMapper, messageMapper);
			dataUtil.startReceiveData(data);
		} catch (Exception e) {
			result = 0;
			log.error(e);
		}
		return result;
	}

}
