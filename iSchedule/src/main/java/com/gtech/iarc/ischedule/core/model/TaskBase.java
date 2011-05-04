// Copyright(c) 2011 gTech, All Rights Reserved.
package com.gtech.iarc.ischedule.core.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 
 * @author ZHIDAO
 */
public interface TaskBase extends Serializable{

	/**
	 * @return Returns the activeInd.
	 */
	public Boolean getActiveInd();

	/**
	 * 
	 * @param activeInd
	 */
	public void setActiveInd(Boolean activeInd);
	/**
	 * @return Returns the createdBy.
	 *
	 */
	public String getCreatedBy();

	/**
	 * @return Returns the createdDate.
	 */
	public Timestamp getCreatedDate();

	/**
	 * @param createdBy The createdBy to set.
	 */
	public void setCreatedBy(String createdBy);

	/**
	 * @param createdDate The createdDate to set.
	 */
	public void setCreatedDate(Timestamp createdDate);

	/**
	 * @return Returns the modifiedBy.
	 */
	public String getModifiedBy();

	/**
	 * @return Returns the modifiedDate.
	 */
	public Timestamp getModifiedDate();
	
	/**
	 * @param modifiedBy The modifiedBy to set.
	 */
	public void setModifiedBy(String modifiedBy);

	/**
	 * @param modifiedDate The modifiedDate to set.
	 */
	public void setModifiedDate(Timestamp modifiedDate);
}