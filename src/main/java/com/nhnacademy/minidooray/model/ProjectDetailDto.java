package com.nhnacademy.minidooray.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ProjectDetailDto {
    private long id;

    private String title;
    private List<Member> members;

    private String authorId;

    private List<Task> tasks;
    private State state;


}
