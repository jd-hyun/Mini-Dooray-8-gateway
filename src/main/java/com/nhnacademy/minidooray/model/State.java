package com.nhnacademy.minidooray.model;

public enum State {
    ACTIVE, SLEEP, TERMINATED;

    public String toCssClass() {
        return this.name().toLowerCase();
    }
}
