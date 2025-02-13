package com.devEmersonc.microblogging.security;

import com.devEmersonc.microblogging.exception.JwtAuthenticationException;
import com.devEmersonc.microblogging.service.CustomUserDetailsService;
import com.devEmersonc.microblogging.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtService jwtService;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    public JwtFilter(CustomUserDetailsService customUserDetailsService, JwtService jwtService, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtService = jwtService;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authotizationHeader = request.getHeader("Authorization");
        String username = null;
        String token = null;

        if(authotizationHeader != null && authotizationHeader.startsWith("Bearer ")) {
            token = authotizationHeader.substring(7);
            try {
                username = jwtService.extractUsername(token);
            } catch (Exception e) {
                jwtAuthenticationEntryPoint.commence(request, response, new JwtAuthenticationException("Token inválido."));
                return;
            }
        }

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            if(!jwtService.validateToken(token, userDetails.getUsername())) {
                jwtAuthenticationEntryPoint.commence(request, response, new JwtAuthenticationException("Token inválido o ha expirado."));
                return;
            }
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }
}