package com.zju.webapp.service;

import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import com.zju.model.PushData;

@WebService
@Path("/")
@Produces({"application/json", "application/xml"})
public interface ReceiveDataService {

	@GET
	@Path("/receive")
	int receivePushData(PushData data);
}

