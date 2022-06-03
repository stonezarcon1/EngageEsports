package com.stonezarcon.engageesports.controllers;

import com.stonezarcon.engageesports.models.User;
import com.stonezarcon.engageesports.repos.RoleRepository;
import com.stonezarcon.engageesports.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication a = context.getAuthentication();
        return new ResponseEntity<>(SecurityContextHolder.getContext(), HttpStatus.ACCEPTED);
    }

    @PostMapping("/register/teacher")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()) == null) {
            user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
            user.setTeacher(true);
            userRepository.save(user);

            return new ResponseEntity<>("Registered student successfully!", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("A student already exists with that name.", HttpStatus.OK);
        }
    }

    @PostMapping("/register/student")
    public ResponseEntity<?> registerStudent(@RequestBody User user) {

        if (userRepository.findByUsername(user.getUsername()) == null) {
            user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
            userRepository.save(user);

            return new ResponseEntity<>("Registered student successfully!", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("A student already exists with that name.", HttpStatus.OK);
        }
    }
}

