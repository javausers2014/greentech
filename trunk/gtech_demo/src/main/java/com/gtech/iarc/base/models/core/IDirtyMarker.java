package com.gtech.iarc.base.models.core;

/** <code>IDirtyMarker</code> marks a dirty state of the data object/POJO and its' members
 * since the last load from the persistence store.
  */
public interface IDirtyMarker {
    public boolean isDirty();
    public boolean isChange(String member);
    public void resetDirty();
    public String[] getDirtyMembers();
    public boolean getMemberDirty(String member);
    public void setMemberDirty(String member, boolean dirty);    
}