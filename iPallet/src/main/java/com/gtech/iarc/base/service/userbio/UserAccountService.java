package com.gtech.iarc.base.service.userbio;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gtech.iarc.base.model.user.UserAccount;
import com.gtech.iarc.base.persistence.BaseDAO;

/**
 * Finds account objects using the BaseDAO Hibernate API.
 */
@SuppressWarnings("unchecked")
public class UserAccountService {

	private BaseDAO baseDAO;

	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	@Transactional(propagation=Propagation.SUPPORTS)
	public UserAccount findByUserName(String userName) {		
		List rs = baseDAO.find("from UserAccount ua where ua.userName = ?", userName);
		return (UserAccount)rs.get(0);
	}

	@Transactional(propagation=Propagation.SUPPORTS)
	public List<UserAccount> getAllUsers() {
		return baseDAO.find("from UserAccount");		
	}
}