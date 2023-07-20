package com.example.SpringBoot.jwt;

import com.example.SpringBoot.Model.UsernameAndPasswordAuthenticationRequest;
import com.example.SpringBoot.Service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.SimpleTimeZone;

public class JwtUsernameAndPasswordAuthenticationFilter  extends OncePerRequestFilter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JWTGenerator jwtGenerator;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestUri = request.getRequestURI();
        if(requestUri.contains("login") || requestUri.contains("register")){
            filterChain.doFilter(request,response);
            return;
        }


        System.out.println("Filter working!!!");
        String token = request.getHeader("auth");

        if(token == null){
           response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Token required");
           return;
        }

        jwtGenerator.validateToken(token);

        Set<SimpleGrantedAuthority> role = jwtGenerator.getRoleFromToken(token);
        String username = jwtGenerator.getUsernameFromToken(token);


        System.out.println("ROLES : "+role);
        System.out.println("USERNAME : "+username);

        Authentication authentication  = new UsernamePasswordAuthenticationToken(
                username,null,role
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);


        System.out.println("Chaining to next filter");
        filterChain.doFilter(request,response);

    }
}
