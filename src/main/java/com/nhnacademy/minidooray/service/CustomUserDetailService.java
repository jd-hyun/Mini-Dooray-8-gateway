package com.nhnacademy.minidooray.service;

import com.nhnacademy.minidooray.model.rest.project.AccountResponse;
import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
@Component
public class CustomUserDetailService implements UserDetailsService {
    private final RestTemplate restTemplate;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ResponseEntity<AccountResponse> resp = restTemplate.exchange(
                "http://localhost:8081/account/"+username,
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
