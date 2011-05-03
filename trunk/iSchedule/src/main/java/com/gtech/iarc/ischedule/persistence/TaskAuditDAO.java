package com.gtech.iarc.ischedule.persistence;

import java.util.List;

import com.gtech.iarc.ischedule.core.model.TaskExecutionAudit;


/**
 * @author ZHIDAO
 * @revision $Id: TaskAuditDAO.java 6647 2007-08-21 15:11:13Z zhidao $
 * @deprecated
 */
public interface TaskAuditDAO {
    
    public TaskExecutionAudit getTaskAudit(String taskAuditId);
    
    public List findTaskAudit(String TaskId);

    public void insertTaskAudit(TaskExecutionAudit taskAudit);

    public void removeTaskAudit(TaskExecutionAudit taskAudit);

    public void updateTaskAudit(TaskExecutionAudit taskAudit);
}
