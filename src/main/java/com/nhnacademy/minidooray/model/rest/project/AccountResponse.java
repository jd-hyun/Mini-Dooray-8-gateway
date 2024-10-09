package com.nhnacademy.minidooray.model.rest.project;


import lombok.NonNull;

public record AccountResponse(@NonNull String id, @NonNull String password, @NonNull String email) {
}
