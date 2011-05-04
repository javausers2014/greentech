import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.SchedulerException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.y3technologies.ewms.common.view.ScheduleJobView;
import com.y3technologies.model.IDataObject;
import com.y3technologies.persistence._DAO;
import com.y3technologies.scheduler.core.Y3SchedulerConstants;
import com.y3technologies.scheduler.model.IY3TaskConfig;
import com.y3technologies.scheduler.model.IY3TaskDetail;
import com.y3technologies.scheduler.model.IY3TaskGroup;
import com.y3technologies.scheduler.model.IY3TaskScheduleInfo;
import com.y3technologies.scheduler.model.Y3TaskConfig;
import com.y3technologies.scheduler.model.Y3TaskDetail;
import com.y3technologies.scheduler.model.Y3TaskGroup;
import com.y3technologies.scheduler.model.Y3TaskScheduleInfo;
import com.y3technologies.scheduler.service.SchedulerServiceManager;
import com.y3technologies.testhelper.ViewObjectHelper;

/**
 * 
 * @author ZHIDAO
 * @revision $Id: SchedulerServiceManagerTest.java 6688 2007-08-23 05:57:41Z
 *           zhidao $
 */
public class SchedulerServiceManagerTest extends TestCase {

    protected static Log log = LogFactory
            .getLog(SchedulerServiceManagerTest.class);

    public static final String CREATED_BY = "UNITTEST";

    public static final String MODIFIED_BY = "UNITTEST";

    protected static ClassPathXmlApplicationContext ctx = null;

    private ScheduleJobView scheduleJobView;

    private ViewObjectHelper viewObject;

    SchedulerServiceManager schedulerServiceManager;

//    private DefaultListableBeanFactory beanFactory;
    
    protected String[] APPLICATION_CONTEXTS = { "/applicationContext.xml",
            "/applicationContext-base.xml","/applicationContext-billing.xml",
            "/applicationContext-audit.xml","/applicationContext-scheduler.xml",
            "/y3-scheduler-beans.xml" };

    public SchedulerServiceManagerTest() {
        ctx = (ctx == null) ? new ClassPathXmlApplicationContext(
                APPLICATION_CONTEXTS) : ctx;
    }

    private IY3TaskConfig prepareUnitTestOneTimeTaskConfig(
            String taskScheduleName, IY3TaskDetail unitTestTaskDetail,
            int minutesLate) {

        IY3TaskScheduleInfo mockTaskScheduleInfo = this
                .getNewDailyTaskScheduleInfoForDelay(minutesLate);
        viewObject.bindDataObject("y3TaskScheduleInfo",
                (IDataObject) mockTaskScheduleInfo);

        IY3TaskConfig taskConfig = new Y3TaskConfig();

        taskConfig.setY3TaskDetail(unitTestTaskDetail);
        taskConfig.setActiveInd(Boolean.TRUE);
        taskConfig.setCreatedBy("UNITTEST");
        taskConfig.setStatus(Y3SchedulerConstants.ACTIVE_JOB_STATUS);
        taskConfig.setDescription("One Time task For UNIT Test!");
        taskConfig.setDisplayEndTime("11:59");
        taskConfig.setDisplayPriority("LOW");
        taskConfig.setDisplayStartTime("00:01");
        taskConfig.setEmailId(null);

        Calendar validFrom = Calendar.getInstance();
        // validFrom.add(Calendar.YEAR, -1);

        Calendar validTo = Calendar.getInstance();
        // validTo.add(Calendar.YEAR, 1);

        taskConfig.setEndDate(validFrom.getTime());
        taskConfig.setStartDate(validTo.getTime());

        taskConfig.setJobCronExpression("");
        taskConfig.setTaskScheduleName(taskScheduleName);
        taskConfig.setTaskSchedule("DAILY;10:09");
        taskConfig.setTaskScheduleMode("DAILY");
        taskConfig.setJobCronExpression("0 09 10 ? * *");
        taskConfig.setCreatedBy(SchedulerServiceManagerTest.CREATED_BY);
        viewObject.bindDataObject("y3TaskConfig", (IDataObject) taskConfig);

        taskConfig = scheduleJobView.prepareTaskConfig(viewObject);
        return taskConfig;
    }

