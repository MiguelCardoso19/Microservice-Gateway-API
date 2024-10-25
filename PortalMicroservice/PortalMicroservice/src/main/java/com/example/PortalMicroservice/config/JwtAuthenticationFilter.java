package com.example.PortalMicroservice.config;

import com.example.PortalMicroservice.exception.InvalidAuthorizationHeaderException;
import com.example.PortalMicroservice.exception.InvalidTokenException;
import com.example.PortalMicroservice.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtTokenService;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String requestURI = request.getRequestURI();

        if (    requestURI.startsWith("/api/v1/proxy/auth/sign-in") ||
                requestURI.startsWith("/api/v1/proxy/user-credentials/register") ||
                requestURI.startsWith("/v3/api-docs") ||
                requestURI.startsWith("/swagger-ui") ||
                requestURI.startsWith("/swagger-resources") ||
                requestURI.startsWith("/webjars/springfox-swagger-ui")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new InvalidAuthorizationHeaderException();
        }

        if (!jwtTokenService.isTokenValid(authHeader, request.getHeader("Username"))) {
            throw new InvalidTokenException();
        }

        filterChain.doFilter(request, response);
    }
}