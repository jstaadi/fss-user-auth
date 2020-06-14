package com.fss.AuthServer1.dao;


import com.fss.AuthServer1.model.UserEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class
OAuthDaoServiceImpl implements OAuthDaoService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public UserEntity getUserDetails(String emailid) {
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        List<UserEntity> list = jdbcTemplate.query("select * from myusers where emailid=?", new String[] {emailid},
                (ResultSet rs, int rowNum) -> {
                        UserEntity user = new UserEntity();
                        user.setEmailid(emailid);
                        user.setId(rs.getString("id"));
                        user.setName(rs.getString("username"));
                        user.setPassword(rs.getString("pass"));
                        return user;
                });
        if (!list.isEmpty()) {
            UserEntity userEntity = list.get(0);

            String query  = "select distinct p.permission_name from permission p\n" +
                    " inner join assign_permission_to_role pr on p.id = pr.permission_id\n" +
                    " inner join role r on r.id = pr.role_id\n" +
                    " inner join assign_user_to_role ur on ur.role_id = r.id\n" +
                    " inner join myusers u on u.id = ur.user_id\n" +
                    " where u.emailid = ?";

            List<String> permissionList = jdbcTemplate.query(query,new String[] {userEntity.getEmailid()},
                    (ResultSet rs,int rowNum) -> {
                        return "ROLE_"+rs.getString("permission_name");
                    });

            if (permissionList != null && !permissionList.isEmpty()){
                for ( String permission : permissionList ) {
                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission);
                    grantedAuthorities.add(grantedAuthority);
                }
                userEntity.setGrantedAuthorities(grantedAuthorities);
            }

            return userEntity;
        }
        return null;
    }
}
