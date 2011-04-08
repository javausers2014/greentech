package com.gtech.iarc.base.model.security;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.gtech.iarc.base.model.core.DataObject;

@Entity
@Table(name = "PERMISSION")
public class Permission extends DataObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1291520203742287916L;

	@Id
	@Column(name = "ID")
	private long id;
	
	@Column(name = "PERMISSION")
	private String permissionCode;
	
	@Column(name = "PERMISSION_DESC")
	private String desc;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPermissionCode() {
		return permissionCode;
	}

	public void setPermissionCode(String permissionCode) {
		this.permissionCode = permissionCode;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
