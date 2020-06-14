package com.fss.AuthServer1.service;

import com.fss.AuthServer1.dao.OAuthDaoService;
import com.fss.AuthServer1.model.CustomUser;
import com.fss.AuthServer1.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    OAuthDaoService oAuthDaoService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = null;
        userEntity = oAuthDaoService.getUserDetails(username);
        if (userEntity != null && userEntity.getId() != null && !"".equalsIgnoreCase(userEntity.getId())) {
            CustomUser user = new CustomUser(userEntity);
            return user;
        }
        else {
            throw new UsernameNotFoundException("User "+username+" was not found in database");
        }

    }
}
