package com.gtech.iarc.base.model.core;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gtech.iarc.base.util.DeepCopy;

/** <code>DataObject</code> is the base for all data object.
 */
@SuppressWarnings("unchecked")
public class DataObject implements IDataObject, IDirtyMarker, IStateObjectInfo, java.io.Serializable {
    /* serialVersionUID */
    static final long serialVersionUID = 454858661699864556L;
    
    // Logger
    private static Log log = LogFactory.getLog(DataObject.class);

    // Constants 
    protected static final BigDecimal zero = new BigDecimal(0.0).setScale(2, BigDecimal.ROUND_HALF_DOWN);
    
    // New flag
    protected boolean newFlag;
    
    // Dirty flags
    protected boolean[] dirtyMembers;
    
    // Changed notification flags
    protected boolean[] changeNotifications;

    // Exception members flags
    protected boolean[] exceptionMembers;
    
    // To be deleted flag
    protected boolean isToBeDeleted;
    
    // User Id
    protected String userId;
    
    // User preferred date pattern
    protected String userDatePattern;
    
    // Exception flag
    protected boolean exception;
    
    // Last change notification member
    protected String lastChangeNotificationMember;
    
    // Fields' state
    protected Behavior[] behaviors;

    // Snapshot value
    protected Object[] snapshots;
    
    /** Default constructor. */
    public DataObject() {
        _init();
    }
    
	public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.MULTI_LINE_STYLE);
    }

    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    public int hashCode(Object o) {
        return HashCodeBuilder.reflectionHashCode(this);
    }    
    /** Initializes the data object to like a new state for reusability purposes. */
    public void init() {
        resetDirty();
        resetMembers();
        resetBehaviors();
        resetChangeNotifications();
    }
    
    /** Get the number of members in the data object. */
    public int getMembers() {
        Metadata metadata = Metadata.getMetadata(this.getClass());
        return metadata.accessors().size();
    }
    
    /** Get all member names of the data object. */

	public String[] getAllMembers() {
        java.util.List members = new java.util.ArrayList();
        Metadata metadata = Metadata.getMetadata(this.getClass());
        for (java.util.Iterator iter = metadata.accessors().iterator(); iter.hasNext(); ) {
            Accessor accessor = (Accessor) iter.next();
            members.add(accessor.memberName());
            
        }
        return (String[]) members.toArray(new String[members.size()]);
    }
    
    /** Get the member name given the index position of the data object. */
    public String getMember(int idx) {
        Metadata metadata = Metadata.getMetadata(this.getClass());
        for (java.util.Iterator iter = metadata.accessors().iterator(); iter.hasNext(); ) {
            Accessor accessor = (Accessor) iter.next();
            if (accessor.pos() == idx) {
                return accessor.memberName();
            }
        }
        return StringUtils.EMPTY;
    }
    
    public Object getMemberValue(String memberName) {
        if (memberName != null) {
            return get(memberName);
        }
        return null;
    }
    
    public void nullEmptyString() {
        Metadata metadata = Metadata.getMetadata(this.getClass());
        for (java.util.Iterator iter = metadata.accessors().iterator(); iter.hasNext(); ) {
            Accessor accessor = (Accessor) iter.next();
            if (accessor.memberClass() == String.class) {
                String string = (String) get(accessor.memberName());;
                if (string != null && string.trim().length() == 0) {
                    setMemberValue(accessor.memberName(), null);
                }
            } 
        }        
    }
    
    /** Reset a single member's change notification. */
    public void resetChangeNotification(String memberName) {
        Metadata metadata = Metadata.getMetadata(this.getClass());
        Accessor accessor = metadata.accessor(memberName);
        changeNotifications[accessor.pos()] = false;
    }
    
    /** Reset the dirty state of the data object, just like a new state, for
     * reusability purposes. 
     */
    public void resetDirty() {
        for (int i = 0; i < dirtyMembers.length; i++) {
            dirtyMembers[i] = false;
        }
    }
    
    /** Reset the exception state of the data object, just like no exception has
     * occured.
     */
    public void resetExceptions() {
        for (int i = 0; i < exceptionMembers.length; i++) {
            exceptionMembers[i] = false;
        }
    }
    
    /** Reset the members of the data object, for reusability purposes. */
    public void resetMembers() {
        Metadata metadata = Metadata.getMetadata(this.getClass());
        setDefaultNull(metadata.accessors());
    }
    
    /** Reset the change notification. */
    public void resetChangeNotifications() {
        for (int i = 0; i < changeNotifications.length; i++) {
            changeNotifications[i] = false;
        }
    }
    
    /** Reset the fields' state of the data object. */
    public void resetBehaviors() {
        for (int i = 0; i < behaviors.length; i++) {
            behaviors[i] = new Behavior();
        }
    }
    
    /** Set the <tt>member</tt> with the given <tt>value</tt>. */
    public void setMemberValue(String member, Object value) {
        // Exclusively defend setting the same value again
        if (!isBooleanValueDiff(member, value)) {
            if (isValueDiff(member, value)) {
                setMemberDirty(member, true);
                set(member, value);
            }
        }
    }
    
    /** Check if the data object is subjected to be deleted. */
    public boolean getIsToBeDeleted() {
        return isToBeDeleted;
    }
    
    /** Mark the data object to be deleted. */
    public void setIsToBeDeleted(boolean isToBeDeleted) {
        this.isToBeDeleted = isToBeDeleted;
    }
    
    /** Check if the given <tt>member</tt> has been changed. */
    public boolean isChange(String member) {
        Metadata metadata = Metadata.getMetadata(this.getClass());
        Accessor accessor = metadata.accessor(member);
        if (accessor != null) {
            return changeNotifications[accessor.pos()];
        } 
        return false;
    }
    
    /** Overrall state of the data object. */
    public boolean isDirty() { 
        for (int i = 0; i < dirtyMembers.length; i++) {
            if (dirtyMembers[i]) { return true; }
        }
        return false;
    }
    
    /** Get a list of dirty members. */
    public String[] getDirtyMembers() {
        java.util.List members = new java.util.ArrayList();
        if (isDirty()) {
            Metadata metadata = Metadata.getMetadata(this.getClass());
            for (int i = 0; i < dirtyMembers.length; i++) {
                if (dirtyMembers[i]) {
                    members.add(metadata.accessor(i).memberName());
                }
            }
        }
        return (String[]) members.toArray(new String[members.size()]);
    }
    
    /** Get the dirty state of the <tt>member</tt>. */
    public boolean getMemberDirty(String member) {
        Metadata metadata = Metadata.getMetadata(this.getClass());
        Accessor accessor = metadata.accessor(member);
        return dirtyMembers[accessor.pos()];
    }
    
    /** Set the <tt>member</tt> with the given <tt>dirty</tt> state. */
    public void setMemberDirty(String member, boolean dirty) {
        Metadata metadata = Metadata.getMetadata(this.getClass());
        Accessor accessor = metadata.accessor(member);
        dirtyMembers[accessor.pos()] = dirty;
        changeNotifications[accessor.pos()] = dirty;
    }

    /** Get the members that have still exceptions flag active. */
    public String[] getMembersException() {
        java.util.List members = new java.util.ArrayList();
        if (isException()) {
            Metadata metadata = Metadata.getMetadata(this.getClass());
            for (int i = 0; i < exceptionMembers.length; i++) {
                if (exceptionMembers[i]) {
                    members.add(metadata.accessor(i).memberName());
                }
            }
        } 
        return (String[]) members.toArray(new String[members.size()]);
    }
    
    /** Get the exception flag of the <tt>member</tt>. */
    public boolean getMemberException(String member) {
        Metadata metadata = Metadata.getMetadata(this.getClass());
        Accessor accessor = metadata.accessor(member);
        return exceptionMembers[accessor.pos()];
    }
    
    /** Set the <tt>member</tt> with the given <tt>exception</tt> state. */
    public void setMemberException(String member, boolean exception) {
        Metadata metadata = Metadata.getMetadata(this.getClass());
        Accessor accessor = metadata.accessor(member);
        exceptionMembers[accessor.pos()] = exception;
    }
    
    /** Get the snapshot of the given <tt>member</tt>. */
    public Object getMemberSnapshot(String member) {
        Metadata metadata = Metadata.getMetadata(this.getClass());
        Accessor accessor = metadata.accessor(member);
        return snapshots[accessor.pos()];
    }

    /** Copy from the given <tt>dataObject</tt> to this. */
    public void copy(IDataObject dataObject) {
        DataObject that = (DataObject) dataObject;
        copyMembers(that);

        this.newFlag = that.newFlag;
        this.exception = that.exception;
        this.isToBeDeleted = that.isToBeDeleted;
        
        this.userId = that.userId;
        this.userDatePattern = that.userDatePattern;
        this.lastChangeNotificationMember = that.lastChangeNotificationMember;
        
        System.arraycopy(that.dirtyMembers, 0, this.dirtyMembers, 0, that.dirtyMembers.length);
        System.arraycopy(that.exceptionMembers, 0, this.exceptionMembers, 0, that.exceptionMembers.length);
        System.arraycopy(that.changeNotifications, 0, this.changeNotifications, 0, that.changeNotifications.length);
    }
    
    /** Deep copy from the given <tt>dataObject</tt> to this. */
    public void deepCopy(IDataObject dataObject) {
        DataObject that = (DataObject) dataObject;
        deepCopyMembers(that);

        this.newFlag = that.newFlag;
        this.exception = that.exception;
        this.isToBeDeleted = that.isToBeDeleted;
        
        this.userId = that.userId;
        this.userDatePattern = that.userDatePattern;
        this.lastChangeNotificationMember = that.lastChangeNotificationMember;
        
        System.arraycopy(that.dirtyMembers, 0, this.dirtyMembers, 0, that.dirtyMembers.length);
        System.arraycopy(that.exceptionMembers, 0, this.exceptionMembers, 0, that.exceptionMembers.length);
        System.arraycopy(that.changeNotifications, 0, this.changeNotifications, 0, that.changeNotifications.length);
    }
    
    public boolean hasMember(String memberName) {
        Metadata metadata = Metadata.getMetadata(this.getClass());
        Accessor accessor = metadata.accessor(memberName);
        return accessor != null ? true : false;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getUserDatePattern() {
        return userDatePattern;
    }
    
    public void setUserDatePattern(String userDatePattern) {
        this.userDatePattern = userDatePattern;
    }
    
    public boolean isException() {
        for (int i = 0; i < exceptionMembers.length; i++) {
            if (exceptionMembers[i]) {
                return true;
            }
        }
        return false;
    }
    
    public String getLastChangeNotificationMember() {
        return lastChangeNotificationMember;
    }
    
    public void setLastChangeNotificationMember(String memberName) {
        this.lastChangeNotificationMember = memberName;
    }

    public boolean isReadOnly(String memberName) {
        Metadata metadata = Metadata.getMetadata(this.getClass());
        Accessor accessor = metadata.accessor(memberName);
        return behaviors[accessor.pos()].getIsReadOnly();
    }
    
    public boolean isEditable(String memberName) {
        Metadata metadata = Metadata.getMetadata(this.getClass());
        Accessor accessor = metadata.accessor(memberName);
        return behaviors[accessor.pos()].getIsEditable();
        
    }
    
    public boolean isSuppress(String memberName) {
        Metadata metadata = Metadata.getMetadata(this.getClass());
        Accessor accessor = metadata.accessor(memberName);
        return behaviors[accessor.pos()].getIsSuppress();
    }
    
    public boolean isViewable(String memberName) {
        Metadata metadata = Metadata.getMetadata(this.getClass());
        Accessor accessor = metadata.accessor(memberName);
        return behaviors[accessor.pos()].getIsViewable();
    }
    
    public boolean isMandatory(String memberName) {
        Metadata metadata = Metadata.getMetadata(this.getClass());
        Accessor accessor = metadata.accessor(memberName);
        return behaviors[accessor.pos()].getIsMandatory();
    }
    
    public boolean isOptional(String memberName) {
        Metadata metadata = Metadata.getMetadata(this.getClass());
        Accessor accessor = metadata.accessor(memberName);
        return behaviors[accessor.pos()].getIsOptional();
    }
    
    public void setMemberState(String memberName, int state) {
        Metadata metadata = Metadata.getMetadata(this.getClass());
        Accessor accessor = metadata.accessor(memberName);
        behaviors[accessor.pos()].setState(state);
    }

    public final Behavior getMemberState(String memberName) {
        Metadata metadata = Metadata.getMetadata(this.getClass());
        Accessor accessor = metadata.accessor(memberName);
    	return behaviors[accessor.pos()];
    }
    
    public void setReadOnly(String memberName) {
        setMemberState(memberName, Behavior.READONLY);
    }

    public void setEditable(String memberName) {
        setMemberState(memberName, Behavior.ALL ^ Behavior.READONLY);
    }
    
    public void setSuppress(String memberName) {
        setMemberState(memberName, Behavior.HIDDEN);
    }
    
    public void setViewable(String memberName) {
        setMemberState(memberName, Behavior.ALL ^ Behavior.HIDDEN);
    }
    
    public void setMandatory(String memberName) {
        setMemberState(memberName, Behavior.MANDATORY);
    }
    
    public void setOptional(String memberName) {
        setMemberState(memberName, Behavior.NONE);
    }

    protected void copyMembers(IDataObject dataObject) {
        DataObject that = (DataObject) dataObject;
        Metadata metadata = Metadata.getMetadata(this.getClass());
        for (Iterator iter = metadata.accessors().iterator(); iter.hasNext(); ) {
            Accessor _accessor = (Accessor) iter.next();
            String _memberName = _accessor.memberName();
            set(_memberName, that.getMemberValue(_memberName));
        }
    }

    protected void deepCopyMembers(IDataObject dataObject) {
        DataObject that = (DataObject) dataObject;
        Metadata metadata = Metadata.getMetadata(this.getClass());
        for (Iterator iter = metadata.accessors().iterator(); iter.hasNext(); ) {
            Accessor _accessor = (Accessor) iter.next();
            String _memberName = _accessor.memberName();
            Class _memberClass = _accessor.memberClass();
            if (IDataObject.class.isAssignableFrom(_memberClass)) {
                set(_memberName, DeepCopy.copy(that.getMemberValue(_memberName)));
            } else {
                // Normal copy
                set(_memberName, that.getMemberValue(_memberName));
            }
        }
    }
    
    Object get(String memberName) {
        Metadata metadata = Metadata.getMetadata(this.getClass());
        Accessor accessor = metadata.accessor(memberName);
        try {
            return accessor.getter().invoke(this, null);
        } catch (Throwable tr) {
            // Log as fatal
            log.fatal(tr);
        }
        return null;
    }
    
    void set(String memberName, Object value) {
        Object[] param = new Object[1];
        Metadata metadata = Metadata.getMetadata(this.getClass());
        Accessor accessor = metadata.accessor(memberName);
        try {
            if (accessor.memberClass() == String.class) {
                param[0] = value;
            } else
            if (accessor.memberClass() == Integer.class) {
                if (value != null) {
                    param[0] = Integer.valueOf(value.toString());
                } else {
                    param[0] = null;
                }
            } else
            if (accessor.memberClass() == Long.class) {
                if (value != null) {
                    param[0] = Long.valueOf(value.toString());
                } else {
                    param[0] = null;
                }
            } else
            if (accessor.memberClass() == Double.class) {
                if (value != null) {
                    param[0] = Double.valueOf(value.toString());
                } else {
                    param[0] = null;
                }
            } else 
            if (accessor.memberClass() == Boolean.class) {
                if (value != null) {
                    if (value.toString().matches("[01]")) {
                        if ("0".equals(value.toString())) {
                            param[0] = Boolean.FALSE;
                        } else {
                            param[0] = Boolean.TRUE;
                        }
                    } else {
                        param[0] = Boolean.valueOf(value.toString());
                    }
                } else {
                    param[0] = null;
                }
            } else {
                param[0] = value;
            }
            snapshot(memberName, getMemberValue(memberName));
            accessor.setter().invoke(this, param);
        } catch (Exception ex) {
            // Log as fatal
            log.fatal(ex);
        }
    }
        
    void snapshot(String member, Object value) {
        Metadata metadata = Metadata.getMetadata(this.getClass());
        Accessor accessor = metadata.accessor(member);
        if (accessor != null) {
            if (snapshots[accessor.pos()] == null) {
                snapshots[accessor.pos()] = value; 
            }
        }
    }
    
    private boolean isValueDiff(String member, Object value) {
        Metadata metadata = Metadata.getMetadata(this.getClass());
        Accessor accessor = metadata.accessor(member);
        if (accessor != null) {
            Object currentValue = getMemberValue(member);
            if (currentValue != null) {
                Class currentClass = currentValue.getClass();                
                if (value != null) {
                    Class valueClass = value.getClass();
                    if (currentClass == valueClass) {
                        return !currentValue.equals(value);
                    } else
                    if (currentClass == Long.class &&
                        value.getClass() == String.class) {
                        return !currentValue.equals(Long.valueOf(value.toString()));
                    } else 
                    if (currentClass == Double.class &&
                        value.getClass() == String.class) {
                        return !currentValue.equals(Double.valueOf(value.toString()));
                    } else
                    if (currentClass == Integer.class &&
                        value.getClass() == String.class) {
                        return !currentValue.equals(Integer.valueOf(value.toString()));
                    } else
                    if (currentClass == Boolean.class &&
                        value.getClass() == String.class) {
                        if (value.toString().matches("[01]")) {
                            if ("0".equals(value.toString())) {
                                return !currentValue.equals(Boolean.FALSE);
                            } else {
                                return !currentValue.equals(Boolean.TRUE);
                            }
                        } else {
                            return !currentValue.equals(Boolean.valueOf(value.toString()));
                        }
                    }
                } else {
                    return true;
                }
            } else
            if (value != null) {
                Class valueClass = value.getClass();
                // value is type String, if it's, make sure it's non-blank
                if (valueClass == String.class && StringUtils.isBlank((String) value)) {
                    return false;
                } 
                // All other non-string type, the different is true
                return true;
            } else {
                // Both is null
                return false;
            }
        }
        // Should throw an exception as the given <tt>member</tt> is not a member
        //  of the data object, a programming error has occurred!!!
        return false;
    }
    
    private boolean isBooleanValueDiff(String member, Object value) {
        if (value != null) {
	        Class clazz = value.getClass();
	        if (clazz == Boolean.class || clazz == boolean.class) {
		        Metadata metadata = Metadata.getMetadata(this.getClass());
		        Accessor accessor = metadata.accessor(member);
		        if (accessor != null) {
		            Boolean newValue = (Boolean) value;
		            Boolean currentValue = (Boolean) getMemberValue(member);
		            if (getMemberDirty(member) || newValue.booleanValue()) {
		                setMemberDirty(member, !getMemberDirty(member));
		            } else
		            if (currentValue != null && currentValue.booleanValue()) {	                
		                setMemberDirty(member, true);
		            } else {
		                setMemberDirty(member, false);
		            }
		            set(member, value);
		        }
		        // Has been handled
		        return true;
	        }
        }
        // Did not handle this
        return false;
    }
    
    /** This should only be called during a new data object construction. */
    private void _init() {
        newFlag = true;
        Metadata metadata = Metadata.getMetadata(this.getClass());
        dirtyMembers = new boolean[metadata.accessors().size()];
        snapshots = new Object[metadata.accessors().size()];
        exceptionMembers = new boolean[metadata.accessors().size()];
        changeNotifications = new boolean[metadata.accessors().size()];

        behaviors = new Behavior[metadata.accessors().size()];
        resetBehaviors();
    }
    
    /** Caused the all data object member to default to class default value, such as if 
     * the member class is an instance of String, it's defaulted to null, primitive boolean
     * to false, etc.
     */ 
    private final void setDefaultNull(Collection accessors) {
        final Class[] param = { null };
        for (Iterator iter = accessors.iterator(); iter.hasNext(); ) {
            Accessor accessor = (Accessor) iter.next();
            try {
                if (accessor.memberClass() == boolean.class) {
                    accessor.setter().invoke(this, new Object[] { Boolean.FALSE } );
                } else {
                    accessor.setter().invoke(this, param);
                }
            } catch (Exception ex) {
                // Classified as fatal
                log.fatal(ex);
            }
        }
    }    
    
    private final void setDefaultValue(Accessor accessor) {
        Class clz = accessor.memberClass();
        String memberName = accessor.memberName();
        if (clz == String.class) {
            setMemberValue(memberName, StringUtils.EMPTY);
        } else
        if (clz == Boolean.class) {
            setMemberValue(memberName, Boolean.FALSE);
        } else
        if (clz == Long.class || clz == long.class) {
            setMemberValue(memberName, new Long(0));
        } else
        if (clz == Double.class || clz == double.class) {
            setMemberValue(memberName, new Double(0));
        } else
        if (clz == Integer.class || clz == int.class) {
            setMemberValue(memberName, new Integer(0));
        } else
        if (clz == BigDecimal.class) {
            setMemberValue(memberName, new BigDecimal(0d));
        }
    }
}