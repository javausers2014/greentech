
package com.gtech.iarc.ischedule.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.SchedulerException;

import com.gtech.iarc.ischedule.core.AbstractTask;
import com.gtech.iarc.ischedule.core.ArcScheduleExpressionUtil;
import com.gtech.iarc.ischedule.core.SchedulerControl;
import com.gtech.iarc.ischedule.core.model.ArcTaskAudit;
import com.gtech.iarc.ischedule.core.model.ArcTaskConfig;
import com.gtech.iarc.ischedule.core.model.ArcTaskDetail;
import com.gtech.iarc.ischedule.core.model.IArcTaskAudit;
import com.gtech.iarc.ischedule.core.model.IArcTaskConfig;
import com.gtech.iarc.ischedule.core.model.IArcTaskDetail;
import com.gtech.iarc.ischedule.core.model.IArcTaskScheduleInfo;
import com.gtech.iarc.ischedule.persistence.TaskConfigDAO;
import com.gtech.iarc.ischedule.persistence.TaskDetailDAO;
import com.gtech.iarc.ischedule.persistence.TaskGroupDAO;
import com.gtech.iarc.ischedule.service.context.SpringBeanServiceDelegator;

/**
 * 
 * @author ZHIDAO
 * @revision $Id$
 */
public class DefaultScheduleServiceManager implements ScheduleServiceManager {
    
    private static Log log = LogFactory.getLog(DefaultScheduleServiceManager.class);
    private SchedulerControl schedulerControl;
    private TaskConfigDAO taskConfigDAO;
    private TaskDetailDAO taskDetailDAO;
    private TaskGroupDAO taskGroupDAO ;
    
    
    /** Used by Spring framework to bind the <tt>scheduler</tt> to this manager. */
    public void setschedulerControl(SchedulerControl schedulerControl) {
        schedulerControl = schedulerControl;
    }

    /** Used by Spring framework to bind the <tt>taskConfigDAO</tt> to this manager. */
    public void setTaskConfigDAO(TaskConfigDAO taskConfigDAO) {
        this.taskConfigDAO = taskConfigDAO;
    }

    /** Used by Spring framework to bind the <tt>taskGroupDAO</tt> to this manager. */
    public void setTaskGroupDAO(TaskGroupDAO taskGroupDAO) {
        this.taskGroupDAO = taskGroupDAO;
    }
    /** Used by Spring framework to bind the <tt>taskDetailDAO</tt> to this manager. */
    public void setTaskDetailDAO(TaskDetailDAO taskDetailDAO) {
        this.taskDetailDAO = taskDetailDAO;
    }

    /**
     * Process:
     * <li>Saving Y3TaskConfig info into system.<br>
     * <li>Instantiate concrete class of Y3AbstractTask defined in Y3TaskDetail
     * <li>Convert schedule time of Y3TaskConfig to Quartz CRON Expression.
     * <li>Invoke Scheduler to activate task.
     * 
     * @see com.ScheduleServiceManager.scheduler.service.SchedulerServiceManager#activateTask(com.y3technologies.scheduler.model.Y3TaskConfig)
     * @param activeJob
     */
    public void activateTask(IArcTaskConfig activeJob) {
		log.debug("To activate a Y3 scheduled task ["
				+ activeJob.getTaskScheduleName() + "]\n");
		if (null == activeJob) {
			log.warn("Can't Activate a NULL Job!");
			return;
		}

		try {
			activeJob.setActiveInd(Boolean.TRUE);
			activeJob.setStatus(ScheduleServiceManager.ACTIVE_JOB_STATUS);

			taskConfigDAO.insertArcTaskConfig(activeJob);
			Long jobId = activeJob.getTaskConfigId();

			IArcTaskDetail taskDetail = activeJob.getArcTaskDetail() == null ? findTaskDetail(activeJob
					.getTaskDetailId())
					: activeJob.getArcTaskDetail();
			String className = taskDetail.getTaskDetailJavaClass();
			log.debug("To find the instance for new Arc job excution :\n["
					+ className + "]");

			AbstractTask scheduleActiveJob = SpringBeanServiceDelegator
					.getTaskInstance(activeJob.getTaskScheduleName(), jobId
							.toString(), className);

			Calendar date1 = Calendar.getInstance();
			date1.setTime(activeJob.getStartDate());
			scheduleActiveJob.setStartTime(date1);

			Calendar date2 = Calendar.getInstance();
			date2.setTime(activeJob.getEndDate());
			scheduleActiveJob.setEndTime(date2);

			scheduleActiveJob.setQuartzScheduledExpression(activeJob
					.getJobCronExpression());

			schedulerControl.activateJob(scheduleActiveJob);

			log
					.debug("To add/activate new job config for new Arc job instance to Quartz...\n");

		} catch (Exception e) {
			log.error("activateJob - " + e.getMessage());
			throw new RuntimeException(e.getMessage());
		}

	}

