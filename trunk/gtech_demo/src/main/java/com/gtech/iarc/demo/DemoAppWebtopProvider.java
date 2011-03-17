package com.gtech.iarc.demo;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import com.innovations.webtop.provider.DefaultWebtopProvider;

public class DemoAppWebtopProvider extends DefaultWebtopProvider {


	@Override
	public List<ResourceBundle> getResourceBundles(Locale defaultLocale) {		
		final ResourceBundle rb = ResourceBundle.getBundle("com/gtech/iarc/demo/messages/DemoApp", defaultLocale);

		return Arrays.asList(rb);
	}
}
