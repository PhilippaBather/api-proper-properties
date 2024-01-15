package com.philippabather.properpropertiesapi.security;

import com.philippabather.properpropertiesapi.exception.ProprietorNotFoundException;
import com.philippabather.properpropertiesapi.model.Proprietor;
import com.philippabather.properpropertiesapi.model.Role;
import com.philippabather.properpropertiesapi.service.ProprietorService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProprietorUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(ProprietorUserDetailsService.class);
    private ProprietorService proprietorService;

    public ProprietorUserDetailsService(ProprietorService proprietorService) {
        this.proprietorService = proprietorService;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws ProprietorNotFoundException {
        logger.info("start: ProprietorUserDetailsService_loadUserByUsername");
        Proprietor user = proprietorService.findByUsername(username);
        if (user == null)
            throw new ProprietorNotFoundException(username);

        List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
        logger.info("end: ProprietorUserDetailsService_loadUserByUsername");
        return buildUserForAuthentication(user, authorities);
    }

    private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
        logger.info("start: ProprietorUserDetailsService_getUserAuthority");
        Set<GrantedAuthority> roles = new HashSet<>();
        userRoles.forEach(role -> roles.add(new SimpleGrantedAuthority(role.getName())));
        logger.info("end: ProprietorUserDetailsService_getUserAuthority");
        return new ArrayList<>(roles);
    }

    private UserDetails buildUserForAuthentication(Proprietor user, List<GrantedAuthority> authorities) {
        logger.info("ProprietorUserDetailsService_ buildUserForAuthentication");
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                user.isActive(), true, true, true, authorities);
    }
}
