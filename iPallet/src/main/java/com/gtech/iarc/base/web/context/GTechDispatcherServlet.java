package com.gtech.iarc.base.web.context;

import org.springframework.web.servlet.DispatcherServlet;

public class GTechDispatcherServlet extends DispatcherServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -229551105221853160L;

	@Override
	public String getContextConfigLocation() {
		return "classpath:com/innovations/webtop/spring/web.xml classpath:gtechweb.xml";
	}
}
