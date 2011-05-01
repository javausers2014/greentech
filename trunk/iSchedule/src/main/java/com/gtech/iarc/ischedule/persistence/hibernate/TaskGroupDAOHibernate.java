
package com.gtech.iarc.ischedule.persistence.hibernate;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.gtech.iarc.ischedule.core.model.IArcTaskGroup;
import com.gtech.iarc.ischedule.persistence.TaskGroupDAO;

/**
 * @dao.hibernate beanId="taskGroupDAO"
 */
public class TaskGroupDAOHibernate extends HibernateDaoSupport implements
        TaskGroupDAO {

    /**
     * 
     * @see com.y3technologies.scheduler.persistence.TaskGroupDAO#getTaskGroup(java.lang.String)
     * @param taskGroupShortName
     * @return
     * @throws DAOException
     */
    public IArcTaskGroup getTaskGroup(String taskGroupShortName) {
        if (!taskGroupShortName.equals("")) {
            List taskGroups;
            taskGroups = getHibernateTemplate().find(
                    "FROM Y3TaskGroup tg WHERE tg.taskGroupShortName=?",
                    taskGroupShortName);

            if (taskGroups.size() > 0) {
                return (IArcTaskGroup) taskGroups.get(0);
            }

            return null;
        } else {
            return null;
        }
    }

    /**
     * 
     * @see com.y3technologies.scheduler.persistence.TaskGroupDAO#findTaskGroup()
     * @return
     */
    public List findTaskGroup() {
        List taskGroups = getHibernateTemplate().find(
                "FROM Y3TaskGroup tg ORDER by tg.taskGroupShortName");

        return taskGroups;
    }

    /**
     * 
     * @see com.y3technologies.scheduler.persistence.TaskGroupDAO#insertTaskGroup(com.y3technologies.scheduler.model.Y3TaskGroup)
     * @param taskGroup
     */
    public void insertTaskGroup(IArcTaskGroup taskGroup) {
        String strSql = "SELECT COUNT(*) FROM Y3TaskGroup tg where tg.taskGroupShortName=?";
        int count = ((Integer) getHibernateTemplate().find(strSql,
                taskGroup.getTaskGroupShortName()).iterator().next())
                .intValue();

        if (count == 0) {
            if (!"1".equals(taskGroup
                    .getActiveInd())) {
                taskGroup.setInactDate(new Timestamp((Calendar.getInstance())
                        .getTimeInMillis()));
            }

            getHibernateTemplate().save(taskGroup);
        }
    }

    /**
     * 
     * @see com.y3technologies.scheduler.persistence.TaskGroupDAO#removeTaskGroup(com.y3technologies.scheduler.model.Y3TaskGroup)
     * @param taskGroup
     */
    public void removeTaskGroup(IArcTaskGroup taskGroup) {
        if (null != taskGroup) {
            getHibernateTemplate().delete(taskGroup);
        }
    }

    /**
     * 
     * @see com.y3technologies.scheduler.persistence.TaskGroupDAO#updateTaskGroup(com.y3technologies.scheduler.model.Y3TaskGroup)
     * @param taskGroup
     */
    public void updateTaskGroup(IArcTaskGroup taskGroup) {
        String strSql = "SELECT COUNT(*) FROM Y3TaskGroup tg where tg.taskGroupShortName=?";
        int count = ((Integer) getHibernateTemplate().find(strSql,
                taskGroup.getTaskGroupShortName()).iterator().next())
                .intValue();

        if (count != 0) {
            getHibernateTemplate().saveOrUpdate(taskGroup);
        }
    }

    /**
     * 
     * @see com.y3technologies.scheduler.persistence.TaskGroupDAO#findTaskGroup(java.lang.String)
     * @param taskGroupShortName
     * @return
     */
    public List findTaskGroup(String taskGroupShortName) {
        List taskGroups = getHibernateTemplate().find(
                "FROM Y3TaskGroup tg WHERE tg.taskGroupShortName=?",
                taskGroupShortName);

        return taskGroups;
    }
}
