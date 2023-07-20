package com.example.SpringBoot.controller;


import com.example.SpringBoot.Model.ResponseMessage;
import com.example.SpringBoot.Service.UserService;
import com.example.SpringBoot.entity.User;
import com.example.SpringBoot.jwt.JWTGenerator;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTGenerator jwtGenerator;



    @PostMapping("/register")
    public ResponseEntity<ResponseMessage> register(@RequestBody User user){
        UserDetails newUser = userService.createUser(user);
        String token =  jwtGenerator.generateToken(newUser);
        ResponseMessage responseMessage = new ResponseMessage(true,"User created",200,null);
        return ResponseEntity.status(200).header("auth",token).body(responseMessage);

//        userService.loadUserByUsername(username);
    }


    @PostMapping("/login")
    public ResponseEntity<ResponseMessage> login(@RequestBody User user){
        UserDetails loggedInUser = userService.loadUserByUsername(user.getUsername());
        String token =  jwtGenerator.generateToken(loggedInUser);
        ResponseMessage responseMessage = new ResponseMessage(true,"Login successful",200,null);
        return ResponseEntity.status(200).header("auth",token).body(responseMessage);
    }

}
