package com.stonezarcon.engageesports.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthService implements AuthenticationProvider {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("Start");
        String username = authentication.getName();
        System.out.println(authentication.getName());
        String password = authentication.getCredentials().toString();
        System.out.println(authentication.getCredentials().toString());

        UserDetails u = userDetailsService.loadUserByUsername(username);
        System.out.println("Authenticating");

        System.out.println(password);
        System.out.println(u.getPassword());
        if (passwordEncoder.matches(password, u.getPassword())) {
            System.out.println("Match found");
            return new UsernamePasswordAuthenticationToken(username,
                    password,
                    u.getAuthorities());
        } else {

            throw new BadCredentialsException("Invalid login details");
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        System.out.println("Verifying");
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
