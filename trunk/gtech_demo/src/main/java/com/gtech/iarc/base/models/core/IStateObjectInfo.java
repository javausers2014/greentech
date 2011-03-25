package com.gtech.iarc.base.models.core;

/** <code>IStateObjectInfo</code> captures and manages information on the state of the object's members.  
 * @author  Jiann
 * @version $Id$
 */
public interface IStateObjectInfo {
    public void resetBehaviors();
    public boolean isReadOnly(String memberName);
    public boolean isEditable(String memberName);
    public boolean isSuppress(String memberName);
    public boolean isViewable(String memberName);
    public boolean isMandatory(String memberName);
    public boolean isOptional(String memberName);
    
    public void setReadOnly(String memberName);
    public void setEditable(String memberName);
    public void setSuppress(String memberName);
    public void setViewable(String memberName);
    public void setMandatory(String memberName);
    public void setOptional(String memberName);
}