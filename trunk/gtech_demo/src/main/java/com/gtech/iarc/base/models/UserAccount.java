package com.gtech.iarc.base.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "USER")
public class UserAccount {
	
	@Id
	@Column(name = "ID")
	private long id;
	
	@Column(name = "USERNAME")
	private String userName;
	
	@Column(name = "FULLNAME")
	private String fullName;
	
	private long personnelId;
	
	@OneToMany
	@JoinColumn(name = "USER_ID")
	private Set<UserPermission> permissions = new HashSet<UserPermission>();

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

	public long getPersonnelId() {
		return personnelId;
	}

	public void setPersonnelId(long personnelId) {
		this.personnelId = personnelId;
	}

	public Set<UserPermission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<UserPermission> permissions) {
		this.permissions = permissions;
	}
}
