/*
 * Created on Jul 25, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.gtech.iarc.ischedule.persistence.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.gtech.iarc.ischedule.core.model.ArcTaskAudit;
import com.gtech.iarc.ischedule.core.model.IArcTaskAudit;
import com.gtech.iarc.ischedule.persistence.TaskAuditDAO;


/**
 * @dao.hibernate beanId="jobAuditDAO"
 */
public class TaskAuditDAOHibernate extends HibernateDaoSupport
    implements TaskAuditDAO {
    private Log log = LogFactory.getLog(TaskAuditDAOHibernate.class);

    //------------------To get the Single JobObject by using jobid------------------- 
    public IArcTaskAudit getTaskAudit(String taskAuditId) {
        Long taskAuditIdlong = Long.valueOf(taskAuditId);

        if ((taskAuditId.equals(null)) || (taskAuditIdlong.longValue() == 0)) {
            throw new IllegalArgumentException(
                "taskAuditId must not be null or 0");
        }

        return (IArcTaskAudit) getHibernateTemplate().get(ArcTaskAudit.class,
            taskAuditIdlong);
    }

    //  ------------------To get the List of TaskAuditObjects by using jobid-------------------
    public List findTaskAudit(String jobId) {
        String qry = "SELECT FROM Y3TaskAudit JA WHERE JA.jobId =?";

        return (List) getHibernateTemplate().find(qry, Long.valueOf(jobId));
    }

    //  ------------------To save a TaskAuditObject-------------------   
    public void insertTaskAudit(IArcTaskAudit taskAudit) {
        if (!taskAudit.equals(null)) {
            getHibernateTemplate().save(taskAudit);
        }
    }

    //  ------------------To Delete a TaskAuditObject-------------------   
    public void removeTaskAudit(IArcTaskAudit taskAudit) {
        if (!taskAudit.equals(null)) {
            getHibernateTemplate().delete(taskAudit);
        }
    }

    //  ------------------To Update a JobObject-------------------
    public void updateTaskAudit(IArcTaskAudit taskAudit) {
        if (!taskAudit.equals(null)) {
            getHibernateTemplate().saveOrUpdate(taskAudit);
        }
    }
}
