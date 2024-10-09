package com.nhnacademy.minidooray.model.rest.project;

import com.nhnacademy.minidooray.model.State;

public record ProjectUpdateRequest(String title, State state) {
}