    /**
     * Process:
     * <li>Removing ArcTaskConfig info from system.<br>
     * <li>Instantiate concrete class of ArcAbstractTask defined in ArcTaskDetail
     * <li>Invoke Scheduler to delete task from schedule module.
     * 
     * @see com.ScheduleServiceManager.scheduler.service.SchedulerServiceManager#deleteTask(com.Arctechnologies.scheduler.model.ArcTaskConfig)
     * @param deleteTask
     * @throws SchedulerException 
     */
    public void deleteTask(IArcTaskConfig deleteTask) throws SchedulerException {
		if (null == deleteTask) {
			return;
		}
		taskConfigDAO.removeArcTaskConfig(deleteTask);

		Long jobTypeId = deleteTask.getTaskDetailId();

		IArcTaskDetail taskDetail = findTaskDetail(jobTypeId);
		String className = taskDetail.getTaskDetailJavaClass();

		AbstractTask scheduleActiveJobDelete = SpringBeanServiceDelegator
				.getTaskInstance(deleteTask.getTaskScheduleName(), deleteTask
						.getTaskConfigId().toString(), className);
		schedulerControl.deleteJob(scheduleActiveJobDelete);

	}

    /**
     * Process:
     * <li>Auditing ArcTaskConfig info in system.<br>
     * <li>Updating ArcTaskConfig status to SchedulerServiceManager.PAUSED_JOB_STATUS
     * <li>Invoke Scheduler to PAUSE task from schedule module.
     * 
	 * @see com.ScheduleServiceManager.scheduler.service.SchedulerServiceManager#pauseActiveTask(com.Arctechnologies.scheduler.model.ArcTaskConfig)
	 * @param pauseTask
     * @throws SchedulerException 
	 */
    public void pauseActiveTask(IArcTaskConfig pauseTask) throws SchedulerException {

    	IArcTaskConfig taskConfig = taskConfigDAO.getArcTaskConfig(pauseTask
				.getTaskConfigId());
		IArcTaskAudit jobAudit = new ArcTaskAudit();
		jobAudit.setTaskId(taskConfig.getTaskConfigId());
		jobAudit.setRequestedBy(taskConfig.getModifiedBy());
		jobAudit.setStatus(taskConfig.getStatus());
        jobAudit.setActiveInd(Boolean.TRUE);
        jobAudit.setCreatedBy(ScheduleServiceManager.SCHEDULER_USERNAME);
        jobAudit.setCreatedDate(new Timestamp((Calendar.getInstance()).getTimeInMillis()));
        jobAudit.setJobLog("Pause Active Task");
        
		taskConfig.setModifiedBy(pauseTask.getModifiedBy());
		taskConfig.setModifiedDate(pauseTask.getModifiedDate());
		taskConfig.setStatus(ScheduleServiceManager.PAUSED_JOB_STATUS);

		taskConfigDAO.updateArcTaskConfig(taskConfig);
		//dao.save(jobAudit);

		schedulerControl.pauseJob(taskConfig.getTaskScheduleName(), taskConfig
				.getArcTaskDetail().getTaskGroup().getTaskGroupShortName());
		log.debug("Active Task ["+pauseTask.getTaskScheduleName()+"] has been paused.");
	}

