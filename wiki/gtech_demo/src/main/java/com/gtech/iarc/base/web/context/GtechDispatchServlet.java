package com.gtech.iarc.base.web.context;

import org.springframework.web.servlet.DispatcherServlet;

public class GtechDispatchServlet extends DispatcherServlet {
	private static final long serialVersionUID = 165454313L;
	
	@Override
	public String getContextConfigLocation() {
		return "classpath:com/gtech/iarc/base/web/context/gtechWebContext.xml";
	}
	
}