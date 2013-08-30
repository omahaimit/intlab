package com.zju.catcher.listener;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.zju.catcher.controller.HomeController;

public class StartUpServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		HomeController controller = HomeController.getInstance();
		controller.StartUpaExecute();
		super.init();
	}
}
