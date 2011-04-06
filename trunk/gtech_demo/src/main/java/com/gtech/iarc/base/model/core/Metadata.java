package com.gtech.iarc.base.model.core;

import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Collection;
import java.util.Collections;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/** <code>Metadata</code> describe the data object's metadata. Application program should
 * not directly use this method.
 * @author JIANN	$Revision: 1.4 $	$Date: 2006/07/27 09:24:21 $
 */
public final class Metadata implements java.io.Serializable {
    static final long serialVersionUID = 5887477839132027487L;

    /** Metadata by it's data object name. */
    private static final Map dataObjectMetdata = Collections.synchronizedMap(new HashMap());
    
    /** Data object name, incl its long qualify name. */
    private String dataObjectName;
    
    /** Data object class type. */
    private Class dataObjectClass;
    
    /** Accessors */
    private Set accessors;
    
    /** Mapping of member names to accessors. */
    private Map membersAccessors;
    
    /** Mapping of member pos to accessors. */
    private Map posAccessors;
    
    /** Convenient method to get the data object's metadata of the given <tt>clazz</tt>. */
    public static Metadata getMetadata(final Class clazz) {
        if (clazz != null) {
            Metadata _metadata = (Metadata) dataObjectMetdata.get(clazz.getName());
            if (_metadata == null) {
                // Probably the first time requesting
                _metadata = new Metadata(clazz);
            }
            return _metadata;
        }
        return null;
    }
    
    /** The long qualify name of the data object. */
    public String dataObjectName() { return dataObjectName; }
    
    /** The data object class. */
    public Class dataObjectClass() { return dataObjectClass; }
    
    /** Return the accessors belonging to the data object metadata. */
    public Collection accessors() { return accessors; }
    
    /** Return the accessor of the given <tt>memberName</tt>. */
    public Accessor accessor(String memberName) { return (Accessor) membersAccessors.get(memberName); } 
    
    /** Return the accessor of the given <tt>memberName</tt>. */
    public Accessor accessor(int memberPos) { return (Accessor) posAccessors.get(Integer.toString(memberPos)); } 
    
    /** Private constructor of constructing the Metadata of the given <tt>clazz</tt>, 
     * application program must not directly deal with this class.
     */
    private Metadata(Class clazz) {
        this.dataObjectClass = clazz;
        this.dataObjectName = clazz.getName();
        
        // Get all accessors
        int pos = 0;
        int modifier;
        Method getter;
        Method setter;
        String methodName;        
        Class returnClass;
        accessors = new HashSet();
        final Class[] param = { null };        
        final Method[] methods = clazz.getMethods();
        final StringBuffer setterName = new StringBuffer();        
        for (int i = 0; i < methods.length; i++) {
            getter = methods[i];
            methodName = getter.getName();
            modifier = getter.getModifiers();
            // Only interested in nonstatic public accessor
            if (Modifier.isPublic(modifier) && !Modifier.isStatic(modifier) &&
                methodName.length() > 3 && methodName.startsWith("get")) {
                returnClass = getter.getReturnType();
                if (getter.getParameterTypes().length == 0 && 
                    /* !returnClass.isInterface() && */ 
                    !Map.class.isAssignableFrom(returnClass) && 
                    !Class.class.isAssignableFrom(returnClass) && 
                    !Collection.class.isAssignableFrom(returnClass)) {
                    try {
                        setterName.setLength(0);
                        setterName.append("set");
                        setterName.append(methodName.substring(3));
                        param[0] = returnClass;
                        setter = dataObjectClass.getMethod(setterName.toString(), param);
                        String memberName = methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
                        accessors.add(new Accessor(memberName, dataObjectClass, getter, setter, pos++));
                    } catch (Exception ex) {
                        // Not interested as of now... Hibernate will also
                        //  ignore if the member has no setter method
                    }
                }
            }
        }
        
        if (accessors.size() > 0) {
            posAccessors = new HashMap();
            membersAccessors = new HashMap();
            for (Iterator iter = accessors.iterator(); iter.hasNext(); ) {
                Accessor accessor = (Accessor) iter.next();
                membersAccessors.put(accessor.memberName(), accessor);
                posAccessors.put(Integer.toString(accessor.pos()), accessor);
            }
        }
        
        dataObjectMetdata.put(dataObjectName, this);
    }
}