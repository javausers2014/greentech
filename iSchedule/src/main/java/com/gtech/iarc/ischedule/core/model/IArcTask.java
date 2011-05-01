// Copyright(c) 2011 gTech, All Rights Reserved.
package com.gtech.iarc.ischedule.core.model;

import java.sql.Timestamp;

/**
 * 
 * @author ZHIDAO
 * @revision $Id: IArcTask.java 6647 2007-08-21 15:11:13Z zhidao $
 */
public interface IArcTask {

	/**
	 * @return Returns the activeInd.
	 */
	public Boolean getActiveInd();

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
	 * @return Returns the inactBy.
	 */
	public String getInactBy();

	/**
	 * @return Returns the inactDate.
	 */
	public Timestamp getInactDate();

	/**
	 * @return Returns the modifiedBy.
	 */
	public String getModifiedBy();

	/**
	 * @return Returns the modifiedDate.
	 */
	public Timestamp getModifiedDate();

	/**
	 * @param activeInd The activeInd to set.
	 */
	public void setActiveInd(Boolean activeInd);

	/**
	 * @param createdBy The createdBy to set.
	 */
	public void setCreatedBy(String createdBy);

	/**
	 * @param createdDate The createdDate to set.
	 */
	public void setCreatedDate(Timestamp createdDate);

	/**
	 * @param inactBy The inactBy to set.
	 */
	public void setInactBy(String inactBy);

	/**
	 * @param inactDate The inactDate to set.
	 */
	public void setInactDate(Timestamp inactDate);

	/**
	 * @param modifiedBy The modifiedBy to set.
	 */
	public void setModifiedBy(String modifiedBy);

	/**
	 * @param modifiedDate The modifiedDate to set.
	 */
	public void setModifiedDate(Timestamp modifiedDate);

}