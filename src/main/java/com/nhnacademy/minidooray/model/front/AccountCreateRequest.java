package com.nhnacademy.minidooray.model.front;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AccountCreateRequest {
    @NotBlank
    private String id;
    @NotBlank
    private String password;
    @Email
    private String email;
}
