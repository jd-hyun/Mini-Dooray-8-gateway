package com.nhnacademy.minidooray.service;

import com.nhnacademy.minidooray.model.rest.comment.CommentCreateRequest;
import com.nhnacademy.minidooray.util.RestUtil;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class CommentService {
    @Qualifier("taskRequestBuilder")
    @Autowired
    private ObjectProvider<UriComponentsBuilder> builderProvider;

    public void sendCreateRequest(long projectId, long taskId, CommentCreateRequest request) {
        UriComponents components = builderProvider.getIfAvailable()
                .path("/projects/{projectId}/tasks/{taskId}/comments").encode()
                .buildAndExpand(projectId, taskId);

        ResponseEntity<HttpStatus> resp = RestUtil.doRest(
                components.toUriString(),
                HttpMethod.POST,
                request,
                HttpStatus.class
        );
    }

    public void sendDeleteRequest(long projectId, long taskId, long commentId) {
        UriComponents components = builderProvider.getIfAvailable()
                .path("/projects/{projectId}/tasks/{taskId}/comments/{commentId}").encode()
                .buildAndExpand(projectId, taskId, commentId);

        ResponseEntity<HttpStatus> resp = RestUtil.doRest(
                components.toUriString(),
                HttpMethod.DELETE,
                null,
                HttpStatus.class
        );
    }
}
