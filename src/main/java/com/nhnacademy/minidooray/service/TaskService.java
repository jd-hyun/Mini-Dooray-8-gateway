package com.nhnacademy.minidooray.service;

import com.nhnacademy.minidooray.model.rest.task.TaskCreateRequest;
import com.nhnacademy.minidooray.model.rest.task.TaskResponseDto;
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
public class TaskService {
    @Qualifier("taskRequestBuilder")
    @Autowired
    private ObjectProvider<UriComponentsBuilder> builderProvider;

    public TaskResponseDto getTaskDetail(long projectId, long taskId) {
        UriComponentsBuilder builder = builderProvider.getIfAvailable();
        UriComponents components = builder.path("/projects/{projectId}/tasks/{taskId}")
                .encode().buildAndExpand(projectId, taskId);

        ResponseEntity<TaskResponseDto> resp = RestUtil.doRest(
                components.toUriString(),
                HttpMethod.GET,
                null,
                TaskResponseDto.class
        );

        return resp.getBody();
    }
    public void sendCreateRequest(long projectId, TaskCreateRequest request) {
        UriComponentsBuilder builder = builderProvider.getIfAvailable();
        UriComponents components = builder.path("/projects/{projectId}/tasks")
                .encode().buildAndExpand(projectId);

        ResponseEntity<HttpStatus> resp = RestUtil.doRest(
                components.toUriString(),
                HttpMethod.POST,
                request,
                HttpStatus.class
        );
    }
    public void sendDeleteRequest(long projectId, long taskId) {
        UriComponentsBuilder builder = builderProvider.getIfAvailable();
        UriComponents components = builder.path("/projects/{projectId}/tasks/{taskId}")
                .encode().buildAndExpand(projectId, taskId);

        ResponseEntity<HttpStatus> resp = RestUtil.doRest(
                components.toUriString(),
                HttpMethod.DELETE,
                null,
                HttpStatus.class
        );
    }
}
