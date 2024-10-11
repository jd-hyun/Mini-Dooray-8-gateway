package com.nhnacademy.minidooray.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum State {
    ACTIVE("활성"), SLEEP("휴면"), TERMINATED("종료");
    private final String view;

    public String toCssClass() {
        return this.name().toLowerCase();
    }

    @JsonCreator
    public static State fromJson(String json) {
        return State.valueOf(json);
    }
}
