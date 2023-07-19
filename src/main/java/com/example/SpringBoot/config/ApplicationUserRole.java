package com.example.SpringBoot.config;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.example.SpringBoot.config.ApplicationUserPermissions.*;

public enum ApplicationUserRole {
    ADMIN(Sets.newHashSet(READ,WRITE)),
    USER(Sets.newHashSet(READ));

    private final Set<ApplicationUserPermissions> applicationUserPermissionsSet;

    ApplicationUserRole(Set<ApplicationUserPermissions> applicationUserPermissionsSet) {
        this.applicationUserPermissionsSet = applicationUserPermissionsSet;
    }

    public Set<ApplicationUserPermissions> getApplicationUserPermissionsSet() {
        return applicationUserPermissionsSet;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
        Set<SimpleGrantedAuthority> permissions =  getApplicationUserPermissionsSet()
                .stream()
                .map(permission->new SimpleGrantedAuthority(permission.getPermission())).collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
        return permissions;
    }
}