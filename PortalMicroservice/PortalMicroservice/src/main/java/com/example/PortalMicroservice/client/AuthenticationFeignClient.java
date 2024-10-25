package com.example.PortalMicroservice.client;

import com.example.PortalMicroservice.config.CustomErrorDecoder;
import com.example.PortalMicroservice.dto.AuthenticationResponseDTO;
import com.example.PortalMicroservice.dto.UserCredentialsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "authenticationFeignClient", url = "${authentication-microservice-authorization.url}", configuration = CustomErrorDecoder.class)
public interface AuthenticationFeignClient {

    @PostMapping("/sign-in")
    ResponseEntity<AuthenticationResponseDTO> signIn(@RequestBody UserCredentialsDTO userCredentialsDTO);

    @PostMapping("/refresh-token")
    ResponseEntity<AuthenticationResponseDTO> refreshToken(@RequestHeader("Authorization") String authHeader);
}