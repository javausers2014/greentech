package com.gtech.iarc.base.security;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

public class DefaultAuthenticationProvider implements AuthenticationProvider, InitializingBean, Ordered {
    private AuthenticationUserDetailsService userDetailsService = null;

	public int getOrder() {
		return -1;
	}

	public void afterPropertiesSet() throws Exception {
        Assert.notNull(userDetailsService, "An AuthenticationUserDetailsService must be set");
	}

	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//		if(StringUtils.isBlank((String) authentication.getPrincipal())){
//			throw new PreAuthenticatedCredentialsNotFoundException("Pre-authentication credentials missing!");
//		}
//		
		final UserDetails user = userDetailsService.loadUserDetails(authentication);

		return new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
	}

	public boolean supports(Class<? extends Object> authentication) {
		return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
	}
	
	public void setUserDetailsService(AuthenticationUserDetailsService u) {
		this.userDetailsService = u;
	}

}
