package com.gtech.iarc.ischedule.persistence;

import java.util.List;

import com.gtech.iarc.ischedule.core.TaskExecutionDetail;


/**
 * @author ZHIDAO
 * @revision $Id$
 * @deprecated
 */
public interface TaskDetailDAO {

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
    public List findTaskDetail(String taskDetailId);

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
    public List findTaskDetail(TaskExecutionDetail taskDetail);

}
