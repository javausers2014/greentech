package com.gtech.iarc.ichartable.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gtech.iarc.ichartable.service.DownloadService;

import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;

/**
 * Handles and retrieves download request
 */
@Controller
@RequestMapping("/main")
public class MainController {

	protected static Logger logger = Logger.getLogger("controller");
	
	@Resource(name="downloadService")
	private DownloadService downloadService;
	
	/**
	 * Handles and retrieves the download page
	 * 
	 * @return the name of the JSP page
	 */
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public String getDownloadPage() {
    	logger.debug("Received request to show download page");
    
    	// Do your work here. Whatever you like
    	// i.e call a custom service to do your business
    	// Prepare a model to be used by the JSP page
    	
    	// This will resolve to /WEB-INF/jsp/downloadpage.jsp
    	return "downloadpage";
	}
 
    /**
     * Retrieves the download file
     * 
     * @return
     */
    @RequestMapping(value = "/download/xls", method = RequestMethod.GET)
    public void doSalesReportXLS(HttpServletResponse response
    	) throws ServletException, IOException,
		ClassNotFoundException, SQLException, ColumnBuilderException, JRException {
    	
		logger.debug("Received request to download Excel report");
		

		// Call DownloadService to do the actual report processing
		downloadService.downloadXLS(response);
	}
}
