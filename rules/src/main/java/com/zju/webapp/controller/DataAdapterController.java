package com.zju.webapp.controller;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.zju.util.Config;

@Controller
@RequestMapping("/admin/adapter*")
public class DataAdapterController {
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	@RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {	
	
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(sdf.format(new Date()).getBytes());
		String hex = byte2hex(md5.digest());
		System.out.println(hex);
		request.setAttribute("url", Config.getCatcherUrl());
		request.setAttribute("hex", hex);
		return new ModelAndView("admin/adapter");
	}
	
	private static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}
}
