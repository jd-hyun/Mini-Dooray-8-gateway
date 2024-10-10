package com.nhnacademy.minidooray.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum State {
    ACTIVE, SLEEP, TERMINATED;

    public String toCssClass() {
        return this.name().toLowerCase();
    }

    @JsonCreator
    public static State fromJson(String json) {
        return State.valueOf(json);
    }
}
