package com.gtech.iarc.base.model.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.gtech.iarc.base.model.core.DataObject;
import com.gtech.iarc.base.model.security.Permission;

@Entity
@Table(name = "USER_PERMISSIONS")
public class UserPermission extends DataObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5376356727111110868L;

	@Id
	@Column(name = "ID")
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "USER_ID")	
	private UserAccount user;
	
//	@Column(name = "PERMISSION_ID")
//	private long permissionId;
	
	@ManyToOne
	@JoinColumn(name = "PERMISSION_ID")
	private Permission permission;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public UserAccount getUser() {
		return user;
	}
	public void setUser(UserAccount user) {
		this.user = user;
	}
	public Permission getPermission() {
		return permission;
	}
	public void setPermission(Permission permission) {
		this.permission = permission;
	}

	
}
