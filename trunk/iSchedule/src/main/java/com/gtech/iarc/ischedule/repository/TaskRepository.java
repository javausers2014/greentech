package com.gtech.iarc.ischedule.repository;

import java.util.List;

import com.gtech.iarc.ischedule.core.model.TaskExecutionDetail;
import com.gtech.iarc.ischedule.core.model.TaskSchedule;

public interface TaskRepository {
	public abstract TaskSchedule getTaskSchedule(
			Long taskScheduleId);

	public abstract List<TaskSchedule> getAllSchedulesForCertainTask(
			Long taskExeDetailId);

	public abstract List<TaskSchedule> findSimilarTaskSchedule(
			TaskSchedule taskConfig);

	public abstract void addNewTaskSchedule(
			TaskSchedule taskSchedule);

	public abstract void deleteTaskSchedule(Long id);

	public abstract void updateTaskSchedule(
			TaskSchedule taskSchedule);

	public abstract TaskSchedule getTaskScheduleByJobScheduledCode(
			String jobSheduledCode);

	public abstract TaskExecutionDetail getTaskDetail(String taskDetailCode);

	public abstract List<TaskExecutionDetail> findTaskDetail(String taskGroupCode);

	public abstract List<TaskExecutionDetail> findSimilarTaskExecutionDetail(
			final TaskExecutionDetail taskExecutionDetail);

	public abstract void addNewTaskExecutionDetail(
			TaskExecutionDetail taskExecutionDetail);

	public abstract void deleteTaskExecutionDetail(Long taskExecutionDetailId);

	public abstract void updateTaskExecutionDetail(
			TaskExecutionDetail taskExecutionDetail);


}
