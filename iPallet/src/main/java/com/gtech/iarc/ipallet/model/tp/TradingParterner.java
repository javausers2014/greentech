package com.gtech.iarc.ipallet.model.tp;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TP")
public class TradingParterner {
	
	public final static String TP_TYPE_OWNER="OWNER";
	public final static String TP_TYPE_SUPPLIER="SUPPLIER";
	public final static String TP_TYPE_TRANSPORTER="TRANSPORTER";
	
	public final static String TP_CATEGORY_PERSONAL="PERSONAL";
	public final static String TP_CATEGORY_COMPANY="COMPANY";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7767170060169697538L;
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(name = "TYPE")
	private String type;
	
	@Column(name = "FULLNAME")
	private String fullName;
	
	@Column(name = "CODE")
	private String code;
	
	@Column(name = "USEACCOUNT")
	private String userAccount;
	
	@Column(name = "CATEGORY")
	private String category;
	
	@Column(name = "BIZ_START_DATE")
	private Date bizStartDate;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Date getBizStartDate() {
		return bizStartDate;
	}
	public void setBizStartDate(Date bizStartDate) {
		this.bizStartDate = bizStartDate;
	}
	


}
