package com.gtech.iarc.base.persistence;

import com.gtech.iarc.base.model.core.IDataObject;

/**
 * Data Access Object (DAO) interface. This is an interface used to tag our DAO
 * classes and to provide common methods to all DAOs.
 */
@SuppressWarnings("unchecked")
public interface BaseDAO {
	
	/**
	 * Generic method to read a data object based on the given <tt>clazz</tt>
	 * and <tt>id</tt>.
	 * 
	 * @param clazz
	 *            of the data object model. May not be null.
	 * @param id
	 *            the identifier (primary key) of the data object model. May not
	 *            be null.
	 * @return IDataObject.
	 */

	public IDataObject read(Class clazz, java.io.Serializable id);

	/**
	 * Generic method to read a list of data objects by templating example given
	 * the <tt>dataobject</tt> with partially filled members as the criteria.
	 * 
	 * @param dataobject
	 *            model. May not be null.
	 * @return java.util.List of data objects.
	 */
	public java.util.List getDataObjects(IDataObject dataobject);

	/**
	 * Generic method to read all data objects given the <tt>clazz</tt>.
	 * 
	 * @param clazz
	 *            of the data object model. May not be null.
	 * @return java.util.List of all data objects of the given <tt>clazz</tt>.
	 */
	public java.util.List getAllDataObjects(Class clazz);

	/**
	 * Generic method to get a single value given the <tt>clazz</tt>, identifier
	 * <tt>id</dd>, and
	 * the <tt>memberName</tt>.
	 * 
	 * @param clazz
	 *            of the data object model. May not be null.
	 * @param id
	 *            the identifier (primary key) of the data object model. May not
	 *            be null.
	 * @param memberName
	 *            to get the value.
	 */
	public Object getSingleMemberValue(Class clazz, java.io.Serializable id,
			String memberName);

	/**
	 * Generic method to get a list of members' values given the <tt>clazz</tt>,
	 * identifier <tt>id</dd>, and
	 * the <tt>memberNames</tt>.
	 * 
	 * @param clazz
	 *            of the data object model. May not be null.
	 * @param id
	 *            the identifier (primary key) of the data object model. May not
	 *            be null.
	 * @param memberName
	 *            to get the value.
	 */
	public java.util.List getMembersValues(Class clazz,
			java.io.Serializable id, String[] memberNames);

	/**
	 * Generic method to save a given <tt>object</tt>.
	 * 
	 * @param dataobject
	 *            to be saved. May not be null.
	 * @return Object saved.
	 */
	public Object save(Object dataobject);

	/**
	 * Generic method to delete a data object given the <tt>clazz</tt> of it and
	 * the <tt>id</tt>.
	 * 
	 * @param clazz
	 *            of the data object model. May not be null.
	 * @param id
	 *            the identifier (primary key) of the data object model. May not
	 *            be null.
	 */
	public void delete(Class clazz, java.io.Serializable id);

	/**
	 * Generic method to delete a <tt>dataobject</tt> and the <tt>id</tt>. An
	 * overloaded class that merely do a dataobject.getClass() for convenience.
	 * 
	 * @param clazz
	 *            of the data object model. May not be null.
	 * @param id
	 *            the identifier (primary key) of the data object model. May not
	 *            be null.
	 */
	public void delete(Object dataobject, java.io.Serializable id);

	/**
	 * Generic method to perform hql or sql <tt>query</tt> and return the result
	 * of it.
	 * 
	 * @param query
	 *            to be executed.
	 * @return java.util.List of result from the query.
	 */
	public java.util.List query(String query);

	/**
	 * Generic method to perform hql or sql <tt>query</tt> and its' params and
	 * return the result of it.
	 * 
	 * @param query
	 *            to be executed.
	 * @param params
	 *            for the query.
	 * @return java.util.List of result from the query.
	 */
	public java.util.List query(String query, Object[] params);

	/**
	 * Limit the search to the given <tt>maxResults</tt> that performs the
	 * query.
	 */
	public java.util.List query(String query, Object[] params, int maxResults);

	/**
	 *  Limit the search to the given <tt>firstResult</tt> and <tt>maxResults</tt> 
	 *  that performs the query for pagination.
	 */
	public java.util.List query(String query, Object[] params, int firstResult, int maxResults);
	/**
	 * Convenient finder which proxy to the HibernateTemplate.find(String) for
	 * HQL string
	 * 
	 * @param query
	 *            of HQL string.
	 * @return java.util.List of result.
	 */
	public java.util.List find(String query);

	/**
	 * Convenient finder which proxy to the HibernateTemplate.find(String,
	 * Object) for HQL string
	 * 
	 * @param query
	 *            of HQL string.
	 * @param param
	 *            for the query.
	 * @return java.util.List of result.
	 */
	public java.util.List find(String query, Object param);

	/**
	 * Convenient finder which proxy to the HibernateTemplate.find(String,
	 * Object[]) for HQL string
	 * 
	 * @param query
	 *            of HQL string.
	 * @param params
	 *            for the query.
	 * @return java.util.List of result.
	 */
	public java.util.List find(String query, Object[] params);

	/**
	 * Execute query of type pseudo-syntax for UPDATE and DELETE statements,
	 * ie.( UPDATE | DELETE ) FROM? EntityName (WHERE where_conditions)?
	 * 
	 * @see http://www.hibernate.org/hib_docs/v3/reference/en/html/batch.html
	 */
	public int executeUpdate(String query, Object[] params);

	/**
	 * Return true if the given dataobject of POJO type has been persisted.
	 * 
	 * @param dataobject
	 *            to check if persisted.
	 * @return boolean true if the dataobject is persisted, otherwise false.
	 */
	public boolean isPersisted(IDataObject dataobject);

	/** Given the <tt>dataobject</tt>, get the primary key belonging to it. */
	public java.io.Serializable getPrimaryKey(IDataObject dataobject);
}