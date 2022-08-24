package com.socialmediaclone.socialmediaclone.security;

import com.socialmediaclone.socialmediaclone.entities.User;
import com.socialmediaclone.socialmediaclone.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServicePrincipal implements UserDetailsService {
    private final UserService userService;

    @Autowired
    public UserDetailsServicePrincipal(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =  userService.getUser(username);
        return new UserDetailsPrincipal(user);
    }
}
