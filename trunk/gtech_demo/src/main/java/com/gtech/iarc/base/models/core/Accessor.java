
package com.gtech.iarc.base.models.core;

import java.lang.reflect.Method;

/** <code>Accessor</code> describe individual member field of the data object, particularly
 * its' accessor. Application program should not directly deal with this class, hence the
 * reason for the package-scope default constructor.
 */
@SuppressWarnings("unchecked")
public final class Accessor {
    private int pos;
    private Method getter;
    private Method setter;
    private String memberName;

	private Class memberClass;
    private Class dataObjectClass;
    
    /** Constructs the <code>Accessor</code>. */
    Accessor(String memberName, Class dataObjectClass, Method getter, Method setter, int pos) {
        this.memberName = memberName;
        this.memberClass = getter.getReturnType();
        this.dataObjectClass = dataObjectClass;
        this.getter = getter;
        this.setter = setter;
        this.pos = pos;
    }
    
    /** Get the member name associated by the accessor. */
    public final String memberName() { return memberName; }    
    
    /** Get the data object class the accessor belongs to. */
    public final Class memberClass() { return memberClass; }
    
    /** Getter method of the accessor. */
    public final Method getter() { return getter; }
    
    /** Setter method of the accessor. */
    public final Method setter() { return setter; }
    
    /** Ordinal position of the accessor. A unique number for the member in the data object. */
    public final int pos() { return pos; }
}