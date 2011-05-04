// Copyright(c) 2011 gTech, All Rights Reserved.
package com.gtech.iarc.ischedule.core.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.commons.lang.SerializationUtils;
import org.hibernate.Hibernate;

import com.gtech.iarc.base.model.core.BaseObject;



/**
 * @hibernate.class table="COMM_TASK"
 * @author ZHIDAO
 * @revision $Id$
 */
public class DefaultTaskScheduleRequirementContext extends BaseObject implements TaskScheduleRequirementContext {

    
	/**
	 * 
	 */
	private static final long serialVersionUID = -5938085808975996287L;
	private Long id;
    private Long taskDetailId;
    private Long priority;
    private String status;
    private Date startDate;
    private Date endDate;
    private String taskSchedule;
    private String taskScheduleMode;
    private String taskScheduleName;
    private String description;
    private TaskExecutionDetail ArcTaskDetail;   
    private String emailId;   
    
    // Non-persistence members
    private Object parameterObject; 
    private String jobCronExpression;
    private String displayStartDate;
    private String displayEndDate;
    private String displayStartTime;
    private String displayEndTime;
    private String displayPriority;
    private String paramterJspURL;

	public String getParamterJspURL() {
		return paramterJspURL;
	}

	public void setParamterJspURL(String paramterJspURL) {
		this.paramterJspURL = paramterJspURL;
	}

	/**
	 * @hibernate.property column="NOTIFIER_EMAIL"
	 */
	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	/**
     * @return Returns the description.
     * @hibernate.property column="TASK_SCHEDULE_MODE"
     */
    public String getTaskScheduleMode() {
        return taskScheduleMode;
    }

    /* (non-Javadoc)
	 * @see com.gtech.iarc.ishedule.model.IArcTaskConfig#setTaskScheduleMode(java.lang.String)
	 */
    public void setTaskScheduleMode(String taskScheduleMode) {
        this.taskScheduleMode = taskScheduleMode;
    }

    public Object getParameterObject() {
        byte[] byteClass = this.getBinFile();  
        if(byteClass==null) return null;
        return SerializationUtils.deserialize(byteClass);
    }

   
    public void setParameterObject(Serializable parameterObject) {
    	this.binFile = SerializationUtils.serialize( parameterObject);
    }

    private byte[] binFile;       
    /**
     * @hibernate.property column="PARAMETER_OBJECT" type="org.springframework.orm.hibernate3.support.BlobByteArrayType"
     */
    public byte[] getBinFile() {
        return binFile;
    }
    public void setBinFile(byte[] binFile) {
        this.binFile = binFile;
    }    
    public void setBlobFile(Blob blobFile) {
        this.binFile = this.toByteArray(blobFile);
    }
    /** Don't invoke this. Used by Hibernate only. */
    public Blob getBlobFile() {
        return Hibernate.createBlob(this.binFile);
    }
    
//    /**
//     * @return Returns the parameterObject.
//     * @hibernate.property column="PARAMETER_OBJECT" type="blob" not-null="false"
//     */
//    public Blob getParameterObjectInBlob() {
//        return ConvertUtils.convertSerializableObjectToBlob(parameterObject);
//    }
//
//    /* (non-Javadoc)
//	 * @see com.gtech.iarc.ishedule.model.IArcTaskConfig#setParameterObjectInBlob(java.sql.Blob)
//	 */
//    public void setParameterObjectInBlob(Blob blob) {
//        this.parameterObject = ConvertUtils.convertBlobToObject(blob);
//    }
    
    
    /**
    * @return Returns the jobType.
    * @hibernate.many-to-one column="TASK_DETAIL_ID"
    * class="com.gtech.iarc.ishedule.model.ArcTaskDetail" not-null="false" insert="false" update="false"
    */
    public TaskExecutionDetail getArcTaskDetail() {
        return ArcTaskDetail;
    }

