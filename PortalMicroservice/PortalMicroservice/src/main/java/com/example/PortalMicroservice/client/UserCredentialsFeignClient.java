package com.example.PortalMicroservice.client;

import com.example.PortalMicroservice.config.CustomErrorDecoder;
import com.example.PortalMicroservice.dto.AuthenticationResponseDTO;
import com.example.PortalMicroservice.dto.UserCredentialsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "userCredentialsFeignClient", url = "${authentication-microservice-user-credentials.url}", configuration = CustomErrorDecoder.class)
public interface UserCredentialsFeignClient {

    @PostMapping("/register")
    ResponseEntity<AuthenticationResponseDTO> register(@RequestBody UserCredentialsDTO userCredentialsDTO);

    @PutMapping("/update")
    ResponseEntity<UserCredentialsDTO> update(@RequestBody UserCredentialsDTO userCredentialsDTO);

    @DeleteMapping("/delete")
    ResponseEntity<Void> delete(@RequestBody UserCredentialsDTO userCredentialsDTO);
}