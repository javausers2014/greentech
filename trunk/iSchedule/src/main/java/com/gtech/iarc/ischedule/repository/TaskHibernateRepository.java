package com.gtech.iarc.ischedule.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.gtech.iarc.base.persistence.BaseRepository;
import com.gtech.iarc.ischedule.core.model.TaskExecutionDetail;
import com.gtech.iarc.ischedule.core.model.TaskSchedule;

@SuppressWarnings("unchecked")
public class TaskHibernateRepository extends HibernateDaoSupport implements
		TaskRepository {
	
	private BaseRepository baseRepository;
	
	
	public void setBaseRepository(BaseRepository baseRepository) {
		this.baseRepository = baseRepository;
	}

	public TaskSchedule getTaskSchedule(Long taskScheduleId) {

		return (TaskSchedule) baseRepository.read(
				TaskSchedule.class, taskScheduleId);
	}

	public List<TaskSchedule> getAllSchedulesForCertainTask(
			Long taskExeDetailId) {
		
		List<TaskSchedule> rs = baseRepository
				.find(
						"from TaskScheduleRequirementContext TSRC where TSRC.taskExecutionDetail.id=?",
						taskExeDetailId);
		return rs;
	}

	public List<TaskSchedule> findSimilarTaskSchedule(
			TaskSchedule taskConfig) {
		return baseRepository.findByExample(taskConfig.getClass(), taskConfig);
		
//		List jobs = (List) getHibernateTemplate().execute(
//				new HibernateCallback() {
//					public Object doInHibernate(Session ses)
//							throws HibernateException {
//						Example example = Example.create(taskConfig)
//								.excludeZeroes() // exclude zero valued
//													// properties
//								.ignoreCase() // perform case insensitive string
//												// comparisons
//								.enableLike();
//						// .excludeProperty("parameterObjectInBlob");
//
//						List results = ses.createCriteria(
//								TaskScheduleRequirementContext.class).add(
//								example).list();
//
//						return results;
//					}
//				});
//		return jobs;
	}

	public void addNewTaskSchedule(TaskSchedule taskSchedule) {
		baseRepository.save(taskSchedule);
	}

	public void deleteTaskSchedule(Long id){//TaskScheduleRequirementContext taskConfig) {
		baseRepository.delete(TaskSchedule.class, id);
	}

	public void updateTaskSchedule(TaskSchedule taskSchedule) {
		baseRepository.save(taskSchedule);
	}

	public TaskSchedule getTaskScheduleByJobScheduledCode(
			String jobSheduledCode) {
		
		List rs = baseRepository.find("from TaskScheduleRequirementContext tsrc where jobScheduledCode=?", jobSheduledCode);
		
		if (rs.isEmpty() || rs.size() != 1) {
			return null;
		}

		return (TaskSchedule) rs.iterator().next();
	}

	public TaskExecutionDetail getTaskDetail(String taskDetailCode) {
		List rs = baseRepository.find(
				"FROM TaskExecutionDetail TED WHERE TED.taskDetailCode = ?",
				taskDetailCode);
		if(rs.isEmpty()){
			return null;
		}else if(rs.size()>1){
			throw new RuntimeException("Data for TaskExecutionDetail is dirty, duplicated taskDetailCode");
		}
		return (TaskExecutionDetail) rs.iterator().next();
	}

	public List findTaskDetail(String taskGroupId) {
		List jtypes = new ArrayList();

		if ((!taskGroupId.equals(null)) && (!taskGroupId.equals(" "))) {
			jtypes = getHibernateTemplate().find(
					"FROM Y3TaskDetail JT WHERE JT.taskGroupId=?",
					Long.valueOf(taskGroupId));

			return jtypes;
		}

		return jtypes;
	}

	public List findSimilarTaskExecutionDetail(final TaskExecutionDetail taskExecutionDetail) {
		List taskDetails = this.baseRepository.findByExample(TaskExecutionDetail.class, taskExecutionDetail);
			
//			(List) getHibernateTemplate().execute(
//				new HibernateCallback() {
//					public Object doInHibernate(Session ses)
//							throws HibernateException {
//						Example example = Example.create(taskDetail)
//								.excludeZeroes() // exclude zero valued
//													// properties
//								.ignoreCase(); // perform case insensitive
//												// string comparisons
//
//						List results = ses.createCriteria(
//								TaskExecutionDetail.class).add(example).list();
//
//						return results;
//					}
//				});

		return taskDetails;
	}

	public void addNewTaskExecutionDetail(TaskExecutionDetail taskExecutionDetail) {
		this.baseRepository.save(taskExecutionDetail);
	}

	public void deleteTaskExecutionDetail(Long taskExecutionDetailId) {
		this.baseRepository.delete(TaskExecutionDetail.class, taskExecutionDetailId);
	}

	public void updateTaskExecutionDetail(TaskExecutionDetail taskExecutionDetail) {
		this.baseRepository.save(taskExecutionDetail);
	}
}
