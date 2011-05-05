package com.gtech.iarc.ischedule.core.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public interface TaskScheduleRequirementContext extends Serializable{

	public abstract long getId();

	public abstract void setId(long id);

	public abstract int getPriority();

	public abstract void setPriority(int priority);

	public abstract String getStatus();

	public abstract void setStatus(String status);

	public abstract Date getStartDate();

	public abstract void setStartDate(Date startDate);

	public abstract Date getEndDate();

	public abstract void setEndDate(Date endDate);

	public abstract String getTaskScheduleMode();

	public abstract void setTaskScheduleMode(String taskScheduleMode);

	public abstract String getJobScheduledCode();

	public abstract void setJobScheduledCode(String jobScheduledCode);

	public abstract String getDescription();

	public abstract void setDescription(String description);

	public abstract TaskExecutionDetail getTaskExecutionDetail();

	public abstract void setTaskExecutionDetail(
			TaskExecutionDetail taskExecutionDetail);

	public abstract String getNotifyEmail();

	public abstract void setNotifyEmail(String notifyEmail);

	public abstract Boolean getActiveInd();

	public abstract void setActiveInd(Boolean activeInd);

	public abstract Object getParameterObject();

	public abstract void setParameterObject(Serializable parameterObject);

	public abstract String getCreatedBy();

	public abstract void setCreatedBy(String createdBy);

	public abstract Timestamp getCreatedDate();

	public abstract void setCreatedDate(Timestamp createdDate);

}