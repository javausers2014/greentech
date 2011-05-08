package com.gtech.iarc.test.ischedule.support.task;

import com.gtech.iarc.ischedule.AbstractSpringBeanTask;

public class SimpleTestTask extends AbstractSpringBeanTask{
	public SimpleTestTask(){
		
	}
	
	@Override
	public String getProxyBeanName() {
		return "simpleTestScheduledWork";
	}

}