    private void removeAllTestData() {
        dao
                .executeUpdate(
                        "delete from Y3TaskDetail taskDetail where taskDetail.taskDetailShortName=?",
                        new Object[] { "UNITTESTONLY" });
        dao.executeUpdate(
                "delete from Y3TaskGroup grp where grp.taskGroupShortName=?",
                new Object[] { "UNITTESTONLY" });
        dao.executeUpdate(
                "delete from Y3TaskConfig conf where conf.createdBy=?",
                new Object[] { SchedulerServiceManagerTest.CREATED_BY });
        dao
                .executeUpdate(
                        "delete from Y3UserProfileBean userProfole where userProfole.createdBy=?",
                        new Object[] { SchedulerServiceManagerTest.CREATED_BY });
        dao
                .executeUpdate(
                        "delete from Y3UserRoleBean userRole where userRole.createdBy=?",
                        new Object[] { "UNITTEST" });
        dao.executeUpdate("delete from Y3UserBean user where user.createdBy=?",
                new Object[] { SchedulerServiceManagerTest.CREATED_BY });
        dao.executeUpdate(
                "delete from Y3ProfileValueBean profV where profV.createdBy=?",
                new Object[] { SchedulerServiceManagerTest.CREATED_BY });

    }

    private IY3TaskScheduleInfo getNewDailyTaskScheduleInfoForDelay(int minutes) {

        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, minutes);

        int hr = now.get(Calendar.HOUR_OF_DAY);
        int mins = now.get(Calendar.MINUTE);
        StringBuffer timeBuf = new StringBuffer();

        if (hr < 10) {
            timeBuf.append("0");
        }
        timeBuf.append(hr + ":");

        if (mins < 10) {
            timeBuf.append("0");
        }
        timeBuf.append(mins);

