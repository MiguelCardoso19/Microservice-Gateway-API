package com.example.PortalMicroservice.client;

import com.example.PortalMicroservice.dto.UserAccountDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@Component
public class UserAccountRestTemplateClient {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${microservice-users.url}")
    private String USER_URL;

    public ResponseEntity<UserAccountDTO> createUser(UserAccountDTO userAccountDTO) {
        return restTemplate.exchange(
                USER_URL + "/create",
                HttpMethod.POST,
                new HttpEntity<>(userAccountDTO),
                UserAccountDTO.class
        );
    }

    public ResponseEntity<UserAccountDTO> getUserById(UUID id) {
        return restTemplate.exchange(
                USER_URL + "/" + id,
                HttpMethod.GET,
                null,
                UserAccountDTO.class
        );
    }

    public ResponseEntity<UserAccountDTO> updateUser(UserAccountDTO userAccountDTO) {
        return restTemplate.exchange(
                USER_URL + "/update",
                HttpMethod.PUT,
                new HttpEntity<>(userAccountDTO),
                UserAccountDTO.class
        );
    }

    public ResponseEntity<Void> deleteUser(UUID id) {
        return restTemplate.exchange(
                USER_URL + "/" + id,
                HttpMethod.DELETE,
                null,
                Void.class
        );
    }

    public ResponseEntity<String> searchUsersByName(String name, Pageable pageable) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(USER_URL)
                .path("/search-by-name")
                .queryParam("name", name)
                .queryParam("size", pageable.getPageSize())
                .queryParam("page", pageable.getPageNumber());

        pageable.getSort().forEach(order -> {
            uriBuilder.queryParam("sort", order.getProperty() + "," + order.getDirection());
        });

        return restTemplate.exchange(
                uriBuilder.toUriString(),
                HttpMethod.GET,
                null,
                String.class
        );
    }
}