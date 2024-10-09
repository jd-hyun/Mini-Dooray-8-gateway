package com.nhnacademy.minidooray.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Map<String, Object> toModelMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("title", title);
        map.put("members", members);
        map.put("author", authorId);
        map.put("tasks", tasks);
        map.put("state", state);
        return map;
    }
}
