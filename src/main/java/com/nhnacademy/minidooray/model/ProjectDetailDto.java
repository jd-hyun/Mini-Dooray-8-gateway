package com.nhnacademy.minidooray.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDetailDto {
    private long id;
    private String title;
    private List<Member> members;

    private String authorId;

    private List<Task> tasks;
    private State state;
}