    /* (non-Javadoc)
	 * @see com.gtech.iarc.ishedule.model.IArcTaskConfig#setTaskDetail(com.gtech.iarc.ishedule.model.ArcTaskDetail)
	 */
    public void setArcTaskDetail(TaskExecutionDetail taskDetail) {
        this.ArcTaskDetail = taskDetail;
    }

    /**
     * @return Returns the description.
     * @hibernate.property column="TASK_FULL_NAME"
     */
    public String getDescription() {
        return description;
    }

    /* (non-Javadoc)
	 * @see com.gtech.iarc.ishedule.model.IArcTaskConfig#setDescription(java.lang.String)
	 */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return Returns the endDate.
     * @hibernate.property column="END_DATE"
     */
    public Date getEndDate() {
        return endDate;
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTaskSchedule() {
		return taskSchedule;
	}

	public void setTaskDetailId(Long taskDetailId) {
		this.taskDetailId = taskDetailId;
	}

	public Long getTaskDetailId(){
		return this.taskDetailId;
	}

    /**
     * @return Returns the priority.
     * @hibernate.property column="PRIORITY"
     */
    public Long getPriority() {
        return priority;
    }

    /**
     * @return Returns the startDate.
     * @hibernate.property column="START_DATE"
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @return Returns the status.
     * @hibernate.property column="STATUS"
     */
    public String getStatus() {
        return status;
    }
    
    /* (non-Javadoc)
	 * @see com.gtech.iarc.ishedule.model.IArcTaskConfig#setEndDate(java.util.Calendar)
	 */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
		
    }

    /* (non-Javadoc)
	 * @see com.gtech.iarc.ishedule.model.IArcTaskConfig#setTaskSchedule(java.lang.String)
	 */
    public void setTaskSchedule(String taskSchedule) {
        this.taskSchedule = taskSchedule;
    }


    /* (non-Javadoc)
	 * @see com.gtech.iarc.ishedule.model.IArcTaskConfig#setPriority(java.lang.Long)
	 */
    public void setPriority(Long priority) {
        this.priority = priority;
    }

    /* (non-Javadoc)
	 * @see com.gtech.iarc.ishedule.model.IArcTaskConfig#setStartDate(java.util.Calendar)
	 */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /* (non-Javadoc)
	 * @see com.gtech.iarc.ishedule.model.IArcTaskConfig#setStatus(java.lang.String)
	 */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return Returns the job name used by Quartz. This is the another key to load the 
     * scheduled Arcjob except primary key
     * @hibernate.property column="TASK_SCHEDULE_NAME"
     */
    public String getTaskScheduleName() {
        return taskScheduleName;
    }

    /* (non-Javadoc)
	 * @see com.gtech.iarc.ishedule.model.IArcTaskConfig#setTaskScheduleName(java.lang.String)
	 */
    public void setTaskScheduleName(String taskScheduleName) {
        this.taskScheduleName = taskScheduleName;
    }


    // The following are Getter and Setter methods for non-persistence members

    /* (non-Javadoc)
	 * @see com.gtech.iarc.ishedule.model.IArcTaskConfig#getJobCronExpression()
	 */
    public String getJobCronExpression() {
        return jobCronExpression;
    }

    /* (non-Javadoc)
	 * @see com.gtech.iarc.ishedule.model.IArcTaskConfig#setJobCronExpression(java.lang.String)
	 */
    public void setJobCronExpression(String jobCronExpression) {
        this.jobCronExpression = jobCronExpression;
    }


    /* (non-Javadoc)
	 * @see com.gtech.iarc.ishedule.model.IArcTaskConfig#getDisplayEndDate()
	 */
    public String getDisplayEndDate() {
        return displayEndDate;
    }

    /* (non-Javadoc)
	 * @see com.gtech.iarc.ishedule.model.IArcTaskConfig#setDisplayEndDate(java.lang.String)
	 */
    public void setDisplayEndDate(String displayEndDate) {
        this.displayEndDate = displayEndDate;
    }

    /* (non-Javadoc)
	 * @see com.gtech.iarc.ishedule.model.IArcTaskConfig#getDisplayEndTime()
	 */
    public String getDisplayEndTime() {
        return displayEndTime;
    }

    /* (non-Javadoc)
	 * @see com.gtech.iarc.ishedule.model.IArcTaskConfig#setDisplayEndTime(java.lang.String)
	 */
    public void setDisplayEndTime(String displayEndTime) {
        this.displayEndTime = displayEndTime;
    }

    /* (non-Javadoc)
	 * @see com.gtech.iarc.ishedule.model.IArcTaskConfig#getDisplayStartDate()
	 */
    public String getDisplayStartDate() {
        return displayStartDate;
    }

    /* (non-Javadoc)
	 * @see com.gtech.iarc.ishedule.model.IArcTaskConfig#setDisplayStartDate(java.lang.String)
	 */
    public void setDisplayStartDate(String displayStartDate) {
        this.displayStartDate = displayStartDate;
    }

    /* (non-Javadoc)
	 * @see com.gtech.iarc.ishedule.model.IArcTaskConfig#getDisplayStartTime()
	 */
    public String getDisplayStartTime() {
        return displayStartTime;
    }

    /* (non-Javadoc)
	 * @see com.gtech.iarc.ishedule.model.IArcTaskConfig#setDisplayStartTime(java.lang.String)
	 */
    public void setDisplayStartTime(String displayStartTime) {
        this.displayStartTime = displayStartTime;
    }

    /* (non-Javadoc)
	 * @see com.gtech.iarc.ishedule.model.IArcTaskConfig#getDisplayPriority()
	 */
    public String getDisplayPriority() {
        return displayPriority;
    }

    /* (non-Javadoc)
	 * @see com.gtech.iarc.ishedule.model.IArcTaskConfig#setDisplayPriority(java.lang.String)
	 */
    public void setDisplayPriority(String displayPriority) {
        this.displayPriority = displayPriority;
    }
    
	private Boolean activeInd;
    private String createdBy;
    private String modifiedBy;
    private String inactBy;    
    private Timestamp inactDate;
    private Timestamp createdDate;
    private Timestamp modifiedDate;    
    /**
     * @return Returns the activeInd.
     * @hibernate.property column="ACTIVE_IND"
     */
	public Boolean getActiveInd() {
		return activeInd;
	}

	public void setActiveInd(Boolean activeInd) {
		this.activeInd = activeInd;
	}

    /**
     * @return Returns the createdBy.
     * @hibernate.property column="CREATED_BY"
     *
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @return Returns the createdDate.
     * @hibernate.property column="CREATED_DATE"
     */
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    /**
     * @return Returns the inactBy.
     * @hibernate.property column="INACT_BY"
     */
    public String getInactBy() {
        return inactBy;
    }

    /**
     * @return Returns the inactDate.
     * @hibernate.property column="INACT_DATE"
     */
    public Timestamp getInactDate() {
        return inactDate;
    }

    /**
     * @return Returns the modifiedBy.
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * @return Returns the modifiedDate.
     */
    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    /**
     * @param createdBy The createdBy to set.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @param createdDate The createdDate to set.
     */
    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @param inactBy The inactBy to set.
     */
    public void setInactBy(String inactBy) {
        this.inactBy = inactBy;
    }

    /**
     * @param inactDate The inactDate to set.
     */
    public void setInactDate(Timestamp inactDate) {
        this.inactDate = inactDate;
    }

    /**
     * @param modifiedBy The modifiedBy to set.
     */
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    /**
     * @param modifiedDate The modifiedDate to set.
     */
    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
    private byte[] toByteArray(Blob fromBlob) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            byte[] buf = new byte[4000];
            InputStream is = fromBlob.getBinaryStream();
            try {
                for (;;) {
                    int dataSize = is.read(buf);
                    if (dataSize == -1)
                        break;
                    baos.write(buf, 0, dataSize);
                }
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException ex) {
                    }
                }
            }
            return baos.toByteArray();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException ex) {
                }
            }
        }
    }

}
