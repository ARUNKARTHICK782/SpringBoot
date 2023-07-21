package com.example.SpringBoot.Config;

import org.checkerframework.checker.units.qual.C;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;


@Component
public class AppRolesAndPermissions {

    HashMap<String, List<SimpleGrantedAuthority>> rolesAndPermissions;

    AppRolesAndPermissions(){
        rolesAndPermissions = new HashMap<>();
        rolesAndPermissions.put("ADMIN",List.of(new SimpleGrantedAuthority("READ"),new SimpleGrantedAuthority("WRITE")));
        rolesAndPermissions.put("USER",List.of(new SimpleGrantedAuthority("READ")));
    }

    public List<SimpleGrantedAuthority> getPermissionsAndRoles(String role){
        List<SimpleGrantedAuthority> permissions = rolesAndPermissions.get(role);
        permissions.add(new SimpleGrantedAuthority("ROLE_"+role));
        return permissions;
    }


}
