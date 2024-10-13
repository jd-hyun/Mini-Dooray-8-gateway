package com.nhnacademy.minidooray.controller;

import com.nhnacademy.minidooray.model.rest.comment.CommentCreateRequest;
import com.nhnacademy.minidooray.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/comment")
@Controller
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/create")
    public String createComment(
            /* UserDetail user, */
            @RequestParam("pid") long projectId, @RequestParam("tid") long taskId, @ModelAttribute CommentCreateRequest createRequest) {
        commentService.sendCreateRequest(projectId, taskId, createRequest);
        return "redirect:/task/" + taskId + "?pid=" + projectId;
    }

    @GetMapping("/delete")
    public String deleteComment(
            @RequestParam("pid") long projectId,
            @RequestParam("tid") long taskId,
            @RequestParam("cid") long commentId
    ) {
        commentService.sendDeleteRequest(projectId, taskId, commentId);
        return "redirect:/task/" + taskId + "?pid=" + projectId;
    }
}
