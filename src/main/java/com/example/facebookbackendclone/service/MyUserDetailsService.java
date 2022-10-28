package com.example.facebookbackendclone.service;

import com.example.facebookbackendclone.model.Role;
import com.example.facebookbackendclone.model.RoleType;
import com.example.facebookbackendclone.model.User;
import com.example.facebookbackendclone.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Transactional
@Service
public class MyUserDetailsService implements UserDetailsService{

    @Autowired
    private UserService userService;

    @Autowired
    RoleRepository roleRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByEmail(username);

        return MyUserDetails.build(user);
    }
}
