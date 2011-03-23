package com.gtech.iarc.base.service;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gtech.iarc.base.models.UserAccount;

/**
 * Finds account objects using the Hibernate API.
 */
public class UserAccountService {

	private SessionFactory sessionFactory;


	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional(propagation=Propagation.SUPPORTS)
	public UserAccount findByUserName(String userName) {
		Query query = getCurrentSession().createQuery(
				"from UserAccount ua where ua.username = ?");
		query.setString(0, userName);		
		return (UserAccount) query.uniqueResult();
	}

	/**
	 * Returns the session associated with the ongoing reward transaction.
	 * 
	 * @return the transactional session
	 */
	protected Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
}