package com.nhnacademy.minidooray.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectSimpleDto {
    private String title;
    private State state;
    private List<String> tags;
    private Milestone milestone;
}
