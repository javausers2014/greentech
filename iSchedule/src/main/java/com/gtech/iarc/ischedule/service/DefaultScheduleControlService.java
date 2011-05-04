
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

import com.gtech.iarc.ischedule.core.AbstractSpringBeanTask;
import com.gtech.iarc.ischedule.core.ExpressionUtil;
import com.gtech.iarc.ischedule.core.SchedulerControl;
import com.gtech.iarc.ischedule.core.model.DefaultTaskExecutionAudit;
import com.gtech.iarc.ischedule.core.model.DefaultTaskExecutionDetail;
import com.gtech.iarc.ischedule.core.model.DefaultTaskScheduleRequirementContext;
import com.gtech.iarc.ischedule.core.model.TaskExecutionAudit;
import com.gtech.iarc.ischedule.core.model.TaskExecutionDetail;
import com.gtech.iarc.ischedule.core.model.TaskScheduleDateTimeInfo;
import com.gtech.iarc.ischedule.core.model.TaskScheduleRequirementContext;
import com.gtech.iarc.ischedule.persistence.TaskRepository;
import com.gtech.iarc.ischedule.service.context.SpringBeanServiceDelegator;

/**
 * 
 * @author ZHIDAO
 * @revision $Id$
 */
public class DefaultScheduleControlService implements ScheduleControlService {
    
    private static Log log = LogFactory.getLog(DefaultScheduleControlService.class);
    private SchedulerControl schedulerControl;
    private TaskRepository taskRepository;

    public void setSchedulerControl(SchedulerControl schedulerControl) {
		this.schedulerControl = schedulerControl;
	}



	public void setTaskRepository(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}

