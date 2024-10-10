package com.nhnacademy.minidooray.util;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.event.Level;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Slf4j
public class RestUtil {
    private static final Level LOG_LEVEL = Level.TRACE;
    private static final RestTemplate restTemplate = new RestTemplate();
    static {
//        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    }
    private static void logRequest(Level level, Object... args) {
        log.makeLoggingEventBuilder(level).log("Send request to {}...", args);
    }

    private static void logResponse(Level level, Object... args) {
        log.makeLoggingEventBuilder(level).log("Received response from {} with status code {}", args);
    }

    public static <T, U> ResponseEntity<T> doRest(String url, HttpMethod method, U body, Class<T> clazz) {
        logRequest(LOG_LEVEL, url);

        HttpEntity<U> entity = new HttpEntity<>(body, null);
        ResponseEntity<T> response = restTemplate.exchange(url, method, entity, clazz);

        logResponse(LOG_LEVEL, url, response.getStatusCode());
        return response;
    }
    public static <T, U> ResponseEntity<T> doRest(String url, HttpMethod method, U body, ParameterizedTypeReference<T> type) {
        logRequest(LOG_LEVEL, url);

        HttpEntity<U> entity = new HttpEntity<>(body, null);
        ResponseEntity<T> response = restTemplate.exchange(url, method, entity, type);

        logResponse(LOG_LEVEL, url, response.getStatusCode());
        return response;
    }
}