    /**
     * Process:
     * <li>Auditing ArcTaskConfig info in system.<br>
     * <li>Updating ArcTaskConfig status to SchedulerServiceManager.ACTIVE_JOB_STATUS
     * <li>Invoke Scheduler to RESUME task from schedule module.
     * 
	 * @see com.ScheduleServiceManager.scheduler.service.SchedulerServiceManager#resumePausedTask(com.Arctechnologies.scheduler.model.ArcTaskConfig)
	 * @param pauseTask
     * @throws SchedulerException 
	 */
    public void resumePausedTask(IArcTaskConfig pausedTask) throws SchedulerException {

    	IArcTaskConfig taskConfig = taskConfigDAO.getArcTaskConfig(pausedTask
				.getTaskConfigId());
		IArcTaskAudit jobAudit = new ArcTaskAudit();
		jobAudit.setTaskId(taskConfig.getTaskConfigId());
		jobAudit.setRequestedBy(taskConfig.getModifiedBy());
		jobAudit.setStatus(taskConfig.getStatus());
        jobAudit.setActiveInd(Boolean.TRUE);
        jobAudit.setCreatedBy(ScheduleServiceManager.SCHEDULER_USERNAME);
        jobAudit.setCreatedDate(new Timestamp((Calendar.getInstance()).getTimeInMillis()));
        jobAudit.setJobLog("Resume Paused Task");
        
		taskConfig.setModifiedBy(pausedTask.getModifiedBy());
		taskConfig.setModifiedDate(pausedTask.getModifiedDate());
		taskConfig.setStatus(ScheduleServiceManager.ACTIVE_JOB_STATUS);

		taskConfigDAO.updateArcTaskConfig(taskConfig);
		//dao.save(jobAudit);

		schedulerControl.resumeJob(taskConfig.getTaskScheduleName(), taskConfig
				.getArcTaskDetail().getTaskGroup().getTaskGroupShortName());
		log.debug("Paused Task ["+pausedTask.getTaskScheduleName()+"] has been resumed.");
	}

    
	/**
	 * @throws ParseException 
	 * @throws SchedulerException 
	 * 
	 */
    public void reScheduleTask(IArcTaskConfig task) throws SchedulerException, ParseException {
		if (null == task) {
			return;
		}

		Long taskDetailId = task.getTaskDetailId();

		task.setActiveInd(Boolean.TRUE);
		task.setStatus(ScheduleServiceManager.ACTIVE_JOB_STATUS);

		taskConfigDAO.updateArcTaskConfig(task);

		IArcTaskDetail taskDetail = findTaskDetail(taskDetailId);
		String className = taskDetail.getTaskDetailJavaClass();

		AbstractTask scheduleTriggerJob = SpringBeanServiceDelegator
				.getTaskInstance(task.getTaskScheduleName(), task
						.getTaskConfigId().toString(), className);

		Calendar date1 = Calendar.getInstance();
		date1.setTime(task.getStartDate());

		Calendar date2 = Calendar.getInstance();
		date2.setTime(task.getEndDate());

		scheduleTriggerJob.setStartTime(date1);
		scheduleTriggerJob.setEndTime(date2);
		scheduleTriggerJob.setQuartzScheduledExpression(task
				.getJobCronExpression());
		log.debug("triggerJob - startTime" + scheduleTriggerJob.getStartTime());
		log.debug("triggerJob - EndTime" + scheduleTriggerJob.getEndTime());

		schedulerControl.rescheduleJob(scheduleTriggerJob);
		log.debug("Task ["+task.getTaskScheduleName()+"] has been triggered.");
	}

    public List findActiveTaskConfigByUser(String userName) {
        List taskList = null;

        if (!userName.equals("")) {
            ArcTaskConfig taskConfig = new ArcTaskConfig();
            taskConfig.setCreatedBy(userName);
            taskConfig.setStatus(ACTIVE_IND_ACTIVE);
            taskList = taskConfigDAO.findArcTaskConfig(taskConfig);
        } else {
            log.debug("findActiveJobByUser - userName is NULL!");
        }

        return taskList;
    }

    public List findActiveTaskConfigByGroup(String taskGroupName) {
        
       
        List<IArcTaskConfig> activeJobList = new ArrayList<IArcTaskConfig>();

        if (!taskGroupName.equals("")) {
            Long taskGroupId = taskGroupDAO.getTaskGroup(taskGroupName).getTaskGroupId();
            
            Long taskDetailId = taskDetailDAO.getTaskDetail(taskGroupId.toString()).getTaskDetailId();
            
            List allJobList = taskConfigDAO.findArcTaskConfigListByTaskDetail(taskDetailId);
            Iterator all = allJobList.iterator();
            while (all.hasNext()) {
                IArcTaskConfig taskConfig = (IArcTaskConfig) all.next();

                if (taskConfig.getStatus() == ACTIVE_IND_ACTIVE) {
                    activeJobList.add(taskConfig);
                }
            }
        } else {
            log.debug("findActiveJobByGroup - jobGroupName is NULL!");
        }

        return activeJobList;
    }

    public List findRunningTaskConfigByUser(String userName) {
        
        if(userName==null||userName.trim().length()==0){
            log.warn("find Running TaskConfig By User - userName is NULL!");
            return Collections.EMPTY_LIST;
        }
        
        ArcTaskConfig sample = new ArcTaskConfig();
        sample.setCreatedBy(userName);
        sample.setStatus(ACTIVE_IND_ACTIVE);
        return taskConfigDAO.findArcTaskConfig(sample);
    }

    public List findRunningTaskConfigByStatus(IArcTaskConfig task) {
        if(task==null){
            log.warn("find Running TaskConfig By sample - sample is NULL!");
            return Collections.EMPTY_LIST;
        }
        
        task.setStatus(ScheduleServiceManager.ACTIVE_JOB_STATUS);
        return taskConfigDAO.findArcTaskConfig(task);
    }

