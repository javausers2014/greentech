package com.gtech.iarc.testcase.ischedule;

import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import com.gtech.iarc.ischedule.core.model.TaskExecutionDetail;
import com.gtech.iarc.ischedule.core.model.TaskSchedule;
import com.gtech.iarc.ischedule.repository.TaskRepository;
import com.gtech.iarc.ischedule.service.ScheduleConstants;
import com.gtech.iarc.ischedule.service.ScheduleControlService;
import com.gtech.iarc.ischedule.util.ExpressionUtil;
import com.gtech.iarc.ischedule.util.TaskScheduleDateTimeInfo;
import com.gtech.iarc.testcase.AbstractScheduleTest;

/**
 * 
 * @author ZHIDAO
 */
public class ScheduleControlServiceTest extends AbstractScheduleTest{
	
    protected static Log log = LogFactory
            .getLog(ScheduleControlServiceTest.class);

    public static final String CREATED_BY = "UNITTEST";

    public static final String MODIFIED_BY = "UNITTEST";
    
    @Test
    public void testActivateTask(){
    	ScheduleControlService scs =(ScheduleControlService) ctx.getBean("scheduleControlService");
    	TaskRepository tr =(TaskRepository) ctx.getBean("taskHibernateRepository");
    	TaskExecutionDetail ted = tr.getTaskDetail("TEST_TASK");
    	TaskScheduleDateTimeInfo scheduleObj = new TaskScheduleDateTimeInfo();
    	scheduleObj.setJobPeriodInterval("1");//every 1 minutes
    	
    	TaskSchedule newSchedule = new TaskSchedule();
    	newSchedule.setActiveInd(true);
    	newSchedule.setCreatedBy(CREATED_BY);
    	newSchedule.setCronExpression("");
    	newSchedule.setJobScheduledCode("1ST_TEST_SCHE");
    	Calendar now = Calendar.getInstance();
    	now.add(Calendar.MINUTE, 1);
    	newSchedule.setStartDate(now.getTime());
    	Calendar now2 = Calendar.getInstance();
    	now2.add(Calendar.MINUTE, 10);
    	newSchedule.setEndDate(now2.getTime());
    	newSchedule.setTaskExecutionDetail(ted);
    	newSchedule.setPriority(1);
    	newSchedule.setNotifyEmail("xuzhidao@gamil.com");
    	newSchedule.setStatus(ScheduleConstants.JOB_STATUS_ACTIVE);
    	scs.activateTask(newSchedule, ExpressionUtil.convertToCronExpression(scheduleObj));
    	try {
			Thread.sleep(300000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//Waiting for 5 min
    }
}
