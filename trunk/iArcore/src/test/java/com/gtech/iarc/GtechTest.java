package com.gtech.iarc;

import org.junit.Before;

public abstract class GtechTest {
	
	@Before
	public void setUp() {	
		testEnvSetup();
	}
	
	abstract protected void testEnvSetup();
	
}
