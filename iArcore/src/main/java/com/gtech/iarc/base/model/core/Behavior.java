package com.gtech.iarc.base.model.core;

import java.io.Serializable;

/** <code>Behavior</code> maintains the field's required states, i.e. readonly/editable, hidden/visible,
 * mandatory/optional.
 * $Id$ 
 */
public class Behavior implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9197112501979556849L;
	public final static int NONE	  = 0x00;
    public final static int READONLY  = 0x01;
    public final static int HIDDEN    = 0x02;
    public final static int MANDATORY = 0x04;
    public final static int ALL       = READONLY | HIDDEN | MANDATORY;
    
    /** Required state of the field, default to editable, visible and optional states. */
    int state = 0x00;
    int previousState = state;
    private String fieldLabel;
    
    public int getState() { 
        return state; 
    }
    
    public void setState(int state) {
        this.previousState = this.state;
        this.state |= state;
    }
    
    public String getFieldLabel() {
        return fieldLabel;
    }
    
    public void setFieldLabel(String fieldLabel) {
        this.fieldLabel = fieldLabel;
    }
    
    public void toggleState(int state) { 
        this.state ^= state; 
    }
    
    public boolean isStateChanged() { 
        return previousState != state; 
    }
    
    public int getStateChanged() {
        return previousState ^ state;
    }
    
    public boolean getIsReadOnly() {
        return (state & Behavior.READONLY) == Behavior.READONLY;
    }
    
    public boolean getIsEditable() {
        return (state & Behavior.READONLY) == 0;
    }
    
    public boolean getIsSuppress() {
        return (state & Behavior.HIDDEN) == Behavior.HIDDEN;
    }
    
    public boolean getIsViewable() {
        return (state & Behavior.HIDDEN) == 0;
    }
    
    public boolean getIsMandatory() {
        return (state & Behavior.MANDATORY) == Behavior.MANDATORY;
    }
    
    public boolean getIsOptional() {
        return (state & Behavior.MANDATORY) == 0;
    }
}