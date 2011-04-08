package com.gtech.iarc.ipallet.web.dwrproxy.dto;

import java.util.Date;

import com.gtech.iarc.base.model.core.InfoTransferObject;
import com.gtech.iarc.base.model.personalinfo.Personnel;

public class StaffInfo implements InfoTransferObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2337434012053247133L;
	private long id;
	private String staffNo;
	private String fullName;
	private String firstName;
	private String lastName;
	private String email;
	private Date birthDate;
	
	public StaffInfo(Personnel personnel){
		this.setId(personnel.getId());
		this.setFullName(personnel.getFullName());
		this.setStaffNo(personnel.getStaffNo());
		this.setEmail(personnel.getEmail());
		this.setFirstName(personnel.getFirstName());
		this.setLastName(personnel.getLastName());
		this.setBirthDate(personnel.getBirthddate());
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public String getStaffNo() {
		return staffNo;
	}
	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}
	
}
