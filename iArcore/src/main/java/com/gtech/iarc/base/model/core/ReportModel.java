package com.gtech.iarc.base.model.core;

import java.util.List;

/** <code>ReportModel</code> is the holder for both report view and its' model for rendering the
 * report.  
 */
public class ReportModel {
    /** Report view reference to the bean to be resolved during rendering of report. */
    private String reportView;
    
    /** Model */
    private java.util.Map model;
    
    /** Parameters */
    private java.util.Map parameters;

    /** Images */
    private java.util.Map images;
    
    /** List of report models*/
    private List reportModelList;
    
    /** Default constructor. */
    public ReportModel() {
        // Do nothing
    }
    
    /** Constructs a <code>ReportModel</code> with the <tt>reportView</tt> and <tt>model</tt>. */
    public ReportModel(String reportView, java.util.Map model) {
        this.reportView = reportView;        
        this.model = model;
    }
    
    /** Constructs a <code>ReportModel</code> with the <tt>reportView</tt>, <tt>model</tt>, and <tt>parameters</tt>. */
    public ReportModel(String reportView, java.util.Map model, java.util.Map parameters) {
        this.reportView = reportView;
        this.parameters = parameters;
        this.model = model;
    }
    
    /** Set a report view name for this <code>ReportModel</code>. */
    public void setReportView(String reportView) {
        this.reportView = reportView;
    }

    /** Return the report view name to be resolved when rendering the report. */
    public String getReportView() {
        return reportView;
    }
    
    /** Set a model for this <code>ReportModel</code>. */
    public void setModel(java.util.Map model) {
        this.model = model;
    }

    /** Return the model map. Never returns null. To be called by application code for modifying the model. */
    public java.util.Map getModel() {
        if (this.model == null) {
            this.model = new java.util.HashMap(1);
        }
        return this.model;
    }
    
    /** Set a parameters for this <code>ReportModel</code>. */
    public void setParameters(java.util.Map parameters) {
        this.parameters = parameters;
    }

    /** Return the parameters map. Never returns null. To be called by application code for modifying the parameters. */
    public java.util.Map getParameters() {
        if (this.parameters == null) {
            this.parameters = new java.util.HashMap(1);
        }
        return this.parameters;
    }
    
    public java.util.Map getImages() {
        return images;
    }

    public void setImages(java.util.Map images) {
        this.images = images;
    }

    public List getReportModelList() {
        return reportModelList;
    }

    public void setReportModelList(List reportModelList) {
        this.reportModelList = reportModelList;
    }
    
    
}