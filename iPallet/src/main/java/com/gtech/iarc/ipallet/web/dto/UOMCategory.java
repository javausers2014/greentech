package com.gtech.iarc.ipallet.web.dto;

import com.gtech.iarc.base.model.core.InfoTransferObject;

public class UOMCategory implements InfoTransferObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6496473471786912161L;
	private String key;
	private String label;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	

}
