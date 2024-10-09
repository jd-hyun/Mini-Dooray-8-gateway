package com.nhnacademy.minidooray.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDetailDto {
    private String title;
    private List<Member> members;
    private List<Task> tasks;
    private State state;
    private List<String> tags;
    private Milestone milestone;
}
