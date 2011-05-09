package com.gtech.iarc.ichartable.service;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXlsAbstractExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gtech.iarc.ichartable.dao.SalesDAO;
import com.gtech.iarc.ichartable.dynamicjasper.ReportLayout;
import com.gtech.iarc.ichartable.jasper.Exporter;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;


/**
 * Service for processing DynamicJasper  reports
 * Issues
 * http://jasperforge.org/plugins/espforum/view.php?group_id=102&forumid=103&topicid=80305
 * http://dynamicjasper.com/forum/viewtopic.php?f=8&t=7248
 */
@Service("downloadService")
@Transactional
public class DownloadService {

	protected static Logger logger = Logger.getLogger("controller");
	
	/**
	 * Processes the download for Excel format
	 */
	public void downloadXLS(HttpServletResponse response) throws ColumnBuilderException, ClassNotFoundException, JRException {

		logger.debug("Downloading Excel report");
		
		// Retrieve our data source
		SalesDAO datasource = new SalesDAO();
		JRDataSource ds = datasource.getDataSource();

		// Create our report layout
		// We delegate the reporting layout to a custom ReportLayout instance
		// The ReportLayout is a wrapper class I made. Feel free to remove or modify it
		ReportLayout layout = new ReportLayout();
		DynamicReport dr = layout.buildReportLayout();

		// params is used for passing extra parameters like when passing
		// a custom datasource, such as Hibernate datasource
		// In this application we won't utilize this parameter
		HashMap params = new HashMap(); 
		
		// Compile our report layout
		JasperReport jr = DynamicJasperHelper.generateJasperReport(dr,
				new ClassicLayoutManager(), params);

		// Creates the JasperPrint object
		// It needs a JasperReport layout and a datasource
		JasperPrint jp = JasperFillManager.fillReport(jr, params, ds);

		// Create our output byte stream
		// This is the stream where the data will be written
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		// Export to output stream
		// The data will be exported to the ByteArrayOutputStream baos
		// We delegate the exporting  to a custom Exporter instance
		// The Exporter is a wrapper class I made. Feel free to remove or modify it
		Exporter exporter = new Exporter();
		exporter.export(jp, baos);

		// Set our response properties
		// Here you can declare a custom filename
		String fileName = "SalesReport.xls";
		response.setHeader("Content-Disposition", "inline; filename="
				+ fileName);
		// Make sure to set the correct content type
		// Each format has its own content type
		response.setContentType("application/vnd.ms-excel");
		response.setContentLength(baos.size());

		// Write to reponse stream
		writeReportToResponseStream(response, baos);
	}
	
	/**
	 * Writes the report to the output stream
	 */
	private void writeReportToResponseStream(HttpServletResponse response,
			ByteArrayOutputStream baos) {
		
		logger.debug("Writing report to the stream");
		try {
			// Retrieve the output stream
			ServletOutputStream outputStream = response.getOutputStream();
			// Write to the output stream
			baos.writeTo(outputStream);
			// Flush the stream
			outputStream.flush();

		} catch (Exception e) {
			logger.error("Unable to write report to the output stream");
		}
	}

}
