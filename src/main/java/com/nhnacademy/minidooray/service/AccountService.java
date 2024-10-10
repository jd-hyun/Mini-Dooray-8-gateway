package com.nhnacademy.minidooray.service;

import com.nhnacademy.minidooray.exception.CreationFailedException;
import com.nhnacademy.minidooray.exception.RedirectRuntimeException;
import com.nhnacademy.minidooray.model.front.AccountCreateRequest;
import com.nhnacademy.minidooray.util.RestUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AccountService {
    private final PasswordEncoder passwordEncoder;

    public void addAccount(AccountCreateRequest createRequest){
        createRequest.setPassword(passwordEncoder.encode(createRequest.getPassword()));
        ResponseEntity<HttpStatus> resp = RestUtil.doRest(
                "http://localhost:8082/account",
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
