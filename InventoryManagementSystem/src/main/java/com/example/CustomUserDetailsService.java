package com.example;

import com.example.UserCredentials.UserCredentials;
import com.example.UserCredentials.UserCredentialsRepository;
import com.example.UserCredentials.UserCredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * Created by lav on 1/8/16.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserCredentialsRepository userCredentialsRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserCredentials user=userCredentialsRepository.findByUserName(username);

       System.out.println("find by user name: "+user.getUserName());
        if(null==user)
            throw new UsernameNotFoundException("no user with: "+username);
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
               user.getUserName(),user.getPassword(),
                true,
                true,
                true,
                true,


                loadUserAuthorities(user.getRole())

        );

        return userDetails;

    }
    public Collection<? extends GrantedAuthority> loadUserAuthorities(String role) {
        List<SimpleGrantedAuthority> auths = new java.util.ArrayList<SimpleGrantedAuthority>();
        System.out.print(role);
        auths.add(new SimpleGrantedAuthority(role));
        return auths;
    }

}
