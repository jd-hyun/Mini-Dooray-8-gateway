package com.nhnacademy.minidooray.model;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class Comment {
    private long id;
    private String authorId;
    private String content;
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private ZonedDateTime createdAt;
}
