package com.zju.webapp.service;

import java.io.IOException;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import org.apache.ws.security.WSPasswordCallback;

public class ClientPaswordCallback implements CallbackHandler {

	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {

		for (int i = 0; i < callbacks.length; i++) {
			
			WSPasswordCallback pc = (WSPasswordCallback) callbacks[i];
			
			int usage = pc.getUsage();
			
			if (usage == WSPasswordCallback.USERNAME_TOKEN) {
				pc.setIdentifier("ws-client");
				pc.setPassword("admin");
			} else {
				pc.setPassword("keyPassword");
			}
		}
	}
}

