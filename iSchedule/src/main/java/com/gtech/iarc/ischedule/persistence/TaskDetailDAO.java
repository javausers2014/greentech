package com.gtech.iarc.ischedule.persistence;

import java.util.List;

import com.gtech.iarc.ischedule.core.model.IArcTaskDetail;


/**
 * @author ZHIDAO
 * @revision $Id$
 */
public interface TaskDetailDAO {

    /**
     * 
     * @param taskDetailShortName
     * @return
     * @throws DAOException
     */
    public IArcTaskDetail getTaskDetail(String taskDetailShortName);

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
    public void insertTaskDetail(IArcTaskDetail taskDetail);

    /**
     * 
     * @param taskDetail
     * @throws DAOException
     */
    public void removeTaskDetail(IArcTaskDetail taskDetail);

    /**
     * 
     * @param taskDetail
     * @throws DAOException
     */
    public void updateTaskDetail(IArcTaskDetail taskDetail);

    /**
     * 
     * @param taskDetail
     * @return
     */
    public List findTaskDetail(IArcTaskDetail taskDetail);

}
