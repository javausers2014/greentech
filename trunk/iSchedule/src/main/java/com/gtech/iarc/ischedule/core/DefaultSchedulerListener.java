package com.gtech.iarc.ischedule.core;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.SchedulerListener;
import org.quartz.Trigger;

import com.gtech.iarc.ischedule.ScheduleConstants;
import com.gtech.iarc.ischedule.core.model.TaskExecutionAudit;
import com.gtech.iarc.ischedule.core.model.TaskScheduleRequirementContext;
import com.gtech.iarc.ischedule.repository.TaskRepository;



/**
 * Class receiving notification of events within the Scheduler itself<br>
 * - not necessarily events related to a specific trigger or job. <br><br>
 * Scheduler-related events include: 
 * <li>the addition,pause/resume and removal of a job/trigger
 * <li>a serious error within the scheduler
 * <li>notification of the scheduler being shutdown
 * <li>and others....
 * @author ZHIDAO
 * 
 */
public class DefaultSchedulerListener implements SchedulerListener {
	
    private static Log log = LogFactory.getLog(DefaultSchedulerListener.class);
    private static HashMap<Long, String> statusMap = new HashMap<Long, String>();
    private static String COMPLETED = "COMPLETED";
    private static String COMPLETED_WITH_ERROR = "COMPLETED_WITH_ERROR";
    private static String ERROR = "ERROR";


    public synchronized void updateCompletedStatus(Long jobId) {
        log.debug("Updating COMPLETED status for Job " + jobId);

        String status = (String) statusMap.get(jobId);

        if (null != status) {
            if (ERROR.equals(status)) {
                log.debug("Updating COMPLETED_WITH_ERROR status for Job " +
                    jobId);
                statusMap.put(jobId, COMPLETED_WITH_ERROR);

                return;
            }
        }

        statusMap.put(jobId, COMPLETED);
    }


    public synchronized void updateErrorStatus(Long jobId) {
        log.debug("Updating ERROR status for Job " + jobId);
        statusMap.put(jobId, ERROR);
    }


    public boolean isJobCompletedWithError(Long jobId) {
        String status = (String) statusMap.get(jobId);

        if (null != status) {
            return COMPLETED_WITH_ERROR.equals(status);
        }

        return false;
    }
    
    private TaskRepository taskRepository;


    /**
     * 
     * @see org.quartz.SchedulerListener#jobScheduled(org.quartz.Trigger)
     * @param trigger
     */
    public void jobScheduled(Trigger trigger) {
        try {
        	TaskScheduleRequirementContext job = taskRepository.getArcTaskConfig(trigger.getJobName());

            if (job != null) {
                TaskExecutionAudit jobAudit = new TaskExecutionAudit();
//                jobAudit.setActiveInd(ArcSchedulerConstants.JOB_ACTIVE_IND);
//                jobAudit.setCreatedBy(ArcSchedulerConstants.SCHEDULER_USERNAME);
//                jobAudit.setCreatedDate( new Timestamp((Calendar.getInstance()).getTimeInMillis()));

                jobAudit.setTaskId(job.getId());
                jobAudit.setStatus(ScheduleConstants.JOB_STATUS_ACTIVE);

                taskRepository.insertTaskAudit(jobAudit);
            }
        } catch (Exception e) {           
            updateErrorStatus(Long.valueOf(trigger
                    .getJobGroup()));
            log.error("jobScheduled - " + e.getMessage());
            throw new RuntimeException(e.getCause());
        }

        log.debug("jobScheduled - " + trigger.getJobName() + " of Job Group "
                + trigger.getJobGroup() + " has been scheduled.");
        log.info("jobScheduled - " + trigger.getJobName() + " of Job Group "
                + trigger.getJobGroup() + " has been scheduled.");
    }

    /**
     * 
     * @see org.quartz.SchedulerListener#jobsPaused(java.lang.String, java.lang.String)
     * @param jobName
     * @param jobGroup
     */
    public void jobsPaused(String jobName, String jobGroup) {
        try {
        	TaskScheduleRequirementContext job = taskRepository.getArcTaskConfig(jobName);

            TaskExecutionAudit jobAudit = new TaskExecutionAudit();
//            jobAudit.setActiveInd(ArcSchedulerConstants.JOB_ACTIVE_IND);
//            jobAudit.setCreatedBy(ArcSchedulerConstants.SCHEDULER_USERNAME);
//            jobAudit.setCreatedDate( new Timestamp((Calendar.getInstance()).getTimeInMillis()));
            jobAudit.setTaskId(job.getId());
            //jobAudit.setRequestedBy(job.getModifiedBy());
            jobAudit.setStatus(ScheduleConstants.JOB_STATUS_PAUSED);

            taskRepository.insertTaskAudit(jobAudit);
        } catch (Exception e) {
            updateErrorStatus(Long.valueOf(jobGroup));
            log.error("jobsPaused - " + e.getMessage());
            throw new RuntimeException(e.getCause());
        }

        log.info("jobsPaused - " + jobName + " of Job Group " + jobGroup
                + " has been paused.");
    }

    /**
     * 
     * @see org.quartz.SchedulerListener#jobsResumed(java.lang.String, java.lang.String)
     * @param jobName
     * @param jobGroup
     */
    public void jobsResumed(String jobName, String jobGroup) {
        try {
        	TaskScheduleRequirementContext job = taskRepository.getArcTaskConfig(jobName);

            TaskExecutionAudit jobAudit = new TaskExecutionAudit();
//            jobAudit.setActiveInd(ArcSchedulerConstants.JOB_ACTIVE_IND);
//            jobAudit.setCreatedBy(ArcSchedulerConstants.SCHEDULER_USERNAME);
//            jobAudit.setCreatedDate( new Timestamp((Calendar.getInstance()).getTimeInMillis()));
            jobAudit.setTaskId(job.getId());
            //jobAudit.setRequestedBy(job.getModifiedBy());
            jobAudit.setStatus(ScheduleConstants.JOB_STATUS_ACTIVE);

            taskRepository.insertTaskAudit(jobAudit);
        } catch (Exception e) {
            updateErrorStatus(Long.valueOf(jobGroup));
            log.error("jobsResumed - " + e.getMessage());
            throw new RuntimeException(e.getCause());
        }

        log.info("jobsResumed - " + jobName + " of Job Group " + jobGroup
                + " has been resumed.");
    }

    /**
     * 
     * @see org.quartz.SchedulerListener#jobUnscheduled(java.lang.String, java.lang.String)
     * @param triggerName
     * @param triggerGroup
     */
    public void jobUnscheduled(String triggerName, String triggerGroup) {
        try {
        	TaskScheduleRequirementContext job = taskRepository.getArcTaskConfig(triggerName);

            TaskExecutionAudit jobAudit = new TaskExecutionAudit();
//            jobAudit.setActiveInd(ArcSchedulerConstants.JOB_ACTIVE_IND);
//            jobAudit.setCreatedBy(ArcSchedulerConstants.SCHEDULER_USERNAME);
//            jobAudit.setCreatedDate( new Timestamp((Calendar.getInstance()).getTimeInMillis()));
//            jobAudit.setInactBy(ArcSchedulerConstants.SCHEDULER_USERNAME);
//            jobAudit.setInactDate( new Timestamp((Calendar.getInstance()).getTimeInMillis()));
            jobAudit.setTaskId(job.getId());
            //jobAudit.setRequestedBy(job.getModifiedBy());
            jobAudit.setStatus(ScheduleConstants.JOB_STATUS_DELETED);

            taskRepository.insertTaskAudit(jobAudit);
        } catch (Exception e) {
            updateErrorStatus(Long.valueOf(triggerGroup));
            log.error("triggerFinalized - " + e.getMessage());
            throw new RuntimeException(e.getCause());
        }

        log.info("jobUnscheduled - " + triggerName + " of Trigger Group "
                + triggerGroup + " has been unscheduled.");
    }

    /**
     * 
     * @see org.quartz.SchedulerListener#schedulerError(java.lang.String, org.quartz.SchedulerException)
     * @param msg
     * @param cause
     */
    public void schedulerError(String msg, SchedulerException cause) {
        cause.printStackTrace();
        log.error("schedulerError - Error message: " + msg);
        log.error("schedulerError - Cause of error: " + cause);
    }

    /**
     * 
     * @see org.quartz.SchedulerListener#schedulerShutdown()
     */
    public void schedulerShutdown() {
        log.info("schedulerShutdown - Scheduler has been shutdown.");
    }

    /**
     * 
     * @see org.quartz.SchedulerListener#triggerFinalized(org.quartz.Trigger)
     * @param trigger
     */
    public void triggerFinalized(Trigger trigger) {
        try {
        	TaskScheduleRequirementContext job = taskRepository.getArcTaskConfig(trigger.getJobName());
            job.setStatus(ScheduleConstants.JOB_STATUS_COMPLETE);
            taskRepository.updateArcTaskConfig(job);

            TaskExecutionAudit jobAudit = new TaskExecutionAudit();
            jobAudit.setCompleteDate(new Date());
            jobAudit.setTaskId(job.getId());

            if (isJobCompletedWithError(Long.valueOf(trigger
                    .getJobGroup()))) {
                jobAudit
                        .setStatus(ScheduleConstants.JOB_STATUS_COMPLETEWITHERROR);
            } else {
                jobAudit.setStatus(ScheduleConstants.JOB_STATUS_COMPLETE);
            }

           // jobAuditDAO.insertJobAudit(jobAudit);
        } catch (Exception e) {
            updateErrorStatus(Long.valueOf(trigger
                    .getJobGroup()));
            log.error("triggerFinalized - " + e.getMessage());
            throw new RuntimeException(e.getCause());
        }

        log.debug("triggerFinalized - Job ID: " + trigger.getJobGroup());
        log.info("triggerFinalized - Job ID: " + trigger.getJobGroup());
    }

    /**
     * 
     * @see org.quartz.SchedulerListener#triggersPaused(java.lang.String, java.lang.String)
     * @param triggerName
     * @param triggerGroup
     */
    public void triggersPaused(String triggerName, String triggerGroup) {
        log.debug("triggersPaused - " + triggerName + " of Trigger Group "
                + triggerGroup + " has been paused.");
        log.info("triggersPaused - " + triggerName + " of Trigger Group "
                + triggerGroup + " has been paused.");
    }

    /**
     * 
     * @see org.quartz.SchedulerListener#triggersResumed(java.lang.String, java.lang.String)
     * @param triggerName
     * @param triggerGroup
     */
    public void triggersResumed(String triggerName, String triggerGroup) {
        log.debug("triggersResumed - " + triggerName + " of Trigger Group "
                + triggerGroup + " has been resumed.");
        log.info("triggersResumed - " + triggerName + " of Trigger Group "
                + triggerGroup + " has been resumed.");
    }

	public void jobAdded(JobDetail jobDetail) {
		
	}

	
	public void jobDeleted(String jobName, String groupName) {
		// TODO Auto-generated method stub
		
	}

	public void schedulerInStandbyMode() {
		// TODO Auto-generated method stub
		
	}

	public void schedulerShuttingdown() {
		// TODO Auto-generated method stub
		
	}

	public void schedulerStarted() {
		// TODO Auto-generated method stub
		
	}
}