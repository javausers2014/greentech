package com.gtech.iarc.base.model.resource;

import java.io.File;
import java.util.Date;


public class FileExplorerItem {
	private boolean isDirectory;
	private String name;
	private long length;
	private long lastModified;
	
	public FileExplorerItem(File f) {
		this.isDirectory = f.isDirectory();
		this.name = f.getName();
		this.length = f.length();
		this.lastModified = f.lastModified();		
	}

	public boolean isDirectory() {
		return isDirectory;
	}

	public String getName() {
		return name;
	}

	public long getLength() {
		return length;
	}

	public Date getLastModified() {
		return new Date(lastModified);
	}
	
	public String getExtension() {
	    String ext = this.name.substring(this.name.lastIndexOf(".") + 1, this.name.length());
	    
	    return ext;
	}
}
