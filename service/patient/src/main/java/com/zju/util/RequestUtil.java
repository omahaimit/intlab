package com.zju.util;

import java.util.Date;
import com.zju.dao.RequestMapper;
import com.zju.model.Request;

public class RequestUtil {

	private static RequestUtil requestUtil = null;
	
	private RequestMapper requestMapper = null;
	
	private RequestUtil() {}
	
	public static RequestUtil getInstance(RequestMapper requestMapper) {
		
		if (requestUtil == null) {
			synchronized (RequestUtil.class) {
				if (requestUtil == null) {
					requestUtil = new RequestUtil();
					requestUtil.requestMapper = requestMapper;
				}
			}
		}
		
		return requestUtil;
	}
	
	public boolean receiveRequest(String patientId, String content) {
		
		boolean flag = true;
		try {
			Request request= new Request();
			request.setPatientId(patientId);
			request.setRequestTime(new Date());
			request.setRequestContent(content);
			requestMapper.insert(request);
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
}
