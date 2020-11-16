package com.recorda.admin.users.client;

import com.recorda.admin.users.exception.ApiClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * Provides a client for a third party API
 *
 * For instance :
 * @see <a href="https://ipapi.co/#api">IP Location API</a>
 */
@Component
public class ApiClient {

    private static final Logger logger = LoggerFactory.getLogger(ApiClient.class);

    /* Underlying Spring Rest Client */
    private RestTemplate restTemplate;

    public ApiClient() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * Sends a GET response
     *
     * @param uri uri to request
     * @param responseClass type of response
     * @param <T>
     * @return
     */
    public <T> T get(URI uri, HttpHeaders headers, Class<T> responseClass) throws ApiClientException {

        ResponseEntity<T> response = null;

        try {
            logger.debug(String.format("Sending GET Request to endpoint: %s", uri));

            response = restTemplate.exchange(uri,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    responseClass);

        } catch (RestClientException rce) {
            throw new ApiClientException(rce.getMessage());
        }

        return response != null ? response.getBody() : null;
    }
}
