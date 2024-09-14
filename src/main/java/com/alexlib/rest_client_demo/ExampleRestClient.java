package com.alexlib.rest_client_demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

@Component
public class ExampleRestClient {

    Logger log = LoggerFactory.getLogger(ExampleRestClient.class);

    private final RestClient restClient;

    @Value("${remoteItemService.path}")
    String path;

    @Autowired
    public ExampleRestClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public String getItemWithId(String id){
        try {
            return restClient
                    .get()
                    .uri(path, id)
                    .retrieve()
                    .body(String.class);
        } catch (RestClientResponseException e) {
            log.error("Response with error statusCode: {} responseBody: {}", e.getStatusCode(), e.getResponseBodyAsString());
            return e.getResponseBodyAsString();
        } catch (Exception e) {
            log.error("Unhandled exception", e);
            return "Unhandled exception";
        }

    }

}
