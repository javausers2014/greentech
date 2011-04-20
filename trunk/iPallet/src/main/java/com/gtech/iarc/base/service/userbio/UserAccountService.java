package com.gtech.iarc.base.service.userbio;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gtech.iarc.base.model.user.UserAccount;
import com.gtech.iarc.base.persistence.BaseDAO;
import com.gtech.iarc.base.persistence.BaseRepository;

/**
 * Finds account objects using the BaseDAO Hibernate API.
 */
@SuppressWarnings("unchecked")
public class UserAccountService {

	private BaseRepository baseRepository;


	public void setBaseRepository(BaseRepository baseRepository) {
		this.baseRepository = baseRepository;
	}

	@Transactional(propagation=Propagation.SUPPORTS)
	public UserAccount findByUserName(String userName) {		
		List rs = baseRepository.find("from UserAccount ua where ua.userName = ?", userName);
		return (UserAccount)rs.get(0);
	}

	@Transactional(propagation=Propagation.SUPPORTS)
	public List<UserAccount> getAllUsers() {
		return baseRepository.find("from UserAccount");		
	}
}