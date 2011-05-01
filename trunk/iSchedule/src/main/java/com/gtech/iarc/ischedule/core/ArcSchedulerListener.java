package com.gtech.iarc.ischedule.core;

import java.sql.Timestamp;
import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.SchedulerListener;
import org.quartz.Trigger;

import com.gtech.iarc.ischedule.core.model.ArcTaskAudit;
import com.gtech.iarc.ischedule.core.model.IArcTaskAudit;
import com.gtech.iarc.ischedule.core.model.IArcTaskConfig;
import com.gtech.iarc.ischedule.persistence.TaskConfigDAO;



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
public class ArcSchedulerListener implements SchedulerListener {
    private static Log log = LogFactory.getLog(ArcSchedulerListener.class);

    private TaskConfigDAO taskConfigDAO;

    /**
     * 
     * @param jobDAO
     */
    public void setTaskConfigDAO(TaskConfigDAO jobDAO) {
        this.taskConfigDAO = jobDAO;
    }

    /**
     * 
     * @see org.quartz.SchedulerListener#jobScheduled(org.quartz.Trigger)
     * @param trigger
     */
    public void jobScheduled(Trigger trigger) {
        try {
        	IArcTaskConfig job = taskConfigDAO.getArcTaskConfig(trigger.getJobName());

            if (job != null) {
                IArcTaskAudit jobAudit = new ArcTaskAudit();
//                jobAudit.setActiveInd(ArcSchedulerConstants.JOB_ACTIVE_IND);
//                jobAudit.setCreatedBy(ArcSchedulerConstants.SCHEDULER_USERNAME);
//                jobAudit.setCreatedDate( new Timestamp((Calendar.getInstance()).getTimeInMillis()));

                jobAudit.setTaskId(job.getTaskConfigId());
                jobAudit.setRequestedBy(job.getCreatedBy());
                jobAudit.setStatus(ArcSchedulerConstants.ACTIVE_JOB_STATUS);

               // jobAuditDAO.insertJobAudit(jobAudit);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ArcJobStatusManager.updateErrorStatus(Long.valueOf(trigger
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
        	IArcTaskConfig job = taskConfigDAO.getArcTaskConfig(jobName);

            IArcTaskAudit jobAudit = new ArcTaskAudit();
//            jobAudit.setActiveInd(ArcSchedulerConstants.JOB_ACTIVE_IND);
//            jobAudit.setCreatedBy(ArcSchedulerConstants.SCHEDULER_USERNAME);
//            jobAudit.setCreatedDate( new Timestamp((Calendar.getInstance()).getTimeInMillis()));
            jobAudit.setTaskId(job.getTaskConfigId());
            jobAudit.setRequestedBy(job.getModifiedBy());
            jobAudit.setStatus(ArcSchedulerConstants.PAUSED_JOB_STATUS);

           // jobAuditDAO.insertJobAudit(jobAudit);
        } catch (Exception e) {
            ArcJobStatusManager.updateErrorStatus(Long.valueOf(jobGroup));
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
        	IArcTaskConfig job = taskConfigDAO.getArcTaskConfig(jobName);

            IArcTaskAudit jobAudit = new ArcTaskAudit();
//            jobAudit.setActiveInd(ArcSchedulerConstants.JOB_ACTIVE_IND);
//            jobAudit.setCreatedBy(ArcSchedulerConstants.SCHEDULER_USERNAME);
//            jobAudit.setCreatedDate( new Timestamp((Calendar.getInstance()).getTimeInMillis()));
            jobAudit.setTaskId(job.getTaskConfigId());
            jobAudit.setRequestedBy(job.getModifiedBy());
            jobAudit.setStatus(ArcSchedulerConstants.ACTIVE_JOB_STATUS);

           // jobAuditDAO.insertJobAudit(jobAudit);
        } catch (Exception e) {
            ArcJobStatusManager.updateErrorStatus(Long.valueOf(jobGroup));
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
        	IArcTaskConfig job = taskConfigDAO.getArcTaskConfig(triggerName);

            IArcTaskAudit jobAudit = new ArcTaskAudit();
//            jobAudit.setActiveInd(ArcSchedulerConstants.JOB_ACTIVE_IND);
//            jobAudit.setCreatedBy(ArcSchedulerConstants.SCHEDULER_USERNAME);
//            jobAudit.setCreatedDate( new Timestamp((Calendar.getInstance()).getTimeInMillis()));
//            jobAudit.setInactBy(ArcSchedulerConstants.SCHEDULER_USERNAME);
//            jobAudit.setInactDate( new Timestamp((Calendar.getInstance()).getTimeInMillis()));
            jobAudit.setTaskId(job.getTaskConfigId());
            jobAudit.setRequestedBy(job.getModifiedBy());
            //jobAudit.setStatus(ArcSchedulerConstants.DELETED_JOB_STATUS);

           // jobAuditDAO.insertJobAudit(jobAudit);
        } catch (Exception e) {
            ArcJobStatusManager.updateErrorStatus(Long.valueOf(triggerGroup));
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
        	IArcTaskConfig job = taskConfigDAO.getArcTaskConfig(trigger.getJobName());
            job.setModifiedBy(ArcSchedulerConstants.SCHEDULER_USERNAME);
            job.setModifiedDate( new Timestamp((Calendar.getInstance()).getTimeInMillis()));
            job.setStatus(ArcSchedulerConstants.COMPLETE_JOB_STATUS);
            taskConfigDAO.updateArcTaskConfig(job);

            IArcTaskAudit jobAudit = new ArcTaskAudit();
//            jobAudit.setActiveInd(ArcSchedulerConstants.JOB_ACTIVE_IND);
            jobAudit.setCompleteDate(new Timestamp((Calendar.getInstance()).getTimeInMillis()));
//            jobAudit.setCreatedBy(ArcSchedulerConstants.SCHEDULER_USERNAME);
//            jobAudit.setCreatedDate( new Timestamp((Calendar.getInstance()).getTimeInMillis()));
            jobAudit.setTaskId(job.getTaskConfigId());
            jobAudit.setRequestedBy(job.getModifiedBy());

            if (ArcJobStatusManager.isJobCompletedWithError(Long.valueOf(trigger
                    .getJobGroup()))) {
                jobAudit
                        .setStatus(ArcSchedulerConstants.COMPLETE_ERROR_JOB_STATUS);
            } else {
                jobAudit.setStatus(ArcSchedulerConstants.COMPLETE_JOB_STATUS);
            }

           // jobAuditDAO.insertJobAudit(jobAudit);
        } catch (Exception e) {
            ArcJobStatusManager.updateErrorStatus(Long.valueOf(trigger
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

	@Override
	public void jobAdded(JobDetail jobDetail) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void jobDeleted(String jobName, String groupName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void schedulerInStandbyMode() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void schedulerShuttingdown() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void schedulerStarted() {
		// TODO Auto-generated method stub
		
	}
}
