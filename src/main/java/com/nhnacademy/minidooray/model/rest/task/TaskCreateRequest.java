package com.nhnacademy.minidooray.model.rest.task;

public record TaskCreateRequest(String title, String contents, Long milestoneId) {
}
