package com.gtech.iarc.demo.web;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.util.Date;

import com.innovations.webtop.managers.Publisher;
import com.innovations.webtop.managers.WebtopManager;

public class ServerPushThread extends Thread {
	private boolean state = true;
	private final int INTERVAL = 5000;
	private String ssnId; 
	private MemoryMXBean memoryMx = ManagementFactory.getMemoryMXBean();
	private OperatingSystemMXBean osMx = ManagementFactory.getOperatingSystemMXBean();
	private RuntimeMXBean runtimeMx = ManagementFactory.getRuntimeMXBean();
	
	public ServerPushThread(ThreadGroup group,String name) {
		super(group, name);
		this.ssnId = name;
		
	}
	
	public void halt() {
		state = false;
	}
	
	@Override
	public void run() {
		Thread thisThread = Thread.currentThread();		
		long lastProcessCpuTime = 0;
		Publisher publisher = WebtopManager.get().getPublisher();
		
		while (state) {
			try {
				thisThread.sleep(INTERVAL);
			} catch (InterruptedException e) {
				
			}
			StringBuffer sb = new StringBuffer();
			
			MemoryUsage heapMemoryUsage = memoryMx.getHeapMemoryUsage();
			long usedHeapMemory = heapMemoryUsage.getUsed();
			
			MemoryUsage nonHeapMemory = memoryMx.getNonHeapMemoryUsage();
			long usedNonHeapMemory = nonHeapMemory.getUsed();
			
			long uptime = runtimeMx.getUptime();			
			
			sb.append("**********************************************\n");
			sb.append(new Date().toString());
			sb.append("\n");
			sb.append("HEAPMEM USED:");
			sb.append(usedHeapMemory);
			sb.append("\n");

			sb.append("NON HEAPMEM USED:");
			sb.append(usedNonHeapMemory);
			sb.append("\n");
			
			sb.append("UPTIME:");
			sb.append(uptime);
			sb.append("\n");
			
			publisher.publish("webtopdemo.serverpush", sb.toString(), ssnId);
		}		
	}	
}