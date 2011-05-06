package com.gtech.iarc.base.persistence.hibernate;


import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.gtech.iarc.base.model.core.BaseObject;
import com.gtech.iarc.base.model.core.IDataObject;
import com.gtech.iarc.base.persistence.BaseRepository;

/** <code>BaseObjectHibernateRepository</code> defines a skeletal base for base object. 
 */
@SuppressWarnings("unchecked")
public class BaseObjectHibernateRepository extends HibernateDaoSupport implements BaseRepository {
    protected final Log log = LogFactory.getLog(BaseObjectHibernateRepository.class);

    static final char patternChar = '*';
    static final char enableLikeChar = '%';

    /**
     * @see com.y3technologies.persistence._DAO#read(Class, java.io.Serializable)
     */
    
	public BaseObject read(Class clazz, java.io.Serializable id) {
        return id == null ? null : (BaseObject) getHibernateTemplate().get(clazz, id);
    }
    
    /**
     * @see com.y3technologies.persistence.DAO#getDataObjects(IDataObject)
     */
    public List getBaseObjects(final BaseObject dataobject) {
        List results = (List) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session ses) throws HibernateException {
                org.hibernate.criterion.Example example = org.hibernate.criterion.Example.create(dataobject)
                    .excludeZeroes()                        // exclude zero valued properties
                    .ignoreCase()                           // perform case insensitive string comparisons
                    .enableLike();                          // allow enable like using %
                List results = ses.createCriteria(dataobject.getClass()).add(example).list();
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
     * @see com.y3technologies.persistence._DAO#getAllBaseObjects(Class)
     */
    public List getAllBaseObjects(Class clazz) {
        getHibernateTemplate().setCacheQueries(true);
        return getHibernateTemplate().loadAll(clazz);
    }

   
    /**
     * @see com.y3technologies.persistence._DAO#save(Object)
     */
    public BaseObject save(BaseObject dataobject) {
    	getHibernateTemplate().saveOrUpdate(dataobject);
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
            	logger.error("");
            }
        }
    }
    
    /**
     * @see com.y3technologies.persistence._DAO#delete(Object, java.io.Serializable)
     */
    public void delete(BaseObject dataobject, java.io.Serializable id) {
        delete(dataobject.getClass(), id);
    }
    
    /**
     * @see com.y3technologies.persistence._DAO#query(String)
     */
	public List query(final String query) {
        if (query == null) {
            return null;
        }
        
        return (List) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session ses) throws HibernateException {
                org.hibernate.Query hQuery = ses.createQuery(query);
                return hQuery.list();
            }
        });
    }
    
    /**
     * @see com.y3technologies.persistence._DAO#query(String, String[])
     */
    public List query(final String query, final Object[] params) {
        if (query == null) {
            return null;
        }

        return (List) getHibernateTemplate().execute(new HibernateCallback() {
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
    public List query(final String query, final Object[] params, final int maxResults) {
        if (query == null) {
            return null;
        }

        return (List) getHibernateTemplate().execute(new HibernateCallback() {
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
     * @see com.y3technologies.persistence._DAO#query(String, String[], int)
     */
    public List query(final String query, final Object[] params, final int firstResult, final int maxResults) {
        if (query == null) {
            return null;
        }

        return (List) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session ses) throws HibernateException {
                org.hibernate.Query hQuery = ses.createQuery(query);
                hQuery.setFirstResult(firstResult);
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
    public List find(String query) {
        return getHibernateTemplate().find(query);
    }
    
    /**
     * @see com.y3technologies.persistence._DAO#find(String, Object)
     */
    public List find(String query, Object param) {
        return getHibernateTemplate().find(query, param);
    }
    
    /**
     * @see com.y3technologies.persistence._DAO#find(String, Object[])
     */
    public List find(String query, Object[] params) {
        return getHibernateTemplate().find(query, params);
    }
    
    /**
     * @see com.y3technologies.persistence._DAO#executeUpdate(String, Object[])
     */
    public int executeUpdate(String query, Object[] params) {
        return getHibernateTemplate().bulkUpdate(query, params);
    }


    protected ClassMetadata getClassMeta(final Class clazz) {
        ClassMetadata classMeta = (ClassMetadata) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session ses) throws HibernateException {
                return ses.getSessionFactory().getClassMetadata(clazz);  
            }
        });
        return classMeta;
    }

	public List findByExample(final Class sampleCls, final BaseObject sample) {
		List rs = (List) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session ses)
							throws HibernateException {
						Example example = Example.create(sample)
								.excludeZeroes() // exclude zero valued
													// properties
								.ignoreCase() // perform case insensitive string
												// comparisons
								.enableLike();
						// .excludeProperty("parameterObjectInBlob");

						List results = ses.createCriteria(sampleCls).add(
								example).list();

						return results;
					}
				});
		return rs;
	}
//    protected void createdOrModifiedByDate(Object object, Object key) {
//        if (IDataObject.class.isAssignableFrom(object.getClass())) {
//            IDataObject dataobject = (IDataObject) object;
//            java.sql.Timestamp now = new java.sql.Timestamp(DateUtil.getTodayDateInMillisInSeconds());
//
//            if (dataobject.hasMember("createdBy") && key == null) {
//                if (UserProfileInfo.getUserId() != null) {
//                    dataobject.setMemberValue("createdBy", UserProfileInfo.getUserId());
//                }
//                dataobject.setMemberValue("createdDate", now);
//            } else
//            if (dataobject.hasMember("modifiedBy")) {
//                if (UserProfileInfo.getUserId() != null) {
//                    dataobject.setMemberValue("modifiedBy", UserProfileInfo.getUserId());
//                }
//                dataobject.setMemberValue("modifiedDate", now);
//            }
//            dataobject.nullEmptyString();
//        }
//    }
}