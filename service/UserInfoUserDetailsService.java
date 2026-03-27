package com.taskmanager.taskmanager.service;

import com.taskmanager.taskmanager.DAO.userDao;
import com.taskmanager.taskmanager.config.UserInfoUserDetails;
import com.taskmanager.taskmanager.entity.Users;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;


    @Override
    public UserDetails loadUserByUsername(String username)  {
        Users userInfo = userService.findUserByUsername(username);
         if(userInfo == null) {
             throw new UsernameNotFoundException(username);
         }
         return new UserInfoUserDetails(userInfo);

    }
}
