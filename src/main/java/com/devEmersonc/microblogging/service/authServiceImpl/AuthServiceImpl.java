package com.devEmersonc.microblogging.service.authServiceImpl;

import com.devEmersonc.microblogging.dto.AuthRequest;
import com.devEmersonc.microblogging.dto.AuthResponse;
import com.devEmersonc.microblogging.service.AuthService;
import com.devEmersonc.microblogging.service.CustomUserDetailsService;
import com.devEmersonc.microblogging.service.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(CustomUserDetailsService customUserDetailsService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthResponse login(AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(authRequest.getUsername());
        String token = jwtService.generateToken(userDetails);
        return new AuthResponse(token);
    }
}