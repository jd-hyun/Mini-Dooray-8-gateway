package com.nhnacademy.minidooray.service;

import com.nhnacademy.minidooray.exception.CreationFailedException;
import com.nhnacademy.minidooray.model.Account;
import com.nhnacademy.minidooray.model.front.AccountCreateRequest;
import com.nhnacademy.minidooray.util.RestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Slf4j
@Service
public class AccountService {
    private final PasswordEncoder passwordEncoder;
    private final ParameterizedTypeReference<List<Account>> listParameterizedTypeReference
            = new ParameterizedTypeReference<List<Account>>() {};

    public AccountService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Qualifier("accountRequestBuilder")
    @Autowired
    private ObjectProvider<UriComponentsBuilder> builderProvider;

    public void addAccount(AccountCreateRequest createRequest){
        createRequest.setPassword(passwordEncoder.encode(createRequest.getPassword()));

        UriComponents components = builderProvider.getIfAvailable().path("/accounts").build();

        log.trace("Adding account request to {}...", components.toUriString());
        ResponseEntity<HttpStatus> resp = RestUtil.doRest(
                components.toUriString(),
                HttpMethod.POST,
                createRequest,
                HttpStatus.class
        );
        switch(resp.getStatusCode()) {
            case HttpStatus.OK, HttpStatus.CREATED -> {}

            case HttpStatus.CONFLICT -> throw new CreationFailedException(
                    "ID Already Exists.. Try other id"
            );
            default -> throw new RuntimeException();
        }
    }
//
//    public List<Account> searchById(String id) {
//        UriComponents components = builderProvider.getIfAvailable().path("/accounts")
//                .queryParam("like", id).encode().build();
//
//        ResponseEntity<List<Account>> resp = RestUtil.doRest(
//                components.toUriString(),
//                HttpMethod.GET,
//                null,
//                listParameterizedTypeReference
//        );
//
//        return resp.getBody();
//    }

}
