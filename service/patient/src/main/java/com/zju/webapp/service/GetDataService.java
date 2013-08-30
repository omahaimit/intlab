package com.zju.webapp.service;

import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.zju.model.PatientInfo;
import com.zju.model.PushData;
import com.zju.model.TestResult;

@WebService
@Path("/")
@Produces({"application/json", "application/xml"})
public interface GetDataService {

	@GET
	@Path("/infos")
	List<PatientInfo> getNewPatientInfo();
	
	@GET
	@Path("/results")
	List<TestResult> getNewTestResult();
	
	@GET
	@Path("/data")
	PushData getPushData();
}
