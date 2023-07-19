package com.example.SpringBoot.config;


import com.example.SpringBoot.Service.UserService;
import com.example.SpringBoot.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


import static com.example.SpringBoot.config.ApplicationUserPermissions.*;
import static com.example.SpringBoot.config.ApplicationUserRole.*;



/*

This configuration will put authorization for all the endpoints. We can configure the endpoints using
    WebSecurityConfigurerAdapter class


@Configuration
public class ProjectConfig {
    @Bean
    public UserDetailsService userDetailsService(){
        InMemoryUserDetailsManager userDetails  = new InMemoryUserDetailsManager();
        UserDetails newUser =  User.withUsername("arun").password("arun").authorities("read").build();
        userDetails.createUser(newUser);
        return userDetails;
    }

    @Bean
    public PasswordEncoder encodePassword(){
        return NoOpPasswordEncoder.getInstance();
    }
}
*/

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(){
        return  new UserService();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public CustomAuthenticationProvider customAuthenticationProvider(){
        return new CustomAuthenticationProvider(userDetailsService(),passwordEncoder());
    }

//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }

//
//
//    @Bean
//    public DaoAuthenticationProvider myDaoAuthProvider(){
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
//        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
//        return daoAuthenticationProvider;
//    }


// This is the implementation of InMemoryUserDetailsManager


//    @Bean
//    public UserDetailsService userDetailsService(){
//        InMemoryUserDetailsManager userDetailsManager  = new InMemoryUserDetailsManager();
//        String encodedPassword1 = passwordEncoder.encode("arun");
//
//        UserDetails user1 =  User
//                .withUsername("arun")
//                .password(encodedPassword1)
//                .roles(String.valueOf(ApplicationRoles.USER))
//                .build();
//
//        String encodedPassword2 = passwordEncoder.encode("karthick");
//        UserDetails user2 =  User
//                .withUsername("karthick")
//                .password(encodedPassword2)
//                .roles(String.valueOf(ADMIN))
//                .build();
//
//        userDetailsManager.createUser(user1);
//        userDetailsManager.createUser(user2);
//
//        return userDetailsManager;
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("In securityFilterChain");
        http
                .csrf((csrf)->
                        csrf.disable()
                )
//                .sessionManagement((session)->{
//                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//                })
                .authenticationProvider(customAuthenticationProvider())
//                .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(http.getSharedObject(AuthenticationConfiguration.class))))
                .authorizeHttpRequests((req)->{
                    req.anyRequest().permitAll();
                });
//                .authorizeHttpRequests((requests) ->
//                        requests
//                                .requestMatchers(HttpMethod.POST,"/api/v1/notes").hasAuthority(WRITE.getPermission())
//                                .requestMatchers(HttpMethod.DELETE,"/api/v1/notes").hasAuthority(WRITE.getPermission())
//                                .requestMatchers(HttpMethod.PUT,"/api/v1/notes").hasAuthority(WRITE.getPermission())
//                                .requestMatchers("/api/v1/admin").hasRole(ADMIN.name())
//                                .anyRequest().permitAll()
//                );

//                ).httpBasic(Customizer.withDefaults());

        return http.build();
    }



}