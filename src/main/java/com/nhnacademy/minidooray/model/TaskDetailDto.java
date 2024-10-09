package com.nhnacademy.minidooray.model;

import lombok.Data;

@Data
public class TaskDetailDto {
    private Task task;
    private String projectTitle;
    private Comment comment;
}
