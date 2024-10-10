package com.nhnacademy.minidooray.controller;

import com.nhnacademy.minidooray.annotation.RedirectOnException;
import com.nhnacademy.minidooray.exception.CreationFailedException;
import com.nhnacademy.minidooray.model.front.AccountCreateRequest;
import com.nhnacademy.minidooray.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class RegisterController {
    private final AccountService accountService;

    @GetMapping("/register")
    public String getRegisterForm() {
        return "account/register_form";
    }

    @PostMapping("/register")
    @RedirectOnException(exceptions = {CreationFailedException.class}, value="/register")
    // @Valid 는 controller 로 진입전에 평가하므로 Aspect advice가 작동하지 않음.
//    @RedirectOnException(exceptions = {MethodArgumentNotValidException.class}, value = "/register")
    public String createAccount(@Valid AccountCreateRequest createRequest) {
        accountService.addAccount(createRequest);
        return "redirect:/";
    }
}
