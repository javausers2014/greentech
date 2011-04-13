package com.gtech.iarc.base.model.personalinfo;

import com.gtech.iarc.base.web.dto.GeneralSearchResultDTO;

public class PersonnelSearchDTO extends GeneralSearchResultDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8597256579150314325L;
	private String staffNo;
	private String fullName;
	private String email;
	
	public String getStaffNo() {
		return staffNo;
	}
	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
