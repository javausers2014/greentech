package com.gtech.iarc.test.ischedule.support.executor;

import com.gtech.iarc.ischedule.AbstractScheduledWork;

public class SimpleTestScheduledWork extends AbstractScheduledWork{

	@Override
	public void doItNow(String scheduledName) {
		System.out.println("Spring Bean Job has been activated!");		
	}

}
