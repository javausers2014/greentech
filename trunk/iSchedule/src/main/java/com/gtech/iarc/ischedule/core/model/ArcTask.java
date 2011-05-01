// Copyright(c) 2011 gTech, All Rights Reserved.
package com.gtech.iarc.ischedule.core.model;

import java.sql.Timestamp;

/**
 * 
 * @author ZHIDAO
 * @revision $Id$
 */
public class ArcTask implements IArcTask {
    static final long serialVersionUID = -4231837352277167582L;
    
	private Boolean activeInd;
    private String createdBy;
    private String modifiedBy;
    private String inactBy;    
    private Timestamp inactDate;
    private Timestamp createdDate;
    private Timestamp modifiedDate;    
    /**
     * @return Returns the activeInd.
     * @hibernate.property column="ACTIVE_IND"
     */
    public Boolean getActiveInd() {
        return activeInd;
    }

    /**
     * @return Returns the createdBy.
     * @hibernate.property column="CREATED_BY"
     *
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @return Returns the createdDate.
     * @hibernate.property column="CREATED_DATE"
     */
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    /**
     * @return Returns the inactBy.
     * @hibernate.property column="INACT_BY"
     */
    public String getInactBy() {
        return inactBy;
    }

    /**
     * @return Returns the inactDate.
     * @hibernate.property column="INACT_DATE"
     */
    public Timestamp getInactDate() {
        return inactDate;
    }

    /**
     * @return Returns the modifiedBy.
     * @hibernate.property column="MODIFIED_BY"
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * @return Returns the modifiedDate.
     * @hibernate.property column="MODIFIED_DATE"
     */
    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    /**
     * @param activeInd The activeInd to set.
     */
    public void setActiveInd(Boolean activeInd) {
        this.activeInd = activeInd;
    }

    /**
     * @param createdBy The createdBy to set.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @param createdDate The createdDate to set.
     */
    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @param inactBy The inactBy to set.
     */
    public void setInactBy(String inactBy) {
        this.inactBy = inactBy;
    }

    /**
     * @param inactDate The inactDate to set.
     */
    public void setInactDate(Timestamp inactDate) {
        this.inactDate = inactDate;
    }

    /**
     * @param modifiedBy The modifiedBy to set.
     */
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    /**
     * @param modifiedDate The modifiedDate to set.
     */
    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
