package com.example.AuthenticationMicroservice.config;

import com.example.AuthenticationMicroservice.model.UserCredentials;
import com.example.AuthenticationMicroservice.service.JwtService;
import com.example.AuthenticationMicroservice.service.UserCredentialsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private JwtService jwtService;
    private UserCredentialsService userCredentialsService;

    public JwtAuthenticationFilter(JwtService jwtService, @Lazy UserCredentialsService userCredentialsService) {
        this.userCredentialsService = userCredentialsService;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String username = jwtService.extractUsername(authHeader.replace("Bearer ", ""));

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserCredentials user = userCredentialsService.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException(username));

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}