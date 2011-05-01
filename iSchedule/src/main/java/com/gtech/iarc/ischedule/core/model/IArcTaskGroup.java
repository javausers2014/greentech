// Copyright(c) 2011 gTech, All Rights Reserved.
package com.gtech.iarc.ischedule.core.model;

/**
 * 
 * @author ZHIDAO
 * @revision $Id: IArcTaskGroup.java 6647 2007-08-21 15:11:13Z zhidao $
 */
public interface IArcTaskGroup extends IArcTask{

	public static final int Arc_SCHEDULE_TASK_GRP_GNRL = 1;
	
	public static final int Arc_SCHEDULE_TASK_GRP_REPORT_VIEW = 2;
	
	/**
	 *@return Returns the jobGroupFullName.
	 */
	public String getTaskGroupFullName();

	/**
	 * @param jobGroupFullName The jobGroupFullName to set.
	 */
	public void setTaskGroupFullName(String jobGroupFullName);

	/**
	 *@return Returns the jobGroupId.
	 */
	public Long getTaskGroupId();

	/**
	 * @param jobGroupId The jobGroupId to set.
	 */
	public void setTaskGroupId(Long jobGroupId);

	/**
	 *@return Returns the jobGroupShortName.
	 */
	public String getTaskGroupShortName();

	/**
	 * @param jobGroupShortName DOCUMENT ME!
	 */
	public void setTaskGroupShortName(String jobGroupShortName);

	/**
	 * @return Returns the deleteLink.
	 */
	public String getDeleteLink();

	/**
	 * @param deleteLink The deleteLink to set.
	 */
	public void setDeleteLink(String deleteLink);

	/**
	 * @return Returns the updateLink.
	 */
	public String getUpdateLink();

	/**
	 * @param updateLink The updateLink to set.
	 */
	public void setUpdateLink(String updateLink);

	/**
	 * @return Returns the viewLink.
	 */
	public String getViewLink();

	/**
	 * @param viewLink The viewLink to set.
	 */
	public void setViewLink(String viewLink);


}