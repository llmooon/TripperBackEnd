package org.soma.tripper.service;

import org.soma.tripper.dao.AppRoleDAO;
import org.soma.tripper.dao.AppUserDAO;
import org.soma.tripper.domain.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private AppUserDAO appUserDAO;
    @Autowired
    private AppRoleDAO appRoleDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = this.appUserDAO.findUserAccount(username);
        if(appUser==null){
            System.out.println("user not found");
            throw new UsernameNotFoundException("User was not found in db");
        }
        System.out.println("find");

        List<String> roleNames = this.appRoleDAO.getRoleNames(appUser.getUserId());
        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
        if(roleNames!=null){
            for(String role : roleNames){
                GrantedAuthority authority = new SimpleGrantedAuthority(role);
                grantList.add(authority);
            }
        }
        UserDetails userDetails = (UserDetails) new User
                (appUser.getUserName(),appUser.getEncrytedPassword(),grantList);
        return userDetails;
    }
}
