package com.gtech.iarc.ischedule.persistence.hibernate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.gtech.iarc.ischedule.core.TaskScheduleRequirementContext;
import com.gtech.iarc.ischedule.core.model.DefaultTaskScheduleRequirementContext;
import com.gtech.iarc.ischedule.persistence.TaskConfigDAO;


/**
 *dao.hibernate beanId="jobDAO"
 */
public class TaskConfigDAOHibernate extends HibernateDaoSupport implements TaskConfigDAO {
    
    /**
     * 
     * @see com.Arctechnologies.scheduler.persistence.TaskConfigDAO#getArcTaskConfig(java.lang.Long)
     * @param taskConfigId
     * @return
     */
    public TaskScheduleRequirementContext getArcTaskConfig(Long taskConfigId) {
       // Long jobIdlong = Long.valueOf(jobId);

        if (taskConfigId==null || taskConfigId.longValue()==0) {
            throw new IllegalArgumentException("jobId must not be null or 0");
        }

        return (TaskScheduleRequirementContext) getHibernateTemplate().get(DefaultTaskScheduleRequirementContext.class, taskConfigId);
    }

    /**
     * DOCUMENT ME!
     *
     * @param jobTypeId DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public List findArcTaskConfigListByTaskDetail(Long jobTypeId) {
        if (!jobTypeId.equals("")) {
            return getHibernateTemplate().find("from ArcTaskConfig j where j.taskConfigId=?",
                jobTypeId);
        }

        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param job DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public List findArcTaskConfig(final TaskScheduleRequirementContext taskConfig) {
        List jobs=(List) getHibernateTemplate().execute(new HibernateCallback() {
                    public Object doInHibernate(Session ses)
                        throws HibernateException {
                        Example example = Example.create(taskConfig).excludeZeroes() // exclude zero valued properties
                            .ignoreCase() // perform case insensitive string comparisons
                            .enableLike();
                            //.excludeProperty("parameterObjectInBlob");

                        List results = ses.createCriteria(DefaultTaskScheduleRequirementContext.class).add(example)
                                          .list();

                        return results;
                    }
                });        
        return jobs;
    }

    /**
     * 
     * @see com.Arctechnologies.scheduler.persistence.TaskConfigDAO#insertArcTaskConfig(com.DefaultTaskScheduleRequirementContext.scheduler.model.ArcTaskConfig)
     * @param taskConfig
     */
    public void insertArcTaskConfig(TaskScheduleRequirementContext taskConfig) {
        
        if (null != taskConfig) {
            getHibernateTemplate().save(taskConfig);
        }
    }

    /**
     * 
     * @see com.Arctechnologies.scheduler.persistence.TaskConfigDAO#removeArcTaskConfig(com.DefaultTaskScheduleRequirementContext.scheduler.model.ArcTaskConfig)
     * @param taskConfig
     */
    public void removeArcTaskConfig(TaskScheduleRequirementContext taskConfig) {
        if (null != taskConfig) {
            getHibernateTemplate().delete(taskConfig);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param job DOCUMENT ME!
     */
    public void updateArcTaskConfig(TaskScheduleRequirementContext job) {
        if (null != job) {
            getHibernateTemplate().saveOrUpdate(job);
        }
    }
    
    /**
     * Accessing table: COMM_JOB to retrieve ArcJob details.
     * @see com.Arctechnologies.scheduler.persistence.TaskConfigDAO#getArcTaskConfig(java.lang.String)
     * @param jobSheduledName  
     * @return ArcJob
     */
    public DefaultTaskScheduleRequirementContext getArcTaskConfig(String jobSheduledName) {
        
        if (jobSheduledName!=null && jobSheduledName.trim().length()>0) {
            
            Collection rs = getHibernateTemplate().find("from ArcTaskConfig j where j.taskScheduleName=?",
                    jobSheduledName);
            
            if(rs.isEmpty()||rs.size()!=1){
                return null;
            }
            
            return (DefaultTaskScheduleRequirementContext)rs.iterator().next();
        }else{
            return null;
        }        
    }

	public List findArcTaskConfig(String taskDetailSName, String taskScheduleName) {
		if((taskDetailSName==null || taskDetailSName.trim().length()==0)
				&&(taskScheduleName==null || taskScheduleName.trim().length()==0)){
			return Collections.EMPTY_LIST;
		}
		StringBuffer hql = new StringBuffer();
		ArrayList params = new ArrayList();
		hql.append("from ArcTaskConfig tc where ");
		boolean needAnd = false;
		
//		if((taskDetailSName!=null && taskDetailSName.trim().length()>0)
//				||(taskScheduleName!=null && taskScheduleName.trim().length()>0)){
//			hql.append(" where ");
//		}
		
		if(taskDetailSName!=null && taskDetailSName.trim().length()>0){
//			if(needAnd){
//				hql.append(" and ");
//			}
			hql.append(" tc.ArcTaskDetail.taskDetailShortName like ?");
			params.add(taskDetailSName.toUpperCase());
			needAnd = true;
		}
		
		if(taskScheduleName!=null && taskScheduleName.trim().length()>0){
			if(needAnd){
				hql.append(" and ");
			}
			hql.append(" tc.taskScheduleName like ?");
			params.add(taskScheduleName.toUpperCase());
//			needAnd = true;
		}
		logger.debug("\nHQL searching task config :"+hql.toString());
		return this.getHibernateTemplate().find(hql.toString(), params.toArray());
		
	}

}