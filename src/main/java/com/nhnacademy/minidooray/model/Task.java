package com.nhnacademy.minidooray.model;

import lombok.Data;

import java.util.List;

@Data
public class Task {
    private String title;
    private String content;
    private List<String> tags;
    private Milestone milestone;
}
