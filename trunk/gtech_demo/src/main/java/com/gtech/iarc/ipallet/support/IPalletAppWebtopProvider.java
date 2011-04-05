package com.gtech.iarc.ipallet.support;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import com.innovations.webtop.provider.DefaultWebtopProvider;

public class IPalletAppWebtopProvider extends DefaultWebtopProvider {
	
	@Override
	public List<ResourceBundle> getResourceBundles(Locale defaultLocale) {		
		final ResourceBundle rb = ResourceBundle.getBundle("com/gtech/iarc/ipallet/message/iPallet", defaultLocale);

		return Arrays.asList(rb);
	}
	
	@Override
	public String getWorkspaceDescriptorPath() {
		return "iPallet-workspace.xml";
	}
}
