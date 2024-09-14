package com.alexlib.rest_client_demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(ExampleRestClient.class)
@Import(ExampleRestClientConfiguration.class)
public class ExampleRestClientTest {

    @Autowired
    ExampleRestClient client;

    @Autowired
    MockRestServiceServer server;
    //Usage look at https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/client/MockRestServiceServer.html


    @Test
    void getItemWithId_WithIdSuccess_ReturnItemContent(){
        server.expect( requestTo("http://example.com/item/123")).andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("item content", MediaType.TEXT_PLAIN));

        String response = client.getItemWithId("123");
        assertEquals("item content", response);
    }

    @Test
    void getItemWithId_WithId500_ReturnErrorMessage(){
        server.expect( requestTo("http://example.com/item/123")).andExpect(method(HttpMethod.GET))
                .andRespond(withServerError().contentType(MediaType.TEXT_PLAIN).body("Error message"));

        String response = client.getItemWithId("123");
        assertEquals("Error message", response);
    }



}
