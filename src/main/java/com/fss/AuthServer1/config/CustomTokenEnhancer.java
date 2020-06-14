package com.fss.AuthServer1.config;

import com.fss.AuthServer1.model.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.LinkedHashMap;
import java.util.Map;


public class CustomTokenEnhancer extends JwtAccessTokenConverter {

    @Autowired
    LdapUserDetailsImpl ldapUserDetails;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

        ldapUserDetails = (LdapUserDetailsImpl) authentication.getPrincipal();
        //CustomUser user = (CustomUser) authentication.getPrincipal();
        Map<String,Object> info = new LinkedHashMap<>(accessToken.getAdditionalInformation());

        info.put("id",1);
        info.put("name",ldapUserDetails.getUsername());
        info.put("userName",ldapUserDetails.getUsername());
        DefaultOAuth2AccessToken customAccessToken = new DefaultOAuth2AccessToken(accessToken);
        customAccessToken.setAdditionalInformation(info);
        return super.enhance(customAccessToken, authentication);
    }
}
