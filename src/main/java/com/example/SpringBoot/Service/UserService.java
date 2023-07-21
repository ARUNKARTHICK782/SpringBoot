package com.example.SpringBoot.Service;

import com.example.SpringBoot.Config.AppRolesAndPermissions;
import com.example.SpringBoot.Model.UserModel;
import com.example.SpringBoot.Repository.UserRepository;
import com.example.SpringBoot.Config.ApplicationUserRole;
import com.example.SpringBoot.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.SpringBoot.Config.ApplicationUserRole.*;

@Component
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AppRolesAndPermissions appRolesAndPermissions;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("In loaduserbyusername");

        try{
            User user = userRepository.findByUsername(username);

            System.out.println("User found");
            System.out.println(user);

//            Set<SimpleGrantedAuthority> authorities = appRolesAndPermissions.getPermissionsAndRoles(user.getRole()).stream().collect(Collectors.toSet());

            Set<SimpleGrantedAuthority> authorities = new HashSet<>();





            if(user.getRole().equals(ADMIN.name())){
//                 appRolesAndPermissions.getPermissionsAndRoles("ADMIN");
               authorities.addAll(ADMIN.getGrantedAuthorities());
            }
            else{
                authorities.addAll(USER.getGrantedAuthorities());
            }

            System.out.println("AUTHORITIES IN LOGIN");
            System.out.println(authorities);
            System.out.println("Authorities has been fetched and assigned!!!!");


            return new UserModel(
                    user.getUsername(),
                    user.getPassword(),
                    user.getRole(),
                    authorities

            );
        }catch (Exception e){
            System.out.println("User not found");
            throw new UsernameNotFoundException("User not found");
        }
    }


    public UserDetails createUser(User user){
        System.out.println("In create user");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(USER.name());
        Set<SimpleGrantedAuthority> authorities = ApplicationUserRole.valueOf(user.getRole()).getGrantedAuthorities();
        System.out.println("Authorities");
        System.out.println(authorities);
        try{
            userRepository.save(user);
            return new UserModel(
                    user.getUsername(),
                    user.getPassword(),
                    USER.name(),
                    authorities
            );
        }catch (Exception e){
            System.out.println(e.getMessage());
            throw new UsernameNotFoundException("Error creating user");
        }
    }
}
