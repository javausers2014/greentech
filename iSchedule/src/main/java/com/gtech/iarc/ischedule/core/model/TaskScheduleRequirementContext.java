// Copyright(c) 2011 gTech, All Rights Reserved.
package com.gtech.iarc.ischedule.core.model;

import java.io.Serializable;
import java.sql.Blob;
import java.util.Date;

/**
 * 
 * @author ZHIDAO
 * @revision $Id: IArcTaskConfig.java 7412 2007-09-21 17:31:17Z zhidao $
 */
public interface TaskScheduleRequirementContext extends TaskBase{

	public String getTaskScheduleMode();

	public void setTaskScheduleMode(String taskScheduleMode);

    public String getParamterJspURL();

	public void setParamterJspURL(String paramterJspURL);
	
	public Object getParameterObject();

	public void setParameterObject(Serializable parameterObject);

	public TaskExecutionDetail getArcTaskDetail();

	public void setArcTaskDetail(TaskExecutionDetail taskDetail);

	public String getDescription();

	public void setDescription(String description);

	public Date getEndDate();

	public String getTaskSchedule();

	public Long getTaskDetailId();


//	public Blob getParameterObjectInBlob();
//
//	public void setParameterObjectInBlob(Blob blob);

	public Long getPriority();
	
	public Date getStartDate();

	public String getStatus();

	public void setEndDate(Date endDate);

	public void setTaskSchedule(String taskSchedule);

	public void setTaskDetailId(Long taskDetailId);

	public void setPriority(Long priority);

	public void setStartDate(Date startDate);

	public void setStatus(String status);

	public String getTaskScheduleName();

	public void setTaskScheduleName(String taskScheduleName);

	public String getJobCronExpression();
	public void setJobCronExpression(String jobCronExpression);

	public String getEmailId();
	public void setEmailId(String emailId);
	
	public String getDisplayEndTime();
	public void setDisplayEndTime(String displayEndTime);


	public String getDisplayStartTime();

	public void setDisplayStartTime(String displayStartTime);

	public String getDisplayPriority();

	public void setDisplayPriority(String displayPriority);
	public Long getId();
	public void setId(Long id);
}