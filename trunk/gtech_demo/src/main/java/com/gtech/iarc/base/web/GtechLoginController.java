package com.gtech.iarc.base.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.innovations.webtop.constants.ContentType;
import com.innovations.webtop.constants.ModelProperty;
import com.innovations.webtop.constants.Url;
import com.innovations.webtop.constants.ViewName;
import com.innovations.webtop.constants.Webtop;
import com.innovations.webtop.types.application.WebtopProperties;
import com.innovations.webtop.util.ResourceUtil;
import com.innovations.webtop.web.controller.AbstractWebtopController;

public class GtechLoginController extends AbstractWebtopController {
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {		
		response.setContentType(ContentType.TEXT_HTML + ";charset=" + Webtop.DEFAULT_CHARSET);
		ModelAndView modelAndView = createModelAndView();
		
		modelAndView.addObject(ModelProperty.MODEL_APPTITLE, getTitle());
		modelAndView.addObject(ModelProperty.MODEL_LOGINSTATICHEADERCONTRIBUTION, getResourceContributionsForPage(request));		
		modelAndView.setViewName(ViewName.LOGIN);

		return modelAndView;
	}
	
	protected String getResourceContributionsForPage(HttpServletRequest request) {
		String basePath = getBasePath(request);
		List<String> js = new ArrayList<String>();
		List<String> css = new ArrayList<String>();
		

		css.add(ResourceUtil.getCssResourcePath(Url.CSS_EXTJS, basePath));
		
		if (webtopProperties.getBoolean(WebtopProperties.DEBUGMODE)) {
			js.add(ResourceUtil.getJsResourcePath(Url.JS_EXTBASE_DEBUG, basePath));
			js.add(ResourceUtil.getJsResourcePath(Url.JS_EXTJS_DEBUG, basePath));
		} else {
			js.add(ResourceUtil.getJsResourcePath(Url.JS_EXTBASE, basePath));
			js.add(ResourceUtil.getJsResourcePath(Url.JS_EXTJS, basePath));
		}
		css.add(ResourceUtil.getCssResourcePath(Url.CSS_WEBTOP, basePath));
		css.add(ResourceUtil.getCssResourcePath(Url.CSS_LOGIN, basePath));
		js.add(ResourceUtil.getJsResourcePath(Url.JS_LOGIN, basePath));
		
		return getHeaderContributions(js, css);
	}	
}
