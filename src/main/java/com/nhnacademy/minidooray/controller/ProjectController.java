package com.nhnacademy.minidooray.controller;

import com.nhnacademy.minidooray.model.ProjectDetailDto;
import com.nhnacademy.minidooray.model.ProjectSimpleDto;
import com.nhnacademy.minidooray.service.ProjectService;
import com.nhnacademy.minidooray.util.RestUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.nhnacademy.minidooray.util.RestUtil.REQUEST_FORMAT;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/project")
@Slf4j
@Controller
public class ProjectController {
    @Value("${dooray.task.host}")
    private String host;

    @Value("${dooray.task.port}")
    private int port;

    private final ProjectService projectService;

    private final ParameterizedTypeReference<List<ProjectSimpleDto>> reference = new ParameterizedTypeReference<List<ProjectSimpleDto>>() {};

    // TODO: Project Service로 리팩토링하기
    @GetMapping
    public String getProjectList(/* UserDetails User */ Model model /*, @RequestParam String memberId*/) {
        // TODO: MEMBER ID
        final String memberId = "123";
        List<ProjectSimpleDto> projectList = projectService.getProjectSimpleListByMemberId(memberId);

        model.addAttribute("projectList", projectList);
        return "project_list";
    }

    @GetMapping("/{projectId}")
    public String getProjectPage(/*UserDetails user, */@PathVariable long projectId, Model model) {
        ProjectDetailDto detail = projectService.getProjectDetailById(projectId);
//        String username = user.getUsername();
//        boolean isMember = detail.getMembers().stream().anyMatch(member -> member.getId().equals(username));

//        if (detail.getAuthorId().equals(username) || isMember) {
        if (true) {
            model.addAttribute("project", detail);
//            model.addAllAttributes(detail.toModelMap());
            return "project/project_detail";
        } else {
            // TODO: 예외처리
            return "redirect:/";
        }
    }
}
