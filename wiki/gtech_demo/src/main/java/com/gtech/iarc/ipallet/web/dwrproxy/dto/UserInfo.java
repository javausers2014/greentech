package com.gtech.iarc.ipallet.web.dwrproxy.dto;

import com.gtech.iarc.base.model.core.InfoTransferObject;
import com.gtech.iarc.base.model.user.UserAccount;

public class UserInfo implements InfoTransferObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2337434012053247133L;
	private String userId;
	private String fullName;
	private String firstName;
	private String lastName;
	private String email;
	private String department;
	
	public UserInfo(UserAccount userAccount){
		this.setUserId(userAccount.getUserName());
		this.setFullName(userAccount.getFullName());
		this.setEmail("xuzhidao@gmail.com");
		this.setFirstName("First Name");
		this.setLastName("SurName");
		this.setDepartment("Here_AFT");
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	
}
