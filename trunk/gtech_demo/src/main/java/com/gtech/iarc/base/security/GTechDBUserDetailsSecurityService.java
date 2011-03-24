package com.gtech.iarc.base.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.gtech.iarc.base.models.UserAccount;
import com.gtech.iarc.base.models.UserPermission;
import com.gtech.iarc.base.service.UserAccountService;

public class GTechDBUserDetailsSecurityService implements
		UserDetailsService {

	private UserAccountService userAccountService;

	public void setUserAccountService(UserAccountService userAccountService) {
		this.userAccountService = userAccountService;
	}

	private Collection<GrantedAuthority> getGrantedAuthorityAssigned(
			List<UserPermission> permSet) {

		if (permSet.isEmpty()) {
			return Collections.emptyList();
		}
		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>(
				permSet.size());
		for (UserPermission uperm : permSet) {
			grantedAuthorities.add(new GrantedAuthorityImpl(uperm
					.getPermission().getPermissionCode()));
		}
		return grantedAuthorities;
	}

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		
		UserAccount user = userAccountService.findByUserName(username);
		if(user==null){
			throw new UsernameNotFoundException("Can't find user with name: "+username);
		}
		Collection<GrantedAuthority> authList = getGrantedAuthorityAssigned(user.getPermissions());
		
		return new User(username, user.getPassword(), true,true,true,true, authList);
	}
}
