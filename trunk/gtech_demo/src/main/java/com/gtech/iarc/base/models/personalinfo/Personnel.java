package com.gtech.iarc.base.models.personalinfo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.gtech.iarc.base.models.core.DataObject;

@Entity
@Table(name = "Personnel")
public class Personnel extends DataObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6134133644198179906L;
	@Id
	@Column(name = "ID")
	private long id;
	
	@Column(name = "FirstName")
	private String firstName;
	
	@Column(name = "LastName")
	private String lastName;
	
	@Column(name = "MiddleName")
	private String middleName;
	
	@Column(name = "PersonnelNo")
	private String personnelNumber;
	
	@Column(name = "Phone")
	private String phone;
	
	@Column(name = "Fax")
	private String fax;
	
	@Column(name = "Mobile")
	private String mobile;
	
	@Column(name = "BirthDate")
	private Date birthddate;
	
	@Column(name = "Email")
	private String email;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getPersonnelNumber() {
		return personnelNumber;
	}
	public void setPersonnelNumber(String personnelNumber) {
		this.personnelNumber = personnelNumber;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getBirthddate() {
		return birthddate;
	}
	public void setBirthddate(Date birthddate) {
		this.birthddate = birthddate;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}

