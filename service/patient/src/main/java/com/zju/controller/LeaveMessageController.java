package com.zju.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/leave") 
public class LeaveMessageController {

	private static Log log = LogFactory.getLog(LeaveMessageController.class);
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@RequestMapping(method = RequestMethod.GET, value="/message")
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		return new ModelAndView("leaveMessage");
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/ajax/submit")
	@ResponseBody
	public int submitMessage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int result = 0;
		try {
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			log.info("-------------------------------------------------------");
			log.info("Time : " + sdf.format(new Date()));
			log.info("Title : " + title);
			log.info("Content : " + content);
			log.info("-------------------------------------------------------");
			result = 1;
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return result;
	}
}
