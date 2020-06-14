package com.fss.AuthServer1.dao;

import com.fss.AuthServer1.model.UserEntity;

public interface OAuthDaoService {

    public UserEntity getUserDetails(String emailid);
}
