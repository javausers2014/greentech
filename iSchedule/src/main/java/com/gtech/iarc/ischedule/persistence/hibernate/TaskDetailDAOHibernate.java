package com.gtech.iarc.ischedule.persistence.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.gtech.iarc.ischedule.core.model.ArcTaskDetail;
import com.gtech.iarc.ischedule.core.model.IArcTaskDetail;
import com.gtech.iarc.ischedule.persistence.TaskDetailDAO;

/**
 * @dao.hibernate beanId="jobTypeDAO"
 */
public class TaskDetailDAOHibernate extends HibernateDaoSupport implements TaskDetailDAO {
    
    /**
     * 
     * @see com.y3technologies.scheduler.persistence.TaskDetailDAO#getTaskDetail(java.lang.String)
     * @param taskDetailShortName
     * @return
     */
    public IArcTaskDetail getTaskDetail(String taskDetailShortName){
        if (taskDetailShortName.equals("")) {
            throw new IllegalArgumentException(
                "taskDetailShortName must not be null or 0");
        }

        List lj = new ArrayList();
        lj = getHibernateTemplate().find("FROM Y3TaskDetail JT WHERE JT.taskDetailShortName like ?",
                taskDetailShortName.toUpperCase());

        return (IArcTaskDetail) lj.get(0);
    }

    /**
     * 
     * @see com.y3technologies.scheduler.persistence.TaskDetailDAO#findTaskDetail(java.lang.String)
     * @param taskGroupId
     * @return
     */
    public List findTaskDetail(String taskGroupId){
        List jtypes = new ArrayList();

        if ((!taskGroupId.equals(null)) && (!taskGroupId.equals(" "))) {
            jtypes = getHibernateTemplate().find("FROM Y3TaskDetail JT WHERE JT.taskGroupId=?",
                    Long.valueOf(taskGroupId));

            return jtypes;
        }

        return jtypes;
    }

    /**
     * 
     * @see com.y3technologies.scheduler.persistence.TaskDetailDAO#findTaskDetail(com.ArcTaskDetail.scheduler.model.Y3TaskDetail)
     * @param taskDetail
     * @return
     */
    public List findTaskDetail(final IArcTaskDetail taskDetail){
        List taskDetails;
        taskDetails = (List) getHibernateTemplate().execute(new HibernateCallback() {
                    public Object doInHibernate(Session ses)
                        throws HibernateException {
                        Example example = Example.create(taskDetail).excludeZeroes() // exclude zero valued properties
                            .ignoreCase(); // perform case insensitive string comparisons

                        List results = ses.createCriteria(ArcTaskDetail.class)
                                          .add(example).list();

                        return results;
                    }
                });

        return taskDetails;
    }

    /**
     * 
     * @see com.y3technologies.scheduler.persistence.TaskDetailDAO#insertTaskDetail(com.ArcTaskDetail.scheduler.model.Y3TaskDetail)
     * @param taskDetail
     */
    public void insertTaskDetail(IArcTaskDetail taskDetail){
        if (null != taskDetail) {
            getHibernateTemplate().save(taskDetail);
        }
    }

    /**
     * 
     * @see com.y3technologies.scheduler.persistence.TaskDetailDAO#removeTaskDetail(com.ArcTaskDetail.scheduler.model.Y3TaskDetail)
     * @param taskDetail
     */
    public void removeTaskDetail(IArcTaskDetail taskDetail){
        if (null != taskDetail) {
            getHibernateTemplate().delete(taskDetail);
        }
    }

    /**
     * 
     * @see com.y3technologies.scheduler.persistence.TaskDetailDAO#updateTaskDetail(com.ArcTaskDetail.scheduler.model.Y3TaskDetail)
     * @param taskDetail
     */
    public void updateTaskDetail(IArcTaskDetail taskDetail){
        if (null != taskDetail) {
            getHibernateTemplate().update(taskDetail);
        }
    }
}
