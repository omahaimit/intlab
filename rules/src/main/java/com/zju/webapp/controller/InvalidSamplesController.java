package com.zju.webapp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import com.zju.model.InvalidSamples;
import com.zju.service.InvalidSamplesManager;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

 
@Controller
@RequestMapping("/invalidSamples*")
public class InvalidSamplesController {
    private InvalidSamplesManager invalidSamplesManager;
 
    @Autowired
    public void setInvalidSamplesManager(InvalidSamplesManager invalidSamplesManager) {
        this.invalidSamplesManager = invalidSamplesManager;
    }
 
    @RequestMapping(method = {RequestMethod.GET})
    public ModelAndView handleRequest(HttpServletRequest request)
    throws Exception {
    	String username=request.getRemoteUser();
		String labDepartment=request.getParameter("labDepartment");
		String fromDate=request.getParameter("fromDate");
		String toDate=request.getParameter("toDate");
		if(fromDate==null){
			fromDate="";
		}
		if (toDate==null){
			toDate="";
		}
		if(labDepartment==null){
			labDepartment="0000000";
		}
		
    	
    	List<InvalidSamples> list=invalidSamplesManager.getList(username,fromDate,toDate,labDepartment);
    	list=invalidSamplesManager.getPatients(list);
    	request.setAttribute("size", list.size());
    	return new ModelAndView().addObject(list);
    }
    
    
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView onSubmit(HttpServletRequest request)
    throws Exception {
		String username=request.getRemoteUser();
		String labDepartment=request.getParameter("labDepartment");
		String fromDate=request.getParameter("fromDate");
		String toDate=request.getParameter("toDate");
		
		List<InvalidSamples> list=invalidSamplesManager.getList(username,fromDate,toDate,labDepartment);
    	list=invalidSamplesManager.getPatients(list);
    	request.setAttribute("toDate", toDate);
    	request.setAttribute("fromDate", fromDate);
    	request.setAttribute("labDepartment", labDepartment);
    	request.setAttribute("size", list.size());
    	return new ModelAndView().addObject(list);
    }
}
