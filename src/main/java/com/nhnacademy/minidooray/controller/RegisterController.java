package com.nhnacademy.minidooray.controller;

import com.nhnacademy.minidooray.annotation.RedirectOnException;
import com.nhnacademy.minidooray.model.front.AccountCreateRequest;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Controller
public class RegisterController {
    private final RestTemplate restTemplate;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String getRegisterForm() {
        return "account/register_form";
    }

    @PostMapping("/register")
    @RedirectOnException(exceptions = {ValidationException.class}, value = "/register")
    public String createAccount(@RequestParam @Valid AccountCreateRequest createRequest) {
        createRequest.setPassword(passwordEncoder.encode(createRequest.getPassword()));
        HttpEntity<AccountCreateRequest> entity = new HttpEntity<>(createRequest, null);

        ResponseEntity<HttpStatus> resp = restTemplate.exchange(
                "http://localhost:8082/account",
                HttpMethod.POST,
                entity,
                HttpStatus.class
        );
        if (resp.getStatusCode().is2xxSuccessful()) {
            return "redirect:/login";
        } else {
            // TODO: 예외처리.
            return "redirect:/register";
        }
    }
}
