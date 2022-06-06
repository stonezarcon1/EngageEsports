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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication a = context.getAuthentication();
        return new ResponseEntity<>(a.getDetails(), HttpStatus.ACCEPTED);
    }

    @PostMapping("/register/teacher")
    public ResponseEntity<?> register(@Valid @RequestBody User user) {

        try {
            if (userRepository.findByUsername(user.getUsername()) == null) {
                user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
                user.setTeacher(true);
                user.setEnabled(true);
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                userRepository.save(user);

                return new ResponseEntity<>("Registered student successfully!", HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>("A student already exists with that name.", HttpStatus.OK);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("You must complete all fields before registering a user.",
                    HttpStatus.NOT_ACCEPTABLE);
        }

    }

    @PostMapping("/register/student")
    public ResponseEntity<?> registerStudent(@Valid @RequestBody User user) {

        try {
            if (userRepository.findByUsername(user.getUsername()) == null) {
                user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
                user.setTeacher(false);
                user.setEnabled(true);
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                userRepository.save(user);

                return new ResponseEntity<>("Registered student successfully!", HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>("A student already exists with that name.", HttpStatus.OK);
            }

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("You must complete all fields before registering a user.",
                    HttpStatus.NOT_ACCEPTABLE);
        }

    }
}

