package com.alexlib.rest_client_demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class ExampleRestClientConfiguration {

    @Value("${remoteItemService.host}")
    String baseURI;

    @Bean
    RestClient restClient(RestClient.Builder builder) {   //You need to inject the builder to let the MockRestServiceServer to work when using RestClientTest

        DefaultUriBuilderFactory defaultUriBuilderFactory = new DefaultUriBuilderFactory(baseURI);   //set baseUrl here
        defaultUriBuilderFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);  //This is the place to configure the url encoding behaviour


        return builder
                //.baseUrl(baseURI)// Setting baseUrl here will not work  https://github.com/spring-projects/spring-framework/issues/32180
                .uriBuilderFactory(defaultUriBuilderFactory)
                .build();
    }


}
