package com.nhnacademy.minidooray.model.rest.task;

import com.nhnacademy.minidooray.model.Comment;
import com.nhnacademy.minidooray.model.Milestone;

import java.util.List;

public record TaskResponseDto(
        long projectId,
        long id,
        String title,
        List<String> tags,
        Milestone milestone,
        String content,
        List<Comment> comments
) {
}
