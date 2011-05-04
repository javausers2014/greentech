// Copyright 2005-2007 Y3 Technologies, All Rights Reserved

package com.gtech.iarc.ischedule.service.context;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.gtech.iarc.ischedule.core.AbstractSpringBeanTask;
import com.gtech.iarc.ischedule.core.ScheduledWork;

/**
 * The root delegator <li>Load any biz service class managed by Spring context.<br>
 */
public class SpringBeanServiceDelegator implements ApplicationContextAware {
    // Logger
    protected static Log log = LogFactory.getLog(SpringBeanServiceDelegator.class);

    protected static ApplicationContext context;

    /**
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
    public void setApplicationContext(ApplicationContext context) {
        SpringBeanServiceDelegator.context = context;
    }

    /**
     * Loading service bean from Spring context.
     */
    public static Object getServiceManager(String beanName) {
        log.info("Loading spring bean of ["+beanName+"]");
        return context.getBean(beanName);
    }   
    
    /**
     * 
     * @param jobName
     * @param jobId
     * @param className
     * @return
     */
    @SuppressWarnings("unchecked")
	public static AbstractSpringBeanTask getTaskInstance(String jobName, String jobId,
            String className) {

        try {
            Class jobClass = Class.forName(className);
            Class[] classArray = { String.class, String.class };
            Constructor jobConstructor = jobClass.getConstructor(classArray);
            Object[] objArray = { jobName, jobId };
            return (AbstractSpringBeanTask) jobConstructor.newInstance(objArray);
        } catch (ClassNotFoundException e) {
            log.error("ClassNotFoundException in Validating Job Class :"
                    + className, e);
            e.printStackTrace();
            throw new RuntimeException(
                    "ClassNotFoundException in Validating Job Class :"
                            + className, e);
        } catch (SecurityException e) {
            log.error(
                    "SecurityException in Validating Job Class :" + className,
                    e);
            e.printStackTrace();
            throw new RuntimeException(
                    "SecurityException in Validating Job Class :" + className,
                    e);
        } catch (NoSuchMethodException e) {
            log.error("NoSuchMethodException in Validating Job Class :"
                    + className, e);
            e.printStackTrace();
            throw new RuntimeException(
                    "NoSuchMethodException in Validating Job Class :"
                            + className, e);
        } catch (IllegalArgumentException e) {
            log.error("IllegalArgumentException in Validating Job Class :"
                    + className, e);
            e.printStackTrace();
            throw new RuntimeException(
                    "IllegalArgumentException in Validating Job Class :"
                            + className, e);
        } catch (InstantiationException e) {
            log.error("InstantiationException in Validating Job Class :"
                    + className, e);
            e.printStackTrace();
            throw new RuntimeException(
                    "InstantiationException in Validating Job Class :"
                            + className, e);
        } catch (IllegalAccessException e) {
            log.error("IllegalAccessException in Validating Job Class :"
                    + className, e);
            e.printStackTrace();
            throw new RuntimeException(
                    "IllegalAccessException in Validating Job Class :"
                            + className, e);
        } catch (InvocationTargetException e) {
            log.error("InvocationTargetException in Validating Job Class :"
                    + className, e);
            e.printStackTrace();
            throw new RuntimeException(
                    "InvocationTargetException in Validating Job Class :"
                            + className, e);
        }

    }

    /**
     * Load spring bean with bean id <i>proxyBeanName</i>, which class shoulb be the implementation of <i>com.y3technologies.scheduler.IY3ScheduleServiceManager</i><br>
     * Then invoke processJob(JobExecutionContext).
     * @param context
     * @param proxyBeanName
     */
    public static void processScheduleJob(JobExecutionContext context,
            String proxyBeanName) {

    	if(proxyBeanName==null||proxyBeanName.trim().length()==0){
    		log.warn("The proxy bean for running schedule job is NULL/Empty");
    		return;
    	}
        ScheduledWork sManager = null;
		try {
			Object serviceBean = SpringBeanServiceDelegator
					.getServiceManager(proxyBeanName);
			sManager = (ScheduledWork) serviceBean;
		} catch (Exception ex) {
			log.error("Loading spring bean for schedule failed.", ex);
			return;
		}

        log.debug("Class : " + sManager.getClass().getName()
                + " has been loaded to process the schduled job.");

        sManager.doIt(context);
    }
}