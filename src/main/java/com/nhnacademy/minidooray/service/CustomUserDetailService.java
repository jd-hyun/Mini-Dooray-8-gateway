package com.nhnacademy.minidooray.service;

import com.nhnacademy.minidooray.model.rest.project.AccountResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomUserDetailService implements UserDetailsService {
    private final RestTemplate restTemplate;

    @Value("${dooray.account.host}")
    private String host;

    @Value("${dooray.account.port}")
    private int port;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String url = String.format("http://%s:%d/api/account/%s", host, port, username);
        log.trace("Try to Login... Formatted url: {}", url);
        ResponseEntity<AccountResponse> resp = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                AccountResponse.class
        );
        if (!resp.getStatusCode().is2xxSuccessful()) {
            throw new UsernameNotFoundException(username);
        }
        AccountResponse response = resp.getBody();
        return new User(username, response.password(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
