package com.nhnacademy.minidooray.util;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

public class RestUtil {
    private static final RestTemplate restTemplate = new RestTemplate();
    static {
//        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    }

    public static <T, U> ResponseEntity<T> doRest(String url, HttpMethod method, U body, Class<T> clazz) {
        HttpEntity<U> entity = new HttpEntity<>(body, null);
        return restTemplate.exchange(url, method, entity, clazz);
    }
    public static <T, U> ResponseEntity<T> doRest(String url, HttpMethod method, U body, ParameterizedTypeReference<T> type) {
        HttpEntity<U> entity = new HttpEntity<>(body, null);
        return restTemplate.exchange(url, method, entity, type);
    }
}
