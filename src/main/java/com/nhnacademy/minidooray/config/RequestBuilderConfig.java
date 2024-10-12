package com.nhnacademy.minidooray.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.util.UriComponentsBuilder;

@Configuration
public class RequestBuilderConfig {
    @Value("${dooray.task.host}")
    private String taskHost;

    @Value("${dooray.task.port}")
    private int taskPort;

    @Value("${dooray.account.host}")
    private String accountHost;

    @Value("${dooray.account.port}")
    private int accountPort;

    @Bean("accountRequestBuilder")
    @Scope("prototype")
    public UriComponentsBuilder accountRequestBuilder() {
        return UriComponentsBuilder.newInstance().scheme("http")
                .host(accountHost)
                .port(accountPort)
                .path("/api");
    }

    @Bean("taskRequestBuilder")
    @Scope("prototype")
    public UriComponentsBuilder taskRequestBuilder() {
        return UriComponentsBuilder.newInstance().scheme("http")
                .host(taskHost)
                .port(taskPort)
                .path("/api");
    }
}
