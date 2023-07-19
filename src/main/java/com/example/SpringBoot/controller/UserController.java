package com.example.SpringBoot.controller;


import com.example.SpringBoot.Service.UserService;
import com.example.SpringBoot.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public void register(@RequestBody User user){
        System.out.println(user);
        userService.createUser(user);
//        userService.loadUserByUsername(username);
    }


    @PostMapping("/login")
    public void login(@RequestBody User user){
        userService.loadUserByUsername(user.getUsername());
    }

}
