// Copyright(c) 2011 gTech, All Rights Reserved.
package com.gtech.iarc.ischedule.core.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.SerializationUtils;
import org.hibernate.Hibernate;

import com.gtech.iarc.base.model.core.BaseObject;

/**
 * @author ZHIDAO
 */
@Entity
@Table(name = "TASK_SCHEDULE")
public class TaskSchedule extends BaseObject{

    
	/**
	 * 
	 */
	private static final long serialVersionUID = -5938085808975996287L;
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(name = "PRIORITY")
    private int priority;
	
	@Column(name = "STATUS")
    private String status;
	
	@Column(name = "START_DATE")
    private Date startDate;
	
	@Column(name = "END_DATE")
    private Date endDate;
	
	@Column(name = "MODE")
    private String taskScheduleMode;
	
	@Column(name = "JOB_CODE")
    private String jobScheduledCode;
	
	@Column(name = "DESC")
    private String description;
	
	@ManyToOne
	@JoinColumn(name = "TASK_DETAIL_ID")
    private TaskExecutionDetail taskExecutionDetail;   
	
	@Column(name = "EMAIL")
    private String notifyEmail; 
	
	@Column(name = "ACTIVE")
	private Boolean activeInd;
    
	@Column(name = "CRON_EXP")
	private String cronExpression;
	
	@Column(name = "CREATED_BY")
    private String createdBy;
	
	@Column(name = "CREATED_DATE")
    private Date createdDate;
	
//    private byte[] binFile;       
//    
//    public byte[] getBinFile() {
//        return binFile;
//    }
//    
//    public void setBinFile(byte[] binFile) {
//        this.binFile = binFile;
//    }  
//    
//    public Object getParameterObject() {
//        byte[] byteClass = this.getBinFile();  
//        if(byteClass==null) return null;
//        return SerializationUtils.deserialize(byteClass);
//    }    
//    
//    public void setParameterObject(Serializable parameterObject) {
//    	this.binFile = SerializationUtils.serialize( parameterObject);
//    }
//    
//    public void setBlobFile(Blob blobFile) {
//        this.binFile = this.toByteArray(blobFile);
//    }
//    /** Don't invoke this. Used by Hibernate only. */
//    public Blob getBlobFile() {
//        return Hibernate.createBlob(this.binFile);
//    }
//
//    private byte[] toByteArray(Blob fromBlob) {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        try {
//            byte[] buf = new byte[4000];
//            InputStream is = fromBlob.getBinaryStream();
//            try {
//                for (;;) {
//                    int dataSize = is.read(buf);
//                    if (dataSize == -1)
//                        break;
//                    baos.write(buf, 0, dataSize);
//                }
//            } finally {
//                if (is != null) {
//                    try {
//                        is.close();
//                    } catch (IOException ex) {
//                    }
//                }
//            }
//            return baos.toByteArray();
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } finally {
//            if (baos != null) {
//                try {
//                    baos.close();
//                } catch (IOException ex) {
//                }
//            }
//        }
//    }

// public Blob getParameterObjectInBlob() {
//     return ConvertUtils.convertSerializableObjectToBlob(parameterObject);
// }
//
// public void setParameterObjectInBlob(Blob blob) {
//     this.parameterObject = ConvertUtils.convertBlobToObject(blob);
// }    
    
    

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getTaskScheduleMode() {
		return taskScheduleMode;
	}

	public void setTaskScheduleMode(String taskScheduleMode) {
		this.taskScheduleMode = taskScheduleMode;
	}

	public String getJobScheduledCode() {
		return jobScheduledCode;
	}

	public void setJobScheduledCode(String jobScheduledCode) {
		this.jobScheduledCode = jobScheduledCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public TaskExecutionDetail getTaskExecutionDetail() {
		return taskExecutionDetail;
	}

	public void setTaskExecutionDetail(TaskExecutionDetail taskExecutionDetail) {
		this.taskExecutionDetail = taskExecutionDetail;
	}

	public String getNotifyEmail() {
		return notifyEmail;
	}

	public void setNotifyEmail(String notifyEmail) {
		this.notifyEmail = notifyEmail;
	}

	public Boolean getActiveInd() {
		return activeInd;
	}

	public void setActiveInd(Boolean activeInd) {
		this.activeInd = activeInd;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

}
