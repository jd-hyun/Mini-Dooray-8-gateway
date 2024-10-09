package com.nhnacademy.minidooray.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Comment {
    private long id;
    private String author;
    private String content;
    private LocalDateTime createdAt;
}