    /**
     * Process:
     * <li>Saving Y3TaskConfig info into system.<br>
     * <li>Instantiate concrete class of Y3AbstractTask defined in Y3TaskDetail
     * <li>Convert schedule time of Y3TaskConfig to Quartz CRON Expression.
     * <li>Invoke Scheduler to activate task.
     * 
     * @see com.ScheduleControlService.scheduler.service.SchedulerServiceManager#activateTask(com.y3technologies.scheduler.model.Y3TaskConfig)
     * @param activeJob
     */
    public void activateTask(TaskScheduleRequirementContext activeJob) {
		log.debug("To activate a Y3 scheduled task ["
				+ activeJob.getTaskScheduleName() + "]\n");
		if (null == activeJob) {
			log.warn("Can't Activate a NULL Job!");
			return;
		}

		try {
			activeJob.setActiveInd(Boolean.TRUE);
			activeJob.setStatus(JOB_STATUS_ACTIVE);

			taskRepository.insertArcTaskConfig(activeJob);
			Long jobId = activeJob.getId();

			TaskExecutionDetail taskDetail = activeJob.getArcTaskDetail() == null ? findTaskDetail(activeJob
					.getTaskDetailId())
					: activeJob.getArcTaskDetail();
			String className = taskDetail.getTaskDetailJavaClass();
			log.debug("To find the instance for new Arc job excution :\n["
					+ className + "]");

			AbstractSpringBeanTask scheduleActiveJob = SpringBeanServiceDelegator
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
     * @see com.ScheduleControlService.scheduler.service.SchedulerServiceManager#deleteTask(com.DefaultTaskScheduleRequirementContext.scheduler.model.ArcTaskConfig)
     * @param deleteTask
     * @throws SchedulerException 
     */
    public void deleteTask(TaskScheduleRequirementContext deleteTask) throws SchedulerException {
		if (null == deleteTask) {
			return;
		}
		taskRepository.removeArcTaskConfig(deleteTask);

		Long jobTypeId = deleteTask.getTaskDetailId();

		TaskExecutionDetail taskDetail = findTaskDetail(jobTypeId);
		String className = taskDetail.getTaskDetailJavaClass();

		AbstractSpringBeanTask scheduleActiveJobDelete = SpringBeanServiceDelegator
				.getTaskInstance(deleteTask.getTaskScheduleName(), deleteTask
						.getId().toString(), className);
		schedulerControl.deleteJob(scheduleActiveJobDelete);

	}

    /**
     * Process:
     * <li>Auditing ArcTaskConfig info in system.<br>
     * <li>Updating ArcTaskConfig status to SchedulerServiceManager.PAUSED_JOB_STATUS
     * <li>Invoke Scheduler to PAUSE task from schedule module.
     * 
	 * @see com.ScheduleControlService.scheduler.service.SchedulerServiceManager#pauseActiveTask(com.DefaultTaskScheduleRequirementContext.scheduler.model.ArcTaskConfig)
	 * @param pauseTask
     * @throws SchedulerException 
	 */
    public void pauseActiveTask(TaskScheduleRequirementContext pauseTask) throws SchedulerException {

    	TaskScheduleRequirementContext taskConfig = taskRepository.getArcTaskConfig(pauseTask
				.getId());
		TaskExecutionAudit jobAudit = new DefaultTaskExecutionAudit();
		jobAudit.setTaskId(taskConfig.getId());
		jobAudit.setRequestedBy(taskConfig.getModifiedBy());
		jobAudit.setStatus(taskConfig.getStatus());
        jobAudit.setActiveInd(Boolean.TRUE);
        jobAudit.setCreatedBy(ScheduleControlService.SCHEDULER_USERNAME);
        jobAudit.setCreatedDate(new Timestamp((Calendar.getInstance()).getTimeInMillis()));
        jobAudit.setJobLog("Pause Active Task");
        
		taskConfig.setModifiedBy(pauseTask.getModifiedBy());
		taskConfig.setModifiedDate(pauseTask.getModifiedDate());
		taskConfig.setStatus(JOB_STATUS_PAUSED);

		taskRepository.updateArcTaskConfig(taskConfig);
		//dao.save(jobAudit);

		schedulerControl.pauseJob(taskConfig.getTaskScheduleName(), taskConfig
				.getArcTaskDetail().getTaskGroupCode());
		log.debug("Active Task ["+pauseTask.getTaskScheduleName()+"] has been paused.");
	}

    /**
     * Process:
     * <li>Auditing ArcTaskConfig info in system.<br>
     * <li>Updating ArcTaskConfig status to SchedulerServiceManager.ACTIVE_JOB_STATUS
     * <li>Invoke Scheduler to RESUME task from schedule module.
     * 
	 * @see com.ScheduleControlService.scheduler.service.SchedulerServiceManager#resumePausedTask(com.DefaultTaskScheduleRequirementContext.scheduler.model.ArcTaskConfig)
	 * @param pauseTask
     * @throws SchedulerException 
	 */
    public void resumePausedTask(TaskScheduleRequirementContext pausedTask) throws SchedulerException {

    	TaskScheduleRequirementContext taskConfig = taskRepository.getArcTaskConfig(pausedTask
				.getId());
		TaskExecutionAudit jobAudit = new DefaultTaskExecutionAudit();
		jobAudit.setTaskId(taskConfig.getId());
		jobAudit.setRequestedBy(taskConfig.getModifiedBy());
		jobAudit.setStatus(taskConfig.getStatus());
        jobAudit.setActiveInd(Boolean.TRUE);
        jobAudit.setCreatedBy(ScheduleControlService.SCHEDULER_USERNAME);
        jobAudit.setCreatedDate(new Timestamp((Calendar.getInstance()).getTimeInMillis()));
        jobAudit.setJobLog("Resume Paused Task");
        
		taskConfig.setModifiedBy(pausedTask.getModifiedBy());
		taskConfig.setModifiedDate(pausedTask.getModifiedDate());
		taskConfig.setStatus(JOB_STATUS_ACTIVE);

		taskRepository.updateArcTaskConfig(taskConfig);
		//dao.save(jobAudit);

		schedulerControl.resumeJob(taskConfig.getTaskScheduleName(), taskConfig
				.getArcTaskDetail().getTaskGroupCode());
		log.debug("Paused Task ["+pausedTask.getTaskScheduleName()+"] has been resumed.");
	}

    
	/**
	 * @throws ParseException 
	 * @throws SchedulerException 
	 * 
	 */
    public void reScheduleTask(TaskScheduleRequirementContext task) throws SchedulerException, ParseException {
		if (null == task) {
			return;
		}

		Long taskDetailId = task.getTaskDetailId();

		task.setActiveInd(Boolean.TRUE);
		task.setStatus(JOB_STATUS_ACTIVE);

		taskRepository.updateArcTaskConfig(task);

		TaskExecutionDetail taskDetail = findTaskDetail(taskDetailId);
		String className = taskDetail.getTaskDetailJavaClass();

		AbstractSpringBeanTask scheduleTriggerJob = SpringBeanServiceDelegator
				.getTaskInstance(task.getTaskScheduleName(), task
						.getId().toString(), className);

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
            DefaultTaskScheduleRequirementContext taskConfig = new DefaultTaskScheduleRequirementContext();
            taskConfig.setCreatedBy(userName);
            taskConfig.setStatus(ACTIVE_IND_ACTIVE);
            taskList = taskRepository.findArcTaskConfig(taskConfig);
        } else {
            log.debug("findActiveJobByUser - userName is NULL!");
        }

        return taskList;
    }

    public List findActiveTaskConfigByGroup(String taskGroupCode) {
        
       
        List<TaskScheduleRequirementContext> activeJobList = new ArrayList<TaskScheduleRequirementContext>();

        if (!taskGroupCode.equals("")) {
        	
            Long taskDetailId = taskRepository.getTaskDetail(taskGroupCode).getId();
            
            List allJobList = taskRepository.findArcTaskConfigListByTaskDetail(taskDetailId);
            Iterator all = allJobList.iterator();
            while (all.hasNext()) {
                TaskScheduleRequirementContext taskConfig = (TaskScheduleRequirementContext) all.next();

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
        
        DefaultTaskScheduleRequirementContext sample = new DefaultTaskScheduleRequirementContext();
        sample.setCreatedBy(userName);
        sample.setStatus(ACTIVE_IND_ACTIVE);
        return taskRepository.findArcTaskConfig(sample);
    }

    public List<TaskScheduleRequirementContext> findRunningTaskConfigByStatus(TaskScheduleRequirementContext task) {
        if(task==null){
            log.warn("find Running TaskConfig By sample - sample is NULL!");
            return Collections.emptyList();
        }
        
        task.setStatus(JOB_STATUS_ACTIVE);
        return taskRepository.findArcTaskConfig(task);
    }

    public List<TaskScheduleRequirementContext> findTaskConfig(String username) {
        DefaultTaskScheduleRequirementContext task = new DefaultTaskScheduleRequirementContext();
        List<TaskScheduleRequirementContext> user = null;

        if (!username.equals("")) {
            task.setCreatedBy(username);

            user = taskRepository.findArcTaskConfig(task);
        } else {
            log.debug("findJob - username is NULL!");
        }

        return user;
    }

    private TaskExecutionDetail findTaskDetail(Long taskDetailId) {
        TaskExecutionDetail taskDetail = new DefaultTaskExecutionDetail();
        taskDetail.setId(taskDetailId);

        // Find the Job Class Name for this Job Type ID
        List jobTypeList = taskRepository.findTaskDetail(taskDetail);
        TaskExecutionDetail jobType = null;

        if (null != jobTypeList) {
            Iterator i = jobTypeList.iterator();

            while (i.hasNext()) {
                jobType = (TaskExecutionDetail) i.next();

                if (jobType.getId().equals(taskDetailId)) {
                    return jobType;
                }
            } // End of 'while' loop
        }

        return jobType;
    }

    /**
     * 
     * @see com.ScheduleControlService.scheduler.service.SchedulerServiceManager#updateTaskDetail(com.DefaultTaskExecutionDetail.scheduler.model.ArcTaskDetail)
     * @param taskDetail
     */
    public void updateTaskDetail(TaskExecutionDetail taskDetail) {
        if (null != taskDetail) {
        	        	
            taskRepository.updateTaskDetail(taskDetail);
        }
    }

    /**
     * 
     * @see com.ScheduleControlService.scheduler.service.SchedulerServiceManager#findTaskDetail(com.DefaultTaskExecutionDetail.scheduler.model.ArcTaskDetail)
     * @param taskDetail
     * @return
     */
    public List<TaskExecutionDetail> findTaskDetail(TaskExecutionDetail taskDetail) {
        
        List<TaskExecutionDetail> taskList = new ArrayList<TaskExecutionDetail>();

        if (null != taskDetail) {
            taskList = taskRepository.findTaskDetail(taskDetail);
        }
        return taskList;
    }

    /**
     * 
     * @see com.ScheduleControlService.scheduler.service.SchedulerServiceManager#getTaskDetail(java.lang.String)
     * @param taskDetailShortName
     * @return
     */
    public TaskExecutionDetail getTaskDetail(String taskDetailShortName) {
        TaskExecutionDetail taskDetail = new DefaultTaskExecutionDetail();

        if (!taskDetailShortName.equals("") && !taskDetailShortName.equals(null)) {
            taskDetail = taskRepository.getTaskDetail(taskDetailShortName);
        }

        return taskDetail;
    }

    public List<TaskScheduleRequirementContext> findTaskConfig(String taskDetailSName, String taskScheduleName) {
    	return taskRepository.findArcTaskConfig(taskDetailSName,taskScheduleName);
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
//        return taskRepository.findArcTaskConfig(taskConfig);
    }

	public void saveTaskDetail(TaskExecutionDetail taskDetail) {
		//TODO
	}


	public TaskScheduleRequirementContext getTaskConfig(Long taskConfigId) {
		
		return taskRepository.getArcTaskConfig(taskConfigId);
	}

    /**
     *
     * @param taskScheduleInfo
     * @return
     * @see com.ScheduleControlService.scheduler.service.SchedulerServiceManager#convertToCronExpression(com.gtech.iarc.ischedule.core.TaskScheduleDateTimeInfo.scheduler.model.IArcTaskScheduleInfo)
     */
    public String convertToCronExpression(TaskScheduleDateTimeInfo taskScheduleInfo) {
        return ExpressionUtil.convertToCronExpression(taskScheduleInfo);
    }

    /**
     *
     * @param startDate
     * @param displayStartTime
     * @return
     * @throws ParseException 
     * @see com.ScheduleControlService.scheduler.service.SchedulerServiceManager#convertToDateTime(java.util.Date, java.lang.String)
     */
    public Date convertToDateTime(Date startDate, String displayStartTime) throws ParseException {
        
        return ExpressionUtil.convertToDateTime(startDate, displayStartTime);
    }
}
