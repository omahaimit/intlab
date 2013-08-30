package com.zju.webapp.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.common.gzip.GZIPInInterceptor;
import org.apache.cxf.transport.common.gzip.GZIPOutInterceptor;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.zju.webapp.dao.PatientDao;
import com.zju.webapp.dao.PatientInfoDao;
import com.zju.webapp.dao.TestResultDao;
import com.zju.webapp.model.PushData;
import com.zju.webapp.service.ClientPaswordCallback;
import com.zju.webapp.service.GetDataManager;
import com.zju.webapp.service.ReceiveDataService;

@Controller
@RequestMapping("/admin/push*")
public class PushDataController {

	@Autowired
	private PatientInfoDao patientInfoDao;
	
	@Autowired
	private TestResultDao testResultDao;
	
	@Autowired
	private PatientDao patientDao;
	
	private static AtomicBoolean currentFlag = new AtomicBoolean(false);
	private static AtomicInteger threadIndex = new AtomicInteger(0);
	private static AtomicLong interval = new AtomicLong(600000);
	private static String patientUrl = "http://omaha.imit.org.cn:81/patient/services/ReceiveDataService";
	private static Log log = LogFactory.getLog(PushDataController.class);
	
	@RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		request.setAttribute("systemStatus", currentFlag.get());
		request.setAttribute("interval", interval.get());
		request.setAttribute("patientUrl", patientUrl);
        return new ModelAndView("admin/pushData");
    }
	
	@RequestMapping(method = RequestMethod.POST, value="/ajax/switch*")
	@ResponseBody
	public int controlSwitch(HttpServletRequest request, HttpServletResponse response) throws Exception {
			
		int resultFlag = 0;
		try {
			String operate = request.getParameter("operate");
			patientUrl = request.getParameter("patientUrl");
			interval.set(Long.parseLong(request.getParameter("interval")));
			if ("start".equals(operate)) {
				if (currentFlag.get() == false) {
					currentFlag.set(true);
					log.info("--------------启动数据同步--------------");
					pushStart();
				}
			} else if ("stop".equals(operate)) {
				log.info("--------------停止数据同步--------------");
				currentFlag.set(false);
			}
			resultFlag = 1;
		} catch (Exception e) {
			log.error(e);
		}
		response.setContentType("text/html;charset=UTF-8");
		return resultFlag;
	}
	
	private void pushStart() {
		
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				int currentIndex = threadIndex.incrementAndGet();
				while (currentFlag.get() && currentIndex == threadIndex.get()) {
					
					try {
						GetDataManager manager = GetDataManager.getInstance(patientInfoDao, testResultDao, patientDao);
						PushData pushData = manager.getPushData();
						
						JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
				        factory.setServiceClass(ReceiveDataService.class);
				        factory.setAddress(patientUrl);
				        Object obj = factory.create();

				        Client client = ClientProxy.getClient(obj);
				        
				        HTTPConduit http = (HTTPConduit) client.getConduit();      
				        HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();      
				        httpClientPolicy.setConnectionTimeout(36000);      
				        httpClientPolicy.setAllowChunking(false);      
				        httpClientPolicy.setReceiveTimeout(120000);      
				        http.setClient(httpClientPolicy);
				        
				        Endpoint endpoint = client.getEndpoint();
				        endpoint.getInInterceptors().add(new LoggingInInterceptor());
				        endpoint.getInInterceptors().add(new GZIPInInterceptor());
				        endpoint.getOutInterceptors().add(new LoggingOutInterceptor());
				        Map<String, Object> props = new HashMap<String, Object>();
				        props.put("action", "UsernameToken");
				        props.put("passwordType", "PasswordText");
				        props.put("user", "ws-client");
				        props.put("passwordCallbackRef", new ClientPaswordCallback());
				        WSS4JOutInterceptor wss4jOut = new WSS4JOutInterceptor(props);
				        endpoint.getOutInterceptors().add(wss4jOut);
				        endpoint.getOutInterceptors().add(new GZIPOutInterceptor());
				        
				        ReceiveDataService service = (ReceiveDataService) obj;
				        int result = service.receivePushData(pushData);
				        manager.updateMark(result, pushData);
				        log.info("-------<- 推送完毕 ->-------");
					} catch (Exception e) {
						log.error(e);
						log.error("数据推送出错", e);
					}
					try {
						Thread.sleep(interval.get());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		thread.start();
	}
}
