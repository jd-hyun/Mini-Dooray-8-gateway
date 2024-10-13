package com.nhnacademy.minidooray.service;

import com.nhnacademy.minidooray.exception.CreationFailedException;
import com.nhnacademy.minidooray.model.Milestone;
import com.nhnacademy.minidooray.model.rest.milestone.MilestoneCreateRequest;
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
public class MilestoneService {
    @Qualifier("taskRequestBuilder")
    @Autowired
    private ObjectProvider<UriComponentsBuilder> builderProvider;

    private final ParameterizedTypeReference<List<Milestone>> listTypeReference
            = new ParameterizedTypeReference<List<Milestone>>() {};

    public List<Milestone> findMilestonesByProjectId(long projectId) {
        UriComponents components = builderProvider.getIfAvailable()
                .path("/projects/{projectId}/milestones").encode()
                .buildAndExpand(projectId);

        ResponseEntity<List<Milestone>> resp = RestUtil.doRest(
                components.toUriString(),
                HttpMethod.GET,
                null,
                listTypeReference
        );

        return resp.getBody();
    }

    public Milestone sendCreateRequest(long projectId, MilestoneCreateRequest request) {
        UriComponents components = builderProvider.getIfAvailable()
                .path("/projects/{projectId}/milestones").encode()
                .buildAndExpand(projectId);

        ResponseEntity<Milestone> resp = RestUtil.doRest(
                components.toUriString(),
                HttpMethod.POST,
                request,
                Milestone.class
        );

        if (!resp.getStatusCode().is2xxSuccessful()) {
            throw new CreationFailedException("Milestone create failed");
        }

        return resp.getBody();
    }

    public void sendDeleteRequest(long projectId, long milestoneId) {
        UriComponents components = builderProvider.getIfAvailable()
                .path("/projects/{projectId}/milestones/{milestoneId}").encode()
                .buildAndExpand(projectId, milestoneId);

        ResponseEntity<HttpStatus> resp = RestUtil.doRest(
                components.toUriString(),
                HttpMethod.DELETE,
                null,
                HttpStatus.class
        );
    }

}
