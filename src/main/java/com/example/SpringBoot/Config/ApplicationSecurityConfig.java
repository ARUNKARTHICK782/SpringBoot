package com.example.SpringBoot.Config;


import com.example.SpringBoot.Model.UsernameAndPasswordAuthenticationRequest;
import com.example.SpringBoot.Service.UserService;
import com.example.SpringBoot.Filter.JwtUsernameAndPasswordAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import static com.example.SpringBoot.Config.ApplicationUserPermissions.*;
import static com.example.SpringBoot.Config.ApplicationUserRole.*;



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


//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }

    @Bean
    public JwtUsernameAndPasswordAuthenticationFilter jwtUsernameAndPasswordAuthenticationFilter(){
        return new JwtUsernameAndPasswordAuthenticationFilter();
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("In securityFilterChain");
        http
                .csrf((csrf)->
                        csrf.disable()
                )
                .sessionManagement((session)->{
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .authorizeHttpRequests((requests) ->
                        requests
                                .requestMatchers(HttpMethod.POST,"/api/v1/notes").hasAuthority(WRITE.getPermission())
                                .requestMatchers(HttpMethod.DELETE,"/api/v1/notes").hasAuthority(WRITE.getPermission())
                                .requestMatchers(HttpMethod.PUT,"/api/v1/notes").hasAuthority(WRITE.getPermission())
                                .requestMatchers(HttpMethod.GET,"/api/v1/notes").hasAuthority(READ.getPermission())
                                .requestMatchers(HttpMethod.GET,"/api/v1/users/name").hasRole(USER.name())
                                .requestMatchers("/api/v1/admin").hasRole(ADMIN.name())
                                .anyRequest().permitAll()
                )
                .addFilterBefore(jwtUsernameAndPasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }



}