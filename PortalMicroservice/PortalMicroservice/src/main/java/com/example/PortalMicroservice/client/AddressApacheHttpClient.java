package com.example.PortalMicroservice.client;

import com.example.PortalMicroservice.dto.AddressDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AddressApacheHttpClient {
    private final ObjectMapper objectMapper;

    @Value("${microservice-addresses.url}")
    private String ADDRESS_URL;

    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    public HttpResponse createAddress(AddressDTO addressDTO) throws IOException {
        HttpPost postRequest = new HttpPost(ADDRESS_URL + "/create");
        postRequest.setHeader("Content-Type", "application/json");
        postRequest.setEntity(new StringEntity(objectMapper.writeValueAsString(addressDTO)));
        return httpClient.execute(postRequest);
    }

    public HttpResponse updateAddress(AddressDTO addressDTO) throws IOException {
        HttpPut putRequest = new HttpPut(ADDRESS_URL + "/update");
        putRequest.setHeader("Content-Type", "application/json");
        putRequest.setEntity(new StringEntity(objectMapper.writeValueAsString(addressDTO)));
        return httpClient.execute(putRequest);
    }

    public HttpResponse deleteAddress(UUID addressId) throws IOException {
        return httpClient.execute(new HttpDelete(ADDRESS_URL + "/" + addressId));
    }

    public HttpResponse getAddressById(UUID addressId) throws IOException {
        return httpClient.execute(new HttpGet(ADDRESS_URL + "/" + addressId));
    }

    public HttpResponse getAddressesByUserId(UUID userId) throws IOException {
        return httpClient.execute(new HttpGet(ADDRESS_URL + "/get-by-user-id/" + userId));
    }

    public HttpResponse getAllAddresses(Pageable pageable) throws IOException {
        String requestUrl = ADDRESS_URL + "/all?page=" + pageable.getPageNumber()
                + "&size=" + pageable.getPageSize()
                + "&sort=" + pageable.getSort().toString()
                .replace(":", ",")
                .replace(" ", "");

        return httpClient.execute(new HttpGet(requestUrl));
    }
}