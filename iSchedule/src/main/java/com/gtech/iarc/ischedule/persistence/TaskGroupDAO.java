
package com.gtech.iarc.ischedule.persistence;

import java.util.List;

import com.gtech.iarc.ischedule.core.model.IArcTaskGroup;


/**
 * 
 * @author ZHIDAO
 * @revision $Id$
 */
public interface TaskGroupDAO{
   
    /**
     * 
     * @param taskGroupShortName
     * @return
     * @throws DAOException
     */
    public IArcTaskGroup getTaskGroup(String taskGroupShortName);

    /**
     * 
     * @return
     * @throws DAOException
     */
    public List findTaskGroup();

    /**
     * 
     * @param jobGroup
     * @throws DAOException
     */
    public void insertTaskGroup(IArcTaskGroup taskGroup);

    /**
     * 
     * @param jobGroup
     * @throws DAOException
     */
    public void removeTaskGroup(IArcTaskGroup taskGroup);

    /**
     * 
     * @param jobGroup
     * @throws DAOException
     */
    public void updateTaskGroup(IArcTaskGroup taskGroup);

    /**
     * 
     * @param taskGroupShortName
     * @return
     * @throws DAOException
     */
    public List findTaskGroup(String taskGroupShortName);
}
