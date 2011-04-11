package com.gtech.iarc.base.model.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.gtech.iarc.base.model.core.DataObject;
import com.gtech.iarc.base.model.personalinfo.Personnel;

@Entity
@Table(name = "USER")
public class UserAccount extends DataObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -310122997165589415L;

	@Id
	@Column(name = "ID")
	private long id;
	
	@Column(name = "USERNAME")
	private String userName;
	
	@Column(name = "FULLNAME")
	private String fullName;
	
	@Column(name = "PASSWORD")
	private String password;
//	
//	@Transient
//	private long personnelId;
	
	@OneToOne(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name = "PERSONNEL_ID")
	private Personnel personnelInfo;
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name = "USER_ID")
	private List<UserPermission> permissions = new ArrayList<UserPermission>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

//	public long getPersonnelId() {
//		return personnelId;
//	}
//
//	public void setPersonnelId(long personnelId) {
//		this.personnelId = personnelId;
//	}

	public List<UserPermission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<UserPermission> permissions) {
		this.permissions = permissions;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Personnel getPersonnelInfo() {
		return personnelInfo;
	}

	public void setPersonnelInfo(Personnel personnelInfo) {
		this.personnelInfo = personnelInfo;
	}
	
	
}
