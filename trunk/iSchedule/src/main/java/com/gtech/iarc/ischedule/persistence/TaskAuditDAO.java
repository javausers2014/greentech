package com.gtech.iarc.ischedule.persistence;

import java.util.List;

import com.gtech.iarc.ischedule.core.model.IArcTaskAudit;


/**
 * @author ZHIDAO
 * @revision $Id: TaskAuditDAO.java 6647 2007-08-21 15:11:13Z zhidao $
 */
public interface TaskAuditDAO {
    
    public IArcTaskAudit getTaskAudit(String taskAuditId);
    
    public List findTaskAudit(String TaskId);

    public void insertTaskAudit(IArcTaskAudit taskAudit);

    public void removeTaskAudit(IArcTaskAudit taskAudit);

    public void updateTaskAudit(IArcTaskAudit taskAudit);
}
