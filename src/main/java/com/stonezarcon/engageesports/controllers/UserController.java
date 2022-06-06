package com.stonezarcon.engageesports.controllers;

import com.stonezarcon.engageesports.models.PlayerProfile;
import com.stonezarcon.engageesports.models.User;
import com.stonezarcon.engageesports.repos.PlayerProfileRepository;
import com.stonezarcon.engageesports.repos.RoleRepository;
import com.stonezarcon.engageesports.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Arrays;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PlayerProfileRepository profileRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public ResponseEntity<?> home() {
        return new ResponseEntity<>("Welcome to my site.", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return new ResponseEntity<>("Welcome " + ((UserDetails)principal).getUsername(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Welcome " + principal.toString(), HttpStatus.OK);
        }
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
                user.setProfile(new PlayerProfile());
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

    @GetMapping("/player/profile")
    public ResponseEntity<?> getPlayerProfile() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        User user = userRepository.findByUsername(username);
        return new ResponseEntity<>(profileRepository.findById(user.getProfile().getId()), HttpStatus.OK) ;
    }

    @PostMapping("/player/profile")
    public ResponseEntity<?> addPlayerProfile(@RequestBody String stuff) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        if (userRepository.findByUsername(username) == null) {
            return new ResponseEntity<>("No user with that ID was found.", HttpStatus.OK);
        } else {

            User user = userRepository.findByUsername(username);
            user.getProfile().setTestField(stuff);
            userRepository.save(user);

            return new ResponseEntity<>("Player Profile successfully set.", HttpStatus.OK);
        }

    }
}