    public List findTaskConfig(String username) {
        ArcTaskConfig task = new ArcTaskConfig();
        List user = null;

        if (!username.equals("")) {
            task.setCreatedBy(username);

            user = taskConfigDAO.findArcTaskConfig(task);
        } else {
            log.debug("findJob - username is NULL!");
        }

        return user;
    }

    private IArcTaskDetail findTaskDetail(Long taskDetailId) {
        IArcTaskDetail taskDetail = new ArcTaskDetail();
        taskDetail.setTaskDetailId(taskDetailId);

        // Find the Job Class Name for this Job Type ID
        List jobTypeList = taskDetailDAO.findTaskDetail(taskDetail);
        IArcTaskDetail jobType = null;

        if (null != jobTypeList) {
            Iterator i = jobTypeList.iterator();

            while (i.hasNext()) {
                jobType = (IArcTaskDetail) i.next();

                if (jobType.getTaskDetailId().equals(taskDetailId)) {
                    return jobType;
                }
            } // End of 'while' loop
        }

        return jobType;
    }

    /**
     * 
     * @see com.ScheduleServiceManager.scheduler.service.SchedulerServiceManager#updateTaskDetail(com.Arctechnologies.scheduler.model.ArcTaskDetail)
     * @param taskDetail
     */
    public void updateTaskDetail(IArcTaskDetail taskDetail) {
        if (null != taskDetail) {
        	        	
            taskDetailDAO.updateTaskDetail(taskDetail);
        }
    }

    /**
     * 
     * @see com.ScheduleServiceManager.scheduler.service.SchedulerServiceManager#findTaskDetail(com.Arctechnologies.scheduler.model.ArcTaskDetail)
     * @param taskDetail
     * @return
     */
    public List findTaskDetail(IArcTaskDetail taskDetail) {
        
        List taskList = new ArrayList();

        if (null != taskDetail) {
            taskList = taskDetailDAO.findTaskDetail(taskDetail);
        }
        return taskList;
    }

    /**
     * 
     * @see com.ScheduleServiceManager.scheduler.service.SchedulerServiceManager#getTaskDetail(java.lang.String)
     * @param taskDetailShortName
     * @return
     */
    public IArcTaskDetail getTaskDetail(String taskDetailShortName) {
        IArcTaskDetail taskDetail = new ArcTaskDetail();

        if (!taskDetailShortName.equals("") && !taskDetailShortName.equals(null)) {
            taskDetail = taskDetailDAO.getTaskDetail(taskDetailShortName);
        }

        return taskDetail;
    }

    public List findTaskConfig(String taskDetailSName, String taskScheduleName) {
    	return taskConfigDAO.findArcTaskConfig(taskDetailSName,taskScheduleName);
//        log.debug("searching taskCOnfig with: taskDetail short Name "+taskDetailSName
//        		+" | task schedule Name "+taskScheduleName);        
//        
//        IArcTaskConfig taskConfig = new ArcTaskConfig();
//        if(taskDetailSName!=null && taskDetailSName.trim().length()!=0){
//            IArcTaskDetail detail = this.getTaskDetail(taskDetailSName);
//            
//            if(detail!=null)log.debug("Found task detail with "+taskDetailSName);
//            
//            taskConfig.setArcTaskDetail(detail);
//        }else{
//        	taskConfig.setArcTaskDetail(new ArcTaskDetail());
//        }
//        taskConfig.setTaskScheduleName(taskScheduleName);
//        taskConfig.setActiveInd(Boolean.TRUE);
//
//        return taskConfigDAO.findArcTaskConfig(taskConfig);
    }

	public void saveTaskDetail(IArcTaskDetail taskDetail) {
		//TODO
	}


	public IArcTaskConfig getTaskConfig(Long taskConfigId) {
		
		return taskConfigDAO.getArcTaskConfig(taskConfigId);
	}

    /**
     *
     * @param taskScheduleInfo
     * @return
     * @see com.ScheduleServiceManager.scheduler.service.SchedulerServiceManager#convertToCronExpression(com.Arctechnologies.scheduler.model.IArcTaskScheduleInfo)
     */
    public String convertToCronExpression(IArcTaskScheduleInfo taskScheduleInfo) {
        return ArcScheduleExpressionUtil.convertToCronExpression(taskScheduleInfo);
    }

    /**
     *
     * @param startDate
     * @param displayStartTime
     * @return
     * @throws ParseException 
     * @see com.ScheduleServiceManager.scheduler.service.SchedulerServiceManager#convertToDateTime(java.util.Date, java.lang.String)
     */
    public Date convertToDateTime(Date startDate, String displayStartTime) throws ParseException {
        
        return ArcScheduleExpressionUtil.convertToDateTime(startDate, displayStartTime);
    }
}
