package com.gtech.iarc.demo.web;

import org.directwebremoting.ScriptSession;
import org.directwebremoting.WebContextFactory;

import com.innovations.webtop.managers.Publisher;

public class ServerPushDemoProxy {
	private static ThreadGroup THREADGROUP = new ThreadGroup("SERVER_PUSH_DEMO_THREAD_GROUP");
	private Publisher publisher;
	
	public void startPush() {		
		ScriptSession ssn = WebContextFactory.get().getScriptSession();	
		final String ssnId = ssn.getId();

		if (findThread(ssnId) == null) {
			new ServerPushThread(THREADGROUP, ssnId).start();
		}
	}
	
	public void stopPush() {
		ScriptSession ssn = WebContextFactory.get().getScriptSession();
		final String ssnId = ssn.getId();
		
		ServerPushThread t = (ServerPushThread) findThread(ssnId);	
		if (t != null) {
			t.halt();
			publisher.publish("webtopdemo.serverpush", "Stopped Push", ssnId);
		}
	}
	
	private Thread findThread(String ssnId) {
		Thread[] ts = new Thread[THREADGROUP.activeCount()];			
		THREADGROUP.enumerate(ts);
		
	    for (int i = 0; i < ts.length; i++) {	    	
	      Thread t = (Thread)ts[i];
	      if (t.getName().equals(ssnId)) {
	    	  return t;
	      }
	    }
	    return null;
	}
	
	public void setPublisher(Publisher p) {
		this.publisher = p;
	}	
}
