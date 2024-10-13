package com.nhnacademy.minidooray.service;

import com.nhnacademy.minidooray.model.rest.tag.TagCreateRequest;
import com.nhnacademy.minidooray.model.rest.tag.TagResponse;
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
public class TagService {
    @Qualifier("taskRequestBuilder")
    @Autowired
    private ObjectProvider<UriComponentsBuilder> builderProvider;

    private final ParameterizedTypeReference<List<TagResponse>> listParameterizedTypeReference
            = new ParameterizedTypeReference<List<TagResponse>>() {};

    public List<TagResponse> getTagLists(long projectId) {
        UriComponents components = builderProvider.getIfAvailable().path("/projects/{projectId}/tags").buildAndExpand(projectId);

        ResponseEntity<List<TagResponse>> resp = RestUtil.doRest(
                components.toUriString(),
                HttpMethod.GET,
                null,
                listParameterizedTypeReference
        );
        return resp.getBody();
    }

    public void sendCreateRequest(long projectId, TagCreateRequest request) {
        UriComponents components = builderProvider.getIfAvailable().path("/projects/{projectId}/tags")
                .encode().buildAndExpand(projectId);

        ResponseEntity<HttpStatus> resp = RestUtil.doRest(
                components.toUriString(),
                HttpMethod.POST,
                request,
                HttpStatus.class
        );
    }

    public void sendDeleteRequest(long projectId, long tagId) {
        UriComponents components = builderProvider.getIfAvailable().path("/projects/{projectId}/tags/{tagId}")
                .encode().buildAndExpand(projectId, tagId);

        ResponseEntity<HttpStatus> resp = RestUtil.doRest(
                components.toUriString(),
                HttpMethod.DELETE,
                null,
                HttpStatus.class
        );
    }
}
