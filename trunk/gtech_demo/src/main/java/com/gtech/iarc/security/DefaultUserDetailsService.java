package com.gtech.iarc.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class DefaultUserDetailsService implements AuthenticationUserDetailsService {


	private Logger logger = LoggerFactory.getLogger(DefaultUserDetailsService.class);
	

	public UserDetails loadUserDetails(Authentication token) throws UsernameNotFoundException {
		
		List<String> permissions = Collections.emptyList();
		
		String userId = token.getPrincipal().toString();
		
			try{
				permissions = getPermissionAssigned();
							
				if(permissions==null || permissions.size()==0){
					logger.warn("User "+ userId +" has no permission's set!");
				}else{
					if(logger.isDebugEnabled()){
						logger.debug("User " + userId + " has following permissions: " + permissions.toString());
					}
				}
			}catch (Exception e) {
				logger.error("Error getting permissions of user '"+ userId + "' from desb!", e);
			}
		

		return new User(userId, "NONE", true, true, true, true, convertPermissions(permissions));
	}
		
	private List<String> getPermissionAssigned() {
		// TODO Auto-generated method stub
		return null;
	}

	private Collection<GrantedAuthority> convertPermissions(List<String> permissions){
		
		List<GrantedAuthority> grantedAuthorities = Collections.emptyList();

		if(permissions!=null){
			grantedAuthorities = new ArrayList<GrantedAuthority>(permissions.size());
			
			for(final String permissionStr : permissions){
				grantedAuthorities.add(new GrantedAuthorityImpl(permissionStr));
			}
		}

		return grantedAuthorities;
	}

}
