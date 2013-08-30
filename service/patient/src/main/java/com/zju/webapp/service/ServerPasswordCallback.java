package com.zju.webapp.service;

import java.io.IOException;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import org.apache.ws.security.WSPasswordCallback;

public class ServerPasswordCallback implements CallbackHandler {

	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {

		WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];

		if (pc.getIdentifier().equalsIgnoreCase("ws-client")) {
			pc.setPassword("admin");
		} else {
			throw new SecurityException("the user does not exits");
		}
	}
}
