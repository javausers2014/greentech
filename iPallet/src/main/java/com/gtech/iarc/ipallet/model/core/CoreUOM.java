package com.gtech.iarc.ipallet.model.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.gtech.iarc.base.model.core.BaseObject;


/**
 * 
 * @author xu_zd
 *
 */
@Entity
@Table(name = "CORE_UOM")
public class CoreUOM extends BaseObject implements BizConstantCode {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1775728873267072297L;
	@Id
	@Column(name = "ID")
	private long id;
	   
	@Column(name = "NAME")
	private String uomName;
	
	@Column(name = "CODE")
    private String uomCode;
	
	@Column(name = "CATEGORY")
    private String uomCategory;
    
	@Column(name = "ACTIVE")
    private boolean active;
	
	@Column(name = "BASE")
    private boolean calculateBase;
	
	@Column(name = "FACTOR")
    private double factor;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUomName() {
		return uomName;
	}

	public void setUomName(String uomName) {
		this.uomName = uomName;
	}

	public String getUomCode() {
		return uomCode;
	}

	public void setUomCode(String uomCode) {
		this.uomCode = uomCode;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public double getFactor() {
		return factor;
	}

	public void setFactor(double factor) {
		this.factor = factor;
	}

	public String getUomCategory() {
		return uomCategory;
	}

	public void setUomCategory(String uomCategory) {
		this.uomCategory = uomCategory;
	}

	public boolean isCalculateBase() {
		return calculateBase;
	}

	public void setCalculateBase(boolean calculateBase) {
		this.calculateBase = calculateBase;
	}
}
