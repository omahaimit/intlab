package com.zju.test;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import com.zju.model.PushData;
import com.zju.webapp.service.ReceiveDataService;

public class WebserviceTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("Main start......");
		
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(ReceiveDataService.class);
        factory.setAddress("http://localhost/services/ReceiveDataService");
        
        ReceiveDataService service = (ReceiveDataService) factory.create();
        
        System.out.println("#############Client getPatientInfo##############");
        int result = service.receivePushData(new PushData());
        System.out.println(result);
        
	}

}
