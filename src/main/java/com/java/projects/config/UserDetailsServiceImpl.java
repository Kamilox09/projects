package com.java.projects.config;

import com.java.projects.model.User;
import com.java.projects.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    public UserDetailsServiceImpl(UserService userService){
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user;
        try{
            user = this.userService.findByUsername(s);
            return new UserDetailsImpl(user);
        } catch(RuntimeException e){
            throw new UsernameNotFoundException("User not found");
        }
    }
}
