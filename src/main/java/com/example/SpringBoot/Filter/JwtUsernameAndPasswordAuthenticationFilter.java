package com.example.SpringBoot.Filter;

import com.example.SpringBoot.Service.UserService;
import com.example.SpringBoot.Jwt.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.SignatureException;
import java.util.Set;

public class JwtUsernameAndPasswordAuthenticationFilter  extends OncePerRequestFilter {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTService jwtService;

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

        try{
            jwtService.validateToken(token);
        }
        catch (Exception e){
            response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE,e.getMessage());
        }


        Set<SimpleGrantedAuthority> authorities = jwtService.getRoleFromToken(token);
        String username = jwtService.getUsernameFromToken(token);


        System.out.println("Authorities and Roles : "+authorities);
        System.out.println("USERNAME : "+username);

        Authentication authentication  = new UsernamePasswordAuthenticationToken(
                username,null,authorities
        );


        SecurityContextHolder.getContext().setAuthentication(authentication);


        System.out.println("Chaining to next filter");
        filterChain.doFilter(request,response);

    }
}
