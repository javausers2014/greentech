package com.gtech.iarc.ischedule.persistence;

import java.util.List;

import com.gtech.iarc.ischedule.core.model.IArcTaskConfig;


/**
 * @author ZHIDAO
 * @revision $Id$
 */
public interface TaskConfigDAO {
    
    /**
     * 
     * @param taskConfigId
     * @return
     */
    public IArcTaskConfig getArcTaskConfig(Long taskConfigId);

    /**
     * The ArcTaskConfig details could be retrieved out base on taskSheduledName, which 
     * is unique. 
     * @param taskSheduledName The sheduled name is configed into Quartz Trigger.     * 
     * @return
     */
    public IArcTaskConfig getArcTaskConfig(String taskSheduledName);
    
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
    public List findArcTaskConfig(IArcTaskConfig taskConfig);

    /**
     * 
     * @param taskConfig
     */
    public void insertArcTaskConfig(IArcTaskConfig taskConfig);

    /**
     * 
     * @param taskConfig
     */
    public void updateArcTaskConfig(IArcTaskConfig taskConfig);

    /**
     * 
     * @param taskConfig
     */
    public void removeArcTaskConfig(IArcTaskConfig taskConfig);

	public List findArcTaskConfig(String taskDetailSName, String taskScheduleName);

}
