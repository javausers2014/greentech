package com.gtech.iarc.ischedule.persistence;

import java.util.List;

import com.gtech.iarc.ischedule.core.TaskScheduleRequirementContext;


/**
 * @author ZHIDAO
 * @revision $Id$
 * @deprecated
 */
public interface TaskConfigDAO {
    
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
    public List findArcTaskConfigListByTaskDetail(Long taskDetailId);

    /**
     * 
     * @param taskConfig
     * @return
     */
    public List findArcTaskConfig(TaskScheduleRequirementContext taskConfig);

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

	public List findArcTaskConfig(String taskDetailSName, String taskScheduleName);

}
