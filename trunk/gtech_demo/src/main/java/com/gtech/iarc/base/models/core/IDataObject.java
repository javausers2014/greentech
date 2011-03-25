package com.gtech.iarc.base.models.core;

/** <code>IDataObject</code>.
 * @version $Id$
 */
public interface IDataObject extends IDirtyMarker, IStateObjectInfo {
    public void init();
    public void copy(IDataObject dataObject);
    public String[] getAllMembers();
    public int getMembers();    
    public void nullEmptyString();
    public String getMember(int idx);
    public Object getMemberValue(String memberName);
    public boolean hasMember(String memberName);
    public void setMemberValue(String memberName, Object value);
    public boolean getIsToBeDeleted();
    public void setIsToBeDeleted(boolean isToBeDeleted);
    public void resetChangeNotification(String memberName);
    public void resetChangeNotifications();

    public boolean isException();
    public void resetExceptions();
    public String[] getMembersException();
    public boolean getMemberException(String member);
    public void setMemberException(String member, boolean exception);
    public Object getMemberSnapshot(String member);
    public String getLastChangeNotificationMember();
    public void setLastChangeNotificationMember(String memberName);
    
    // User owning the data object during creation, updation, or deletion 
    public String getUserId();
    public void setUserId(String userId);
    
    // User's preference date pattern
    public String getUserDatePattern();
    public void setUserDatePattern(String userDatePattern);
}