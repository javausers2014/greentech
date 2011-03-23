package com.gtech.iarc.base.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.gtech.iarc.base.models.UserAccount;
import com.gtech.iarc.base.models.UserPermission;
import com.gtech.iarc.base.service.UserAccountService;

public class DefaultUserDetailsService implements
		AuthenticationUserDetailsService {

	private UserAccountService userAccountService;


	public void setUserAccountService(UserAccountService userAccountService) {
		this.userAccountService = userAccountService;
	}

	public UserDetails loadUserDetails(Authentication token)
			throws UsernameNotFoundException {

		String userId = token.getPrincipal().toString();
		Collection<GrantedAuthority> authorities = getGrantedAuthorityAssigned(userId);

		return new User(userId, "NONE", true, true, true, true, authorities);
	}

	private Collection<GrantedAuthority> getGrantedAuthorityAssigned(
			String userId) {
		UserAccount user = userAccountService.findByUserName(userId);
		if (user == null) {
			throw new UsernameNotFoundException("User " + userId
					+ " is not found.");
		}
		Set<UserPermission> permSet = user.getPermissions();
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

//	private Collection<GrantedAuthority> convertPermissions(
//			List<String> permissions) {
//
//		List<GrantedAuthority> grantedAuthorities = Collections.emptyList();
//
//		if (permissions != null) {
//			grantedAuthorities = new ArrayList<GrantedAuthority>(permissions
//					.size());
//
//			for (final String permissionStr : permissions) {
//				grantedAuthorities.add(new GrantedAuthorityImpl(permissionStr));
//			}
//		}
//
//		return grantedAuthorities;
//	}

}
