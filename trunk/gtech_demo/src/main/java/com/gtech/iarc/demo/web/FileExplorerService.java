package com.gtech.iarc.demo.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;

import org.directwebremoting.io.FileTransfer;

import com.gtech.iarc.demo.models.FileExplorerItem;

public class FileExplorerService {
	private String fileExplorerPath;
	
	public List<FileExplorerItem> getEntries() {
		List<FileExplorerItem> explorerItems = new ArrayList<FileExplorerItem>();
		File folder = new File(fileExplorerPath);
		
		File[] listOfFiles = folder.listFiles();
	    for (int i = 0; i < listOfFiles.length; i++) {
	    	explorerItems.add(new FileExplorerItem(listOfFiles[i]));
	    }		
		return explorerItems;
	}
	
	public void uploadFile(FileTransfer uploadedFile) {		
		try {
			File f = new File(fileExplorerPath + "/" + uploadedFile.getFilename());
			
			// Process f as a CSV file
			// ...
			
			OutputStream os = new FileOutputStream (f);
			
			byte buf[]=new byte[1024];
			int len;
			while((len=uploadedFile.getInputStream().read(buf))>0) {
				os.write(buf,0,len);
			}
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public FileTransfer downloadFile(String fileName) {				
		try {
			String fullFilePath = fileExplorerPath + "/" + fileName;
			File file = new File(fullFilePath);
			InputStream is = new FileInputStream(file);
			
			long length = file.length();
			
			byte[] bytes = new byte[(int)length];
	        int offset = 0;
	        int numRead = 0;
	        while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
	            offset += numRead;
	        }
	    
	        // Ensure all the bytes have been read in
	        if (offset < bytes.length) {
	            throw new IOException("Could not completely read file "+file.getName());
	        }
	    
	        // Close the input stream and return bytes
	        is.close();
	        
	        return new FileTransfer(fileName, new MimetypesFileTypeMap().getContentType(file), bytes);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void setFileExplorerPath(String p) {
		this.fileExplorerPath = p;
	}	
}
