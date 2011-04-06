package com.gtech.iarc.base.persistence.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.EntityMode;
import org.hibernate.Session;
import org.hibernate.LockMode;
import org.hibernate.HibernateException;
import org.hibernate.metadata.ClassMetadata;

import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.gtech.iarc.base.model.core.IDataObject;
import com.gtech.iarc.base.model.core.UserInfo;
import com.gtech.iarc.base.persistence.BaseDAO;
import com.gtech.iarc.base.util.DateUtil;

/** <code>BaseDAOHibernate</code> defines a skeletal base for data access object. 
 * @version $Revision: 1.4 $  $Date: 2006/11/12 10:59:38 $
 */
@SuppressWarnings("unchecked")
public class BaseDAOHibernate extends HibernateDaoSupport implements BaseDAO {
    protected final Log log = LogFactory.getLog(BaseDAOHibernate.class);

    static final char patternChar = '*';
    static final char enableLikeChar = '%';

    /**
     * @see com.y3technologies.persistence._DAO#read(Class, java.io.Serializable)
     */
    
	public IDataObject read(Class clazz, java.io.Serializable id) {
        return id == null ? null : (IDataObject) getHibernateTemplate().get(clazz, id);
    }
    
    /**
     * @see com.y3technologies.persistence.DAO#getDataObjects(IDataObject)
     */
    public java.util.List getDataObjects(final IDataObject dataobject) {
        enableLikeAllMembers(dataobject);
        java.util.List results = (java.util.List) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session ses) throws HibernateException {
                org.hibernate.criterion.Example example = org.hibernate.criterion.Example.create(dataobject)
                    .excludeZeroes()                        // exclude zero valued properties
                    .ignoreCase()                           // perform case insensitive string comparisons
                    .enableLike();                          // allow enable like using %
                java.util.List results = ses.createCriteria(dataobject.getClass()).add(example).list();
                return results;
            }
        });
        
        // Return an empty list if no criteria match
        if (results == null) {
            results = new java.util.ArrayList();
        }
        return results;
    }

    /**
     * @see com.y3technologies.persistence._DAO#getAllDataObjects(Class)
     */
    public java.util.List getAllDataObjects(Class clazz) {
        getHibernateTemplate().setCacheQueries(true);
        return getHibernateTemplate().loadAll(clazz);
    }

    /**
     * @see com.y3technologies.persistence._DAO#getSingleMemberValue(Class, java.io.Serializable, String)
     */
    public Object getSingleMemberValue(Class clazz, java.io.Serializable id, String memberName) {
        java.util.List values = getMembersValues(clazz, id, new String[] { memberName });
        if (values.size() > 1) {
            // Expected single value, but return more than one value
            log.error("BaseDAOHibernate.getSingleMemberValue() return more than one or more result values!");
        }
        if (values == null || values.size() == 0) {
            return null;
        }
        return values.get(0);
    }
    
    /**
     * @see com.y3technologies.persistence._DAO#getMembersValues(Class, java.io.Serializable, Object[])
     */
    public java.util.List getMembersValues(Class clazz, java.io.Serializable id, String[] memberNames) {
        ClassMetadata classMetadata = getClassMeta(clazz);

        // Bullet-proof if memberNames happens to be empty
        if (memberNames.length == 0) {
            return new java.util.ArrayList();
        }
        
        // Query
        final StringBuffer query = new StringBuffer("select ");
        for (int i = 0; i < memberNames.length; i++) {
            query.append(memberNames[i]);
            if (memberNames.length - (i+1) != 0) {
                query.append(",");
            }
        }
        query.append(" from ");
        query.append(classMetadata.getEntityName());
        query.append(" where ");
        query.append(classMetadata.getIdentifierPropertyName());
        query.append(" = ");
        query.append(id.toString());
        
        return (java.util.List) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session ses) throws HibernateException {
                return ses.createQuery(query.toString()).list();
            }
        });
    }
    
    /**
     * @see com.y3technologies.persistence._DAO#save(Object)
     */
    public Object save(Object dataobject) {
        ClassMetadata metadata = getClassMeta(dataobject.getClass());
        
        Object id = metadata.getIdentifier(dataobject, org.hibernate.EntityMode.POJO);
        createdOrModifiedByDate(dataobject, id);
        if (metadata.isVersioned() && id != null) {
            getHibernateTemplate().update(dataobject, LockMode.READ);
        } else {
            getHibernateTemplate().saveOrUpdate(dataobject);
        }
        return dataobject;
    }

    /**
     * @see com.y3technologies.persistence._DAO#delete(Class, java.io.Serializable)
     */
    public void delete(Class clazz, final java.io.Serializable id) {
        // Check if <tt>id</tt> has been set
        if (id == null) {
            throw new IllegalArgumentException("Id must not be null");
        }       
        
        Object objectToBeDeleted = read(clazz, id);
        if (objectToBeDeleted != null) {
            final ClassMetadata metadata = getClassMeta(clazz);
            final StringBuffer buffer = new StringBuffer("delete from ");  
            buffer.append(metadata.getEntityName());
            buffer.append(" where ");
            buffer.append(metadata.getIdentifierPropertyName());
            buffer.append(" = :id");
            
            try {
                getHibernateTemplate().execute(new HibernateCallback() {
                    public Object doInHibernate(Session ses) throws HibernateException {
                        org.hibernate.Query hQuery = ses.createQuery(buffer.toString());
                            // Best of my knowledge, all PK is long
                            if (metadata.getIdentifierType().getClass() == org.hibernate.type.LongType.class) {
                                hQuery.setLong("id", ((Long) id).longValue());
                            } 
                        hQuery.executeUpdate();
                        return null;
                    }
                });
            } catch (org.springframework.dao.DataIntegrityViolationException ex) {
                java.sql.Timestamp now = new java.sql.Timestamp(DateUtil.getTodayDateInMillisInSeconds());
            
                // Mark the inactive info
                if (objectToBeDeleted instanceof IDataObject) {
                    boolean found = false;
                    IDataObject dataobject = (IDataObject) objectToBeDeleted;
                    if (dataobject.hasMember("inactDate")) {
                        dataobject.setMemberValue("inactDate", now);
                        found = true;
                    }
                    if (dataobject.hasMember("inactBy")) {
                        dataobject.setMemberValue("inactBy", UserInfo.getUserId());
                        found = true;
                    }
                    if (found) {
                        save(dataobject);
                    }
                }   
            }
        }
    }
    
    /**
     * @see com.y3technologies.persistence._DAO#delete(Object, java.io.Serializable)
     */
    public void delete(Object dataobject, java.io.Serializable id) {
        delete(dataobject.getClass(), id);
    }
    
    /**
     * @see com.y3technologies.persistence._DAO#query(String)
     */
	public java.util.List query(final String query) {
        if (query == null) {
            return null;
        }
        
        return (java.util.List) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session ses) throws HibernateException {
                org.hibernate.Query hQuery = ses.createQuery(query);
                return hQuery.list();
            }
        });
    }
    
    /**
     * @see com.y3technologies.persistence._DAO#query(String, String[])
     */
    public java.util.List query(final String query, final Object[] params) {
        if (query == null) {
            return null;
        }

        return (java.util.List) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session ses) throws HibernateException {
                org.hibernate.Query hQuery = ses.createQuery(query);
                for (int i = 0; i < params.length; i++) {
                    hQuery.setParameter(i, params[i]);
                }
                return hQuery.list();
            }
        });
    }
    
    /**
     * @see com.y3technologies.persistence._DAO#query(String, String[], int)
     */
    public java.util.List query(final String query, final Object[] params, final int maxResults) {
        if (query == null) {
            return null;
        }

        return (java.util.List) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session ses) throws HibernateException {
                org.hibernate.Query hQuery = ses.createQuery(query);
                hQuery.setMaxResults(maxResults);
                for (int i = 0; i < params.length; i++) {
                    hQuery.setParameter(i, params[i]);
                }
                return hQuery.list();
            }
        });
    }

    /**
     * @see com.y3technologies.persistence._DAO#find(String)
     */
    public java.util.List find(String query) {
        return getHibernateTemplate().find(query);
    }
    
    /**
     * @see com.y3technologies.persistence._DAO#find(String, Object)
     */
    public java.util.List find(String query, Object param) {
        return getHibernateTemplate().find(query, param);
    }
    
    /**
     * @see com.y3technologies.persistence._DAO#find(String, Object[])
     */
    public java.util.List find(String query, Object[] params) {
        return getHibernateTemplate().find(query, params);
    }
    
    /**
     * @see com.y3technologies.persistence._DAO#executeUpdate(String, Object[])
     */
    public int executeUpdate(String query, Object[] params) {
        return getHibernateTemplate().bulkUpdate(query, params);
    }
    
    /**
     * @see com.y3technologies.persistence._DAO#isPersisted(IDataObject)
     */
    public boolean isPersisted(IDataObject dataobject) {
        boolean isPersisted = false;
        try {
            ClassMetadata classMetadata = getClassMeta(dataobject.getClass());
            java.io.Serializable key = classMetadata.getIdentifier(dataobject, EntityMode.POJO);
            isPersisted = key != null ? true : false;
        } catch (Exception ex) {
            // Do nothing
        }
        return isPersisted;
    }
    
    public java.io.Serializable getPrimaryKey(IDataObject dataobject) {
        ClassMetadata classMetadata = getClassMeta(dataobject.getClass());
        return classMetadata.getIdentifier(dataobject, EntityMode.POJO);
    }

    protected ClassMetadata getClassMeta(final Class clazz) {
        ClassMetadata classMeta = (ClassMetadata) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session ses) throws HibernateException {
                return ses.getSessionFactory().getClassMetadata(clazz);  
            }
        });
        return classMeta;
    }
    
    protected void enableLikeAllMembers(IDataObject dataobject) {
        ClassMetadata classMetadata = getClassMeta(dataobject.getClass());
        
        if (classMetadata == null) return;
        
        String[] properties = classMetadata.getPropertyNames(); 
        for (int i = 0; i < properties.length; i++) {
            Object object = dataobject.getMemberValue(properties[i]);
            // Enable like only deals with 
            if (object != null && object.getClass() == String.class) {
                String str = (String) object;
                if (str.length() == 0) {
                    str = null;
                } else {
                    str.replace(patternChar, enableLikeChar);
                }
                dataobject.setMemberValue(properties[i], str);
            }
        }
    }
    
    protected void createdOrModifiedByDate(Object object, Object key) {
        if (IDataObject.class.isAssignableFrom(object.getClass())) {
            IDataObject dataobject = (IDataObject) object;
            java.sql.Timestamp now = new java.sql.Timestamp(DateUtil.getTodayDateInMillisInSeconds());

            if (dataobject.hasMember("createdBy") && key == null) {
                if (UserInfo.getUserId() != null) {
                    dataobject.setMemberValue("createdBy", UserInfo.getUserId());
                }
                dataobject.setMemberValue("createdDate", now);
            } else
            if (dataobject.hasMember("modifiedBy")) {
                if (UserInfo.getUserId() != null) {
                    dataobject.setMemberValue("modifiedBy", UserInfo.getUserId());
                }
                dataobject.setMemberValue("modifiedDate", now);
            }
            dataobject.nullEmptyString();
        }
    }
}