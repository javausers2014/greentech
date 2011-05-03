package com.gtech.iarc.ischedule.persistence;

import java.util.List;

import com.gtech.iarc.ischedule.core.TaskExecutionAudit;
import com.gtech.iarc.ischedule.core.TaskExecutionDetail;
import com.gtech.iarc.ischedule.core.TaskScheduleRequirementContext;

public interface TaskRepository {
	
    public TaskExecutionAudit getTaskAudit(String taskAuditId);
    
    public List<TaskExecutionAudit> findTaskAudit(String taskId);

    public void insertTaskAudit(TaskExecutionAudit taskAudit);

    public void removeTaskAudit(TaskExecutionAudit taskAudit);

    public void updateTaskAudit(TaskExecutionAudit taskAudit);
    
    
    /**
     * 
     * @param taskConfigId
     * @return
     */
    public TaskScheduleRequirementContext getArcTaskConfig(Long taskConfigId);

    /**
     * The ArcTaskConfig details could be retrieved out base on taskSheduledName, which 
     * is unique. 
     * @param taskSheduledName The sheduled name is configed into Quartz Trigger.     * 
     * @return
     */
    public TaskScheduleRequirementContext getArcTaskConfig(String taskSheduledName);
    
    /**
     * 
     * @param taskDetailId
     * @return
     */
    public List<TaskScheduleRequirementContext> findArcTaskConfigListByTaskDetail(Long taskDetailId);

    /**
     * 
     * @param taskConfig
     * @return
     */
    public List<TaskScheduleRequirementContext> findArcTaskConfig(TaskScheduleRequirementContext taskConfig);

    /**
     * 
     * @param taskConfig
     */
    public void insertArcTaskConfig(TaskScheduleRequirementContext taskConfig);

    /**
     * 
     * @param taskConfig
     */
    public void updateArcTaskConfig(TaskScheduleRequirementContext taskConfig);

    /**
     * 
     * @param taskConfig
     */
    public void removeArcTaskConfig(TaskScheduleRequirementContext taskConfig);

	public List<TaskScheduleRequirementContext> findArcTaskConfig(String taskDetailSName, String taskScheduleName);
    
	   /**
     * 
     * @param taskDetailShortName
     * @return
     * @throws DAOException
     */
    public TaskExecutionDetail getTaskDetail(String taskDetailShortName);

    /**
     * 
     * @param taskDetailId
     * @return
     * @throws DAOException
     */
    public List<TaskExecutionDetail> findTaskDetail(String taskDetailId);

    /**
     * 
     * @param taskDetail
     * @throws DAOException
     */
    public void insertTaskDetail(TaskExecutionDetail taskDetail);

    /**
     * 
     * @param taskDetail
     * @throws DAOException
     */
    public void removeTaskDetail(TaskExecutionDetail taskDetail);

    /**
     * 
     * @param taskDetail
     * @throws DAOException
     */
    public void updateTaskDetail(TaskExecutionDetail taskDetail);

    /**
     * 
     * @param taskDetail
     * @return
     */
    public List<TaskExecutionDetail> findTaskDetail(TaskExecutionDetail taskDetail);

}
