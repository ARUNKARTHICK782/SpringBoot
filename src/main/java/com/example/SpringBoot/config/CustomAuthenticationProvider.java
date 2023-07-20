package com.example.SpringBoot.config;

import com.example.SpringBoot.Service.UserService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


public class CustomAuthenticationProvider implements AuthenticationProvider {


    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

//
//    CustomAuthenticationProvider(UserDetailsService userDetailsService,PasswordEncoder passwordEncoder){
//        System.out.println("In custom authentication provider");
//        this.userDetailsService = userDetailsService;
//        this.passwordEncoder = passwordEncoder;
//    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("In custom authentication provider authenticate method ✅");

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails u = userDetailsService.loadUserByUsername(username);

        if(u == null){
            throw new UsernameNotFoundException("User not found");
        }

        System.out.println(u.getAuthorities());

        if(passwordEncoder.matches(password,u.getPassword())){
            System.out.println("Password matched");
            return new UsernamePasswordAuthenticationToken(
                    username,
                    password,
                    u.getAuthorities());
        }
        else{
            throw new BadCredentialsException("Error");
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
