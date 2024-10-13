package com.nhnacademy.minidooray.service;

import com.nhnacademy.minidooray.exception.CreationFailedException;
import com.nhnacademy.minidooray.exception.RedirectRuntimeException;
import com.nhnacademy.minidooray.model.front.AccountCreateRequest;
import com.nhnacademy.minidooray.util.RestUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
public class AccountService {
    private final PasswordEncoder passwordEncoder;

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
}
