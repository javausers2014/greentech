package com.gtech.iarc.base.springsecurity;

import java.util.Collection;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class SimplePermissionVoter implements AccessDecisionVoter {


    public boolean supports(ConfigAttribute attribute) {
        if ((attribute.getAttribute() != null)) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * This implementation supports any type of class, because it does not query
     * the presented secure object.
     *
     * @param clazz the secure object
     *
     * @return always <code>true</code>
     */
    public boolean supports(Class<?> clazz) {
        return true;
    }

    public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
        int result = ACCESS_ABSTAIN;
        Collection<GrantedAuthority> authorities = extractAuthorities(authentication);

        for (ConfigAttribute attribute : attributes) {
            if (this.supports(attribute)) {
                result = ACCESS_DENIED;

                // Attempt to find a matching granted authority
                for (GrantedAuthority authority : authorities) {
                    if (attribute.getAttribute().equals(authority.getAuthority())) {
                        return ACCESS_GRANTED;
                    }
                }
            }
        }

        return result;
    }

    Collection<GrantedAuthority> extractAuthorities(Authentication authentication) {
        return authentication.getAuthorities();
    }
}
