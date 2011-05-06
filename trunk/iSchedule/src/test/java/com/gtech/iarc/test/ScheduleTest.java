package com.gtech.iarc.test;

import org.junit.Before;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public abstract class ScheduleTest {
	
	@Before
	public void setUp() {	
		testEnvSetup();
	}
	
    protected static ClassPathXmlApplicationContext ctx = null;
	
	protected void testEnvSetup(){
        ctx = (ctx == null) ? new ClassPathXmlApplicationContext(
		"/testApplicationContext.xml") : ctx;
	}
	
	
}
