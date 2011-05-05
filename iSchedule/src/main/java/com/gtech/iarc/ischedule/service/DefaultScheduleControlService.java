package com.gtech.iarc.ischedule.service;

import java.text.ParseException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.SchedulerException;

import com.gtech.iarc.ischedule.core.AbstractSpringBeanTask;
import com.gtech.iarc.ischedule.core.SchedulerControl;
import com.gtech.iarc.ischedule.core.model.TaskExecutionAudit;
import com.gtech.iarc.ischedule.core.model.TaskScheduleRequirementContext;
import com.gtech.iarc.ischedule.repository.TaskRepository;
import com.gtech.iarc.ischedule.service.context.SpringBeanServiceDelegator;

/**
 * 
 * @author ZHIDAO
 */
public class DefaultScheduleControlService implements ScheduleControlService {

	private static Log log = LogFactory
			.getLog(DefaultScheduleControlService.class);
	private SchedulerControl schedulerControl;
	private TaskRepository taskRepository;

	public void setSchedulerControl(SchedulerControl schedulerControl) {
		this.schedulerControl = schedulerControl;
	}

	public void setTaskRepository(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}

	/**
	 * Process: <li>Saving Y3TaskConfig info into system.<br> <li>Instantiate
	 * concrete class of Y3AbstractTask defined in Y3TaskDetail <li>Convert
	 * schedule time of Y3TaskConfig to Quartz CRON Expression. <li>Invoke
	 * Scheduler to activate task.
	 * 
	 * @see com.ScheduleControlService.scheduler.service.SchedulerServiceManager#activateTask(com.y3technologies.scheduler.model.Y3TaskConfig)
	 * @param activeJob
	 */
	public void activateTask(TaskScheduleRequirementContext taskToBeScheduled,
			String cronExpression) {
		if (null == taskToBeScheduled) {
			log.warn("Can't Activate a NULL Job!");
			return;
		}

		log.debug("To activate scheduled task ["
				+ taskToBeScheduled.getJobScheduledCode() + "]\n");

		try {
			taskToBeScheduled.setActiveInd(Boolean.TRUE);
			taskToBeScheduled.setStatus(JOB_STATUS_ACTIVE);

			taskRepository.insertArcTaskConfig(taskToBeScheduled);

			String className = taskToBeScheduled.getTaskExecutionDetail()
					.getTaskDetailJavaClass();
			log.debug("To find the instance for schedule job excution :\n["
					+ className + "]");

			AbstractSpringBeanTask scheduleActiveJob = SpringBeanServiceDelegator
					.getTaskInstance(className);

			String taskGroup = taskToBeScheduled.getTaskExecutionDetail()
					.getTaskGroupCode() == null ? DEFAULT_JOB_GROUP
					: taskToBeScheduled.getTaskExecutionDetail()
							.getTaskGroupCode();

			schedulerControl.activateJob(scheduleActiveJob.getClass(),
					taskToBeScheduled.getJobScheduledCode(), taskGroup,
					taskToBeScheduled.getStartDate(), taskToBeScheduled
							.getEndDate(), cronExpression);

			log
					.debug("Added/Activated New job config for new job instance to Quartz...\n");

		} catch (Exception e) {
			log.error("activateJob - " + e.getMessage());
			throw new RuntimeException(e.getMessage());
		}

	}

	/**
	 * Process: <li>Removing ArcTaskConfig info from system.<br> <li>Instantiate
	 * concrete class of ArcAbstractTask defined in ArcTaskDetail <li>Invoke
	 * Scheduler to delete task from schedule module.
	 * 
	 * @see com.ScheduleControlService.scheduler.service.SchedulerServiceManager#deleteTask(com.DefaultTaskScheduleRequirementContext.scheduler.model.ArcTaskConfig)
	 * @param deleteTask
	 * @throws SchedulerException
	 */
	public void deleteTask(TaskScheduleRequirementContext taskToBeDeleted)
			throws SchedulerException {
		if (null == taskToBeDeleted) {
			return;
		}
		taskRepository.removeArcTaskConfig(taskToBeDeleted);
		String taskGroup = taskToBeDeleted.getTaskExecutionDetail()
				.getTaskGroupCode() == null ? DEFAULT_JOB_GROUP
				: taskToBeDeleted.getTaskExecutionDetail().getTaskGroupCode();

		schedulerControl.deleteJob(taskToBeDeleted.getJobScheduledCode(),
				taskGroup);

	}

