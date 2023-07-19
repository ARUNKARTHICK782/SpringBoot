package com.example.SpringBoot.Service;

import com.example.SpringBoot.Model.UserModel;
import com.example.SpringBoot.Repository.UserRepository;
import com.example.SpringBoot.config.ApplicationUserPermissions;
import com.example.SpringBoot.config.ApplicationUserRole;
import com.example.SpringBoot.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static com.example.SpringBoot.config.ApplicationUserRole.*;

@Component
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        try{
            User user = userRepository.findByUsername(username);

            System.out.println("User found");
            System.out.println(user);

            Set<SimpleGrantedAuthority> authorities = new HashSet<>();

            if(user.getRole().equals(ADMIN.name())){
               authorities.addAll(ADMIN.getGrantedAuthorities());
            }
            else{
                authorities.addAll(USER.getGrantedAuthorities());
            }


            System.out.println("Authorities has been fetched and assigned!!!!");


            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    true,
                    true,
                    true,
                    true,
                    authorities

            );
        }catch (Exception e){
            System.out.println("User not found");
            throw new UsernameNotFoundException("User not found");
        }
    }


    public void createUser(User user){
        System.out.println("In create user");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(USER.name());

        try{
            userRepository.save(user);
        }catch (Exception e){
            System.out.println(e.getMessage());

        }

    }
}
