package com.nhnacademy.minidooray.service;

import com.nhnacademy.minidooray.exception.CreationFailedException;
import com.nhnacademy.minidooray.exception.EntityAlreadyExistsException;
import com.nhnacademy.minidooray.exception.EntityNotExistsException;
import com.nhnacademy.minidooray.model.ProjectDetailDto;
import com.nhnacademy.minidooray.model.ProjectSimpleDto;
import com.nhnacademy.minidooray.model.rest.project.ProjectCreateRequest;
import com.nhnacademy.minidooray.model.rest.project.ProjectUpdateRequest;
import com.nhnacademy.minidooray.util.RestUtil;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class ProjectService {
    @Qualifier("taskRequestBuilder")
    @Autowired
    private ObjectProvider<UriComponentsBuilder> builderProvider;

    private final ParameterizedTypeReference<List<ProjectSimpleDto>> listParameterizedTypeReference
            = new ParameterizedTypeReference<List<ProjectSimpleDto>>() {};

    public List<ProjectSimpleDto> getProjectSimpleListByMemberId(String memberId) {
        UriComponentsBuilder builder = builderProvider.getIfAvailable();
        UriComponents components = builder.path("/projects").queryParam("memberId", memberId)
                .encode().build();

        ResponseEntity<List<ProjectSimpleDto>> resp = RestUtil.doRest(
                components.toUriString(),
                HttpMethod.GET,
                null,
                listParameterizedTypeReference
        );

        return resp.getBody();
    }

    public ProjectDetailDto getProjectDetailById(long projectId) {
        UriComponentsBuilder builder = builderProvider.getIfAvailable();
        UriComponents components = builder.path("/projects/{projectId}").encode().buildAndExpand(projectId);

        ResponseEntity<ProjectDetailDto> resp = RestUtil.doRest(
                components.toUriString(),
                HttpMethod.GET,
                null,
                ProjectDetailDto.class
        );

        if (!resp.getStatusCode().is2xxSuccessful()) {
            throw new EntityNotExistsException("That project does not exist.");
        }

        return resp.getBody();
    }

    public void sendCreateProjectRequest(ProjectCreateRequest request) {
        UriComponents components = builderProvider.getIfAvailable().path("/projects")
                .encode().build();

        ResponseEntity<HttpStatus> resp = RestUtil.doRest(
                components.toUriString(),
                HttpMethod.POST,
                null,
                HttpStatus.class
        );

        if (!resp.getStatusCode().is2xxSuccessful()) {
            throw new CreationFailedException("Creating project was failed");
        }
    }

    public void sendDeleteProjectRequest(long projectId) {
        UriComponents components = builderProvider.getIfAvailable().path("/projects/{projectId}")
                .encode().buildAndExpand(projectId);

        ResponseEntity<HttpStatus> resp = RestUtil.doRest(
                components.toUriString(),
                HttpMethod.DELETE,
                null,
                HttpStatus.class
        );
    }

    public ProjectSimpleDto sendUpdateProjectRequest(long projectId, ProjectUpdateRequest request) {
        UriComponents components = builderProvider.getIfAvailable().path("/projects/{projectId}")
                .encode().buildAndExpand(projectId);

        ResponseEntity<ProjectSimpleDto> resp = RestUtil.doRest(
                components.toUriString(),
                HttpMethod.PUT,
                request,
                ProjectSimpleDto.class
        );

        return resp.getBody();
    }
}