	/**
	 * Process: <li>Auditing ArcTaskConfig info in system.<br> <li>Updating
	 * ArcTaskConfig status to SchedulerServiceManager.PAUSED_JOB_STATUS <li>
	 * Invoke Scheduler to PAUSE task from schedule module.
	 * 
	 * @see com.ScheduleControlService.scheduler.service.SchedulerServiceManager#pauseActiveTask(com.DefaultTaskScheduleRequirementContext.scheduler.model.ArcTaskConfig)
	 * @param pauseTask
	 * @throws SchedulerException
	 */
	public void pauseActiveTask(TaskScheduleRequirementContext taskToBePaused)
			throws SchedulerException {

		TaskExecutionAudit jobAudit = new TaskExecutionAudit();
		jobAudit.setTaskId(taskToBePaused.getId());

		jobAudit.setStatus(taskToBePaused.getStatus());

		jobAudit.setJobLog("Pause Active Task");

		taskToBePaused.setStatus(JOB_STATUS_PAUSED);

		taskRepository.updateArcTaskConfig(taskToBePaused);
		// dao.save(jobAudit);
		String taskGroup = taskToBePaused.getTaskExecutionDetail()
				.getTaskGroupCode() == null ? DEFAULT_JOB_GROUP
				: taskToBePaused.getTaskExecutionDetail().getTaskGroupCode();
		schedulerControl.pauseJob(taskToBePaused.getJobScheduledCode(),
				taskGroup);
		log.debug("Active Task [" + taskToBePaused.getJobScheduledCode()
				+ "] has been paused.");
	}

	/**
	 * Process: <li>Auditing ArcTaskConfig info in system.<br> <li>Updating
	 * ArcTaskConfig status to SchedulerServiceManager.ACTIVE_JOB_STATUS <li>
	 * Invoke Scheduler to RESUME task from schedule module.
	 * 
	 * @see com.ScheduleControlService.scheduler.service.SchedulerServiceManager#resumePausedTask(com.DefaultTaskScheduleRequirementContext.scheduler.model.ArcTaskConfig)
	 * @param pauseTask
	 * @throws SchedulerException
	 */
	public void resumePausedTask(TaskScheduleRequirementContext pausedTask)
			throws SchedulerException {

		TaskExecutionAudit jobAudit = new TaskExecutionAudit();
		jobAudit.setTaskId(pausedTask.getId());
		jobAudit.setStatus(pausedTask.getStatus());
		jobAudit.setJobLog("Resume Paused Task");

		pausedTask.setStatus(JOB_STATUS_ACTIVE);

		taskRepository.updateArcTaskConfig(pausedTask);
		// dao.save(jobAudit);
		String taskGroup = pausedTask.getTaskExecutionDetail()
				.getTaskGroupCode() == null ? DEFAULT_JOB_GROUP : pausedTask
				.getTaskExecutionDetail().getTaskGroupCode();

		schedulerControl.resumeJob(pausedTask.getJobScheduledCode(), taskGroup);
		log.debug("Paused Task [" + pausedTask.getJobScheduledCode()
				+ "] has been resumed.");
	}

	/**
	 * @throws ParseException
	 * @throws SchedulerException
	 * 
	 */
	public void reScheduleTask(TaskScheduleRequirementContext reTask,
			String cronExpression) {

		// Long taskDetailId = task.getTaskDetailId();
		try {
			reTask.setActiveInd(Boolean.TRUE);
			reTask.setStatus(JOB_STATUS_ACTIVE);

			taskRepository.updateArcTaskConfig(reTask);

			schedulerControl.rescheduleJob(reTask.getJobScheduledCode(), reTask
					.getTaskExecutionDetail().getTaskGroupCode(), reTask
					.getStartDate(), reTask.getEndDate(), cronExpression);
		} catch (Exception e) {
			log.error("rescheduleJob - " + e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		log.debug("Task [" + reTask.getJobScheduledCode()
				+ "] has been triggered.");
	}

}