        IY3TaskScheduleInfo mockTaskScheduleInfo = new Y3TaskScheduleInfo();
        mockTaskScheduleInfo.setJobRadio1("DAILY");
        mockTaskScheduleInfo.setJobDailyTime(timeBuf.toString());
        return mockTaskScheduleInfo;
    }

    private IY3TaskGroup populateTaskGroup() {
        Y3TaskGroup taskGrp = new Y3TaskGroup();
        taskGrp.setActiveInd(Boolean.TRUE);
        taskGrp.setTaskGroupFullName("Unit Test Task Group");
        taskGrp.setTaskGroupShortName("UNITTESTONLY");
        taskGrp.setCreatedBy(SchedulerServiceManagerTest.CREATED_BY);
        dao.save(taskGrp);
        return taskGrp;
    }

    private IY3TaskDetail populate1stUnitTestTaskDetail() {
        Y3TaskDetail viewTaskDetail = new Y3TaskDetail();
        viewTaskDetail.setTaskDetailCode("UNITTESTONLY");
        viewTaskDetail.setTaskDetailDesc("1st Schedule Task for Unit Test");

        viewTaskDetail
                .setTaskDetailJavaClass("com.y3technologies.scheduler.test.FirstScheduleTask");
        IY3TaskGroup taskGrp = populateTaskGroup();

        viewTaskDetail.setTaskGroupId(taskGrp.getTaskGroupId());
        viewTaskDetail.setTaskDetailCategory("UNITTEST");
        viewTaskDetail.setTaskObjectCode("unitTestOnly");
        viewTaskDetail.setTaskObjectClass("you.should.not.find.this.class");

        viewTaskDetail.setActiveInd(Boolean.TRUE);
        viewTaskDetail.setCreatedBy(SchedulerServiceManagerTest.CREATED_BY);
        viewTaskDetail.setCreatedDate(new Timestamp((new Date()).getTime()));

        dao.save(viewTaskDetail);
        return viewTaskDetail;
    }

    private IY3TaskDetail populate2ndUnitTestTaskDetail() {
        Y3TaskDetail viewTaskDetail = new Y3TaskDetail();
        viewTaskDetail.setTaskDetailCode("UNITTESTONLY");
        viewTaskDetail.setTaskDetailDesc("2nd Schedule Task for Unit Test");

        viewTaskDetail
                .setTaskDetailJavaClass("com.y3technologies.scheduler.test.SecondScheduleTask");
        IY3TaskGroup taskGrp = populateTaskGroup();

        viewTaskDetail.setTaskGroupId(taskGrp.getTaskGroupId());
        viewTaskDetail.setTaskDetailCategory("UNITTEST");
        viewTaskDetail.setTaskObjectCode("unitTestOnly");
        viewTaskDetail.setTaskObjectClass("you.should.not.find.this.class");

        viewTaskDetail.setActiveInd(Boolean.TRUE);
        viewTaskDetail.setCreatedBy(SchedulerServiceManagerTest.CREATED_BY);
        viewTaskDetail.setCreatedDate(new Timestamp((new Date()).getTime()));

        dao.save(viewTaskDetail);
        return viewTaskDetail;
    }

    private _DAO dao;

    // private ScheduleReportManager scheduleReportManager;

    /** Setup any resources requires before running the test. */
    protected void setUp() throws Exception {

        scheduleJobView = (ScheduleJobView) ctx.getBean("scheduleJobView");
        dao = (_DAO) ctx.getBean("dao");
        schedulerServiceManager = (SchedulerServiceManager) ctx
                .getBean("schedulerServiceManager");
        viewObject = new ViewObjectHelper();
        removeAllTestData();
    }

    protected void tearDown() throws Exception {
        // this.removeAllTestData();
        scheduleJobView = null;
        dao = null;
        schedulerServiceManager = null;
        viewObject = null;
    }

    public void testActivateScheduleJob() throws InterruptedException {
        IY3TaskDetail unitTestTaskDetail = this.populate1stUnitTestTaskDetail();

        IY3TaskConfig taskConfig = prepareUnitTestOneTimeTaskConfig(
                "UNITTEST_TASK", unitTestTaskDetail, 1);
        schedulerServiceManager.activateTask(taskConfig);
        Thread.sleep(70000);
        Object rs = dao.read(Y3TaskConfig.class, taskConfig.getTaskConfigId());

        assertNull(rs);
    }

    public void testSuspendResumeDeleteScheduleJob()
            throws InterruptedException {

        IY3TaskDetail unitTestTaskDetail = this.populate2ndUnitTestTaskDetail();
        IY3TaskConfig taskConfig = prepareUnitTestOneTimeTaskConfig(
                "UNITTEST_TASK", unitTestTaskDetail, 10);
        // 1. Activate a task;
        schedulerServiceManager.activateTask(taskConfig);
        IY3TaskConfig activeTask = (Y3TaskConfig) dao.read(Y3TaskConfig.class,
                taskConfig.getTaskConfigId());
        // task should be prepared.
        assertNotNull(activeTask);
        assertTrue(activeTask.getStatus().equalsIgnoreCase(
                Y3SchedulerConstants.ACTIVE_JOB_STATUS));

        // 2. Pause the newly created task;
        try {
            schedulerServiceManager.pauseActiveTask(activeTask);
        } catch (SchedulerException e) {
            e.printStackTrace();
            assertTrue(false);
        }

        IY3TaskConfig pausedTask = (Y3TaskConfig) dao.read(Y3TaskConfig.class,
                activeTask.getTaskConfigId());
        assertNotNull(pausedTask);
        assertTrue(pausedTask.getStatus().equalsIgnoreCase(
                Y3SchedulerConstants.PAUSED_JOB_STATUS));

        // 3. Resume the newly paused task;
        try {
            schedulerServiceManager.resumePausedTask(pausedTask);
        } catch (SchedulerException e) {
            e.printStackTrace();
            assertTrue(false);
        }
        IY3TaskConfig resumedTask = (Y3TaskConfig) dao.read(Y3TaskConfig.class,
                pausedTask.getTaskConfigId());
        assertNotNull(resumedTask);
        assertTrue(resumedTask.getStatus().equalsIgnoreCase(
                Y3SchedulerConstants.ACTIVE_JOB_STATUS));

        // 4. Delete the newly resumed task;
        try {
            schedulerServiceManager.deleteTask(resumedTask);
        } catch (SchedulerException e) {
            e.printStackTrace();
            assertTrue(false);
        }
        Object rs = (Y3TaskConfig) dao.read(Y3TaskConfig.class, resumedTask
                .getTaskConfigId());
        assertNull(rs);
        removeAllTestData();
    }
}
