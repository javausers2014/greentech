// Copyright(c) 2011 GTech.
package com.gtech.iarc.ischedule.service;

import org.quartz.SchedulerException;

import com.gtech.iarc.ischedule.ScheduleConstants;
import com.gtech.iarc.ischedule.core.model.TaskScheduleRequirementContext;

/**
 * 
 * @author ZHIDAO
 * @revision $Id$
 */
public interface ScheduleControlService extends ScheduleConstants {

	public static final String JRXML_FILE = "jrxml";

	public static final String MISMATCH = "MISMATCH";

	public static final String LOOKUP_LAYOUT_CLASS = "COMM_JOB_TYPE.OUTPUT_LAYOUT_TYPE";

	public static final String LOOKUP_PRIORITY_CLASS = "COMM_TASK.PRIORITY";

	public static final String LOOKUP_DEVICE_CLASS = "COMM_JOB.OUTPUT_DEVICE";

	public static final String LOOKUP_CLASS_WEEK_DAYS = "COMM_TASK.WEEK_DAYS";

	public static final String LOOKUP_CLASS_MONTHS = "COMM_TASK.MONTHS";

	public static final String LOOKUP_CLASS_MONTH_DAYS = "COMM_TASK.MONTH_DAYS";

	public static final String ERROR_INVALID_SCHEDULE = "scheduler.error.invalid.schhedule";

	/**
	 * The task configed will be stored and then passed to scheduler. After
	 * activate task, the status of Y3TaskConfig will be <br>
	 * com.y3technologies.scheduler.core.Y3SchedulerConstants.ACTIVE_JOB_STATUS
	 * 
	 * @param taskConfig
	 * @see com.SchedulerControl.scheduler.core.ArcScheduler#activateJob(com.y3technologies.scheduler.core.Y3AbstractTask)
	 */
	public void activateTask(TaskScheduleRequirementContext taskConfig,
			String cronExpression);

	/**
	 * The task will be stopped by scheduler first if it is still active, and
	 * then, the task will be removed from system.<br>
	 * Once task is deleted, all information related to this task, such as
	 * parameter objects, running results, will all be removed from system.
	 * 
	 * @param taskConfig
	 * @throws SchedulerException
	 * @see com.SchedulerControl.scheduler.core.ArcScheduler#activateJob(com.Arctechnologies.scheduler.core.ArcAbstractTask)
	 */
	public void deleteTask(TaskScheduleRequirementContext taskConfig)
			throws SchedulerException;

	/**
	 * The activate task will be paused by scheduler if it is still active.<br>
	 * After activate task, the status of ArcTaskConfig will be <br>
	 * com.Arctechnologies.scheduler.core.ArcSchedulerConstants.
	 * PAUSED_JOB_STATUS
	 * 
	 * @param activeTask
	 * @throws SchedulerException
	 */
	public void pauseActiveTask(TaskScheduleRequirementContext activeTask)
			throws SchedulerException;

	/**
	 * The paused task will be resumed by scheduler if it is still valid, i.e,
	 * still in valid period defined in ArcTaskConfig.<br>
	 * After activate task, the status of ArcTaskConfig will be <br>
	 * com.Arctechnologies.scheduler.core.ArcSchedulerConstants.
	 * ACTIVE_JOB_STATUS
	 * 
	 * @param pausedTask
	 * @throws SchedulerException
	 */
	public void resumePausedTask(TaskScheduleRequirementContext pausedTask)
			throws SchedulerException;

	/**
	 * The task will be rescheduled with new settings by scheduler if it is
	 * still valid.<br>
	 * Not be opened as public API, since reschedule may involve
	 * "parameter object" changing. Can ask user to suspend/delete old one and
	 * create a new one.
	 */
	public void reScheduleTask(TaskScheduleRequirementContext reTask,
			String cronExpression);

}
