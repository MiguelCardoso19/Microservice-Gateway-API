package com.example.PortalMicroservice.config;

import com.example.PortalMicroservice.exception.GenericException;
import com.example.PortalMicroservice.exception.authentication.InvalidPasswordException;
import com.example.PortalMicroservice.exception.authentication.InvalidRefreshTokenException;
import com.example.PortalMicroservice.exception.authentication.UserCredentialsValidationException;
import com.example.PortalMicroservice.exception.UserNotFoundException;
import com.example.PortalMicroservice.util.ClientUtils;
import feign.Response;
import feign.codec.ErrorDecoder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.UUID;

@Component
public class CustomErrorDecoder implements ErrorDecoder {

    @SneakyThrows
    @Override
    public Exception decode(String methodKey, Response response) {
        HttpServletRequest request = getCurrentHttpRequest();

        switch (response.status()) {
            case 404 -> handleNotFound(methodKey, request);
            case 401 -> handleUnauthorized(methodKey);
            case 409 -> handleConflict(methodKey, response);
        }

        return new GenericException();
    }

    private HttpServletRequest getCurrentHttpRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes != null ? attributes.getRequest() : null;
    }

    private void handleNotFound(String methodKey, HttpServletRequest request) throws UserNotFoundException {
        if (methodKey.contains("refreshToken") || methodKey.contains("delete") || methodKey.contains("update")) {
            throw new UserNotFoundException((UUID) request.getAttribute("id"));
        } else if (methodKey.contains("signIn")) {
            throw new UserNotFoundException((String) request.getAttribute("username"));
        }
    }

    private void handleUnauthorized(String methodKey) throws InvalidPasswordException, InvalidRefreshTokenException {
        if (methodKey.contains("signIn") || methodKey.contains("delete") || methodKey.contains("update")) {
            throw new InvalidPasswordException();
        } else if (methodKey.contains("refreshToken")) {
            throw new InvalidRefreshTokenException();
        }
    }

    private void handleConflict(String methodKey, Response response) throws UserCredentialsValidationException {
        if (methodKey.contains("register") || methodKey.contains("update")) {
            throw new UserCredentialsValidationException((ClientUtils.extractErrorMessage(response)));
        }
    }
}