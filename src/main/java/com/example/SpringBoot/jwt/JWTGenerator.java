package com.example.SpringBoot.jwt;

import com.example.SpringBoot.config.ApplicationUserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.checkerframework.checker.units.qual.A;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JWTGenerator {
    private static final Key key = Keys.hmacShaKeyFor("myjwttokensecretkeymyjwttokensecretkeymyjwttokensecretkeymyjwttokensecretkey".getBytes());

    public String generateToken(UserDetails userDetails) {

        List<String> roles = new ArrayList<>();

        for(GrantedAuthority simpleGrantedAuthority : userDetails.getAuthorities()){
            roles.add(simpleGrantedAuthority.getAuthority());
        }

        String token =
                Jwts
                    .builder()
                        .claim("username",userDetails.getUsername())
                        .claim("roles",roles)
                    .setIssuedAt(Date.valueOf(LocalDate.now()))
                    .setExpiration(Date.valueOf(LocalDate.now().plusDays(1)))
                    .signWith(key)
                    .compact();
        System.out.println("New token :");
        System.out.println(token);
        return token;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token);
            System.out.println("Token validated successfully!!!!");
            return true;

        } catch (Exception ex) {
            throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect",ex.fillInStackTrace());
        }
    }


    public Set<SimpleGrantedAuthority> getRoleFromToken(String token){
        Claims claims = (Jwts.parser().setSigningKey(key)
                .parseClaimsJws(token)).getBody();

        System.out.println("In get role");

        List<String> roles = claims.get("roles", List.class);

        System.out.println(roles);

        Set<SimpleGrantedAuthority> simpleGrantedAuthorities = new HashSet<>();

        simpleGrantedAuthorities  = roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());

        return simpleGrantedAuthorities;
    }




    public String getUsernameFromToken(String token){
        Claims claims = (Jwts.parser().setSigningKey(key)
                .parseClaimsJws(token)).getBody();
        return claims.get("username").toString();
    }
}
