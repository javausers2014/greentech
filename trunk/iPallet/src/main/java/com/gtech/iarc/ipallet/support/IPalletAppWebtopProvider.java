package com.gtech.iarc.ipallet.support;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import com.innovations.webtop.provider.DefaultWebtopProvider;

public class IPalletAppWebtopProvider extends DefaultWebtopProvider {
	
	@Override
	public List<ResourceBundle> getResourceBundles(Locale locale) {		
		ResourceBundle baserb = ResourceBundle.getBundle("com/gtech/iarc/base/message/base", locale);
		ResourceBundle iPalletrb = ResourceBundle.getBundle("com/gtech/iarc/ipallet/message/iPallet", locale);
//		MergeResourceBundle mrs = new MergeResourceBundle(baserb,iPalletrb);
//		return Arrays.asList((ResourceBundle)mrs);
		
		return Arrays.asList(baserb,iPalletrb);
	}
	
	@Override
	public String getWorkspaceDescriptorPath() {
		return "iPallet-workspace.xml";
	}
}
