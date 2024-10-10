package com.nhnacademy.minidooray.service;

import com.nhnacademy.minidooray.exception.CreationFailedException;
import com.nhnacademy.minidooray.exception.RedirectRuntimeException;
import com.nhnacademy.minidooray.model.front.AccountCreateRequest;
import com.nhnacademy.minidooray.util.RestUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountService {
    private final PasswordEncoder passwordEncoder;

    @Value("${dooray.account.host}")
    private String host;

    @Value("${dooray.account.port}")
    private int port;

    private static final String REQUEST_FORMAT = "http://%s:%d/%s";

    public void addAccount(AccountCreateRequest createRequest){
        createRequest.setPassword(passwordEncoder.encode(createRequest.getPassword()));

        String url = String.format(REQUEST_FORMAT, host, port, "/accounts");
        log.trace("Adding account request to {}...", url);
        ResponseEntity<HttpStatus> resp = RestUtil.doRest(
                url,
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
