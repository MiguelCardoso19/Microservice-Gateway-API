package com.example.PortalMicroservice.util;

import com.example.PortalMicroservice.dto.UserCredentialsDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ClientUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void extractUserDetails(String body, ServletRequestAttributes attributes) throws JsonProcessingException {
        UserCredentialsDTO userCredentialsDTO = objectMapper.readValue(body, UserCredentialsDTO.class);
        if (userCredentialsDTO != null) {
            String username = userCredentialsDTO.getUsername();
            UUID id = userCredentialsDTO.getId();

            attributes.getRequest().setAttribute("username", username);
            if (id != null) {
                attributes.getRequest().setAttribute("id", id);
            }
        }
    }

    @SneakyThrows
    public static String extractErrorMessage(Response response) {
        JsonNode bodyJson = objectMapper.readTree(response.body().asInputStream());
        String errorMessage = bodyJson.get("message").asText();

        return errorMessage.substring(errorMessage.indexOf("[") + 1, errorMessage.indexOf("]"));
    }

    public static <T> Page<T> parsePage(ObjectMapper objectMapper, String jsonContent, Pageable pageable, TypeReference<List<T>> typeReference) throws JsonProcessingException {
        Map<String, Object> pageMap = objectMapper.readValue(jsonContent, new TypeReference<>() {
        });

        long totalElements = ((Number) pageMap.get("totalElements")).longValue();
        int number = ((Number) pageMap.get("number")).intValue();
        int size = ((Number) pageMap.get("size")).intValue();
        List<T> content = objectMapper.convertValue(pageMap.get("content"), typeReference);

        return new PageImpl<>(content, PageRequest.of(number, size, pageable.getSort()), totalElements);
    }
}