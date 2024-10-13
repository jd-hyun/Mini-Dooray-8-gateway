package com.nhnacademy.minidooray.service;

import com.nhnacademy.minidooray.model.Member;
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

import java.util.List;

@Service
public class MemberService {
    @Qualifier("taskRequestBuilder")
    @Autowired
    private ObjectProvider<UriComponentsBuilder> builderProvider;

    private final ProjectService projectService;

    public MemberService(ProjectService projectService) {
        this.projectService = projectService;
    }

    public List<Member> getMemberList(long projectId) {
        return projectService.getProjectDetailById(projectId).getMembers();
    }

    public void sendCreateRequest(long projectId, String memberId) {
        UriComponents components = builderProvider.getIfAvailable().path("/projects/{projectId}/members")
                .queryParam("memberId", memberId).encode().buildAndExpand(projectId);

        ResponseEntity<HttpStatus> resp = RestUtil.doRest(
                components.toUriString(),
                HttpMethod.PUT,
                null,
                HttpStatus.class
        );
    }

    public void sendDeleteRequest(long projectId, String memberId) {
        UriComponents components = builderProvider.getIfAvailable().path("/projects/{projectId}/members")
                .queryParam("memberId", memberId).encode().buildAndExpand(projectId);

        ResponseEntity<HttpStatus> resp = RestUtil.doRest(
                components.toUriString(),
                HttpMethod.DELETE,
                null,
                HttpStatus.class
        );
    }
}
