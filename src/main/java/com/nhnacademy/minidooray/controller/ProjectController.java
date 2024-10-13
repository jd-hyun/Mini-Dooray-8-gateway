package com.nhnacademy.minidooray.controller;

import com.nhnacademy.minidooray.model.ProjectDetailDto;
import com.nhnacademy.minidooray.model.ProjectSimpleDto;
import com.nhnacademy.minidooray.model.rest.project.ProjectCreateRequest;
import com.nhnacademy.minidooray.model.rest.project.ProjectUpdateRequest;
import com.nhnacademy.minidooray.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

//@RequiredArgsConstructor
@RequestMapping("/project")
@Slf4j
@Controller
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Qualifier("accountRequestBuilder")
    @Autowired
    private ObjectProvider<UriComponentsBuilder> accountBuilderProvider;

    @Qualifier("taskRequestBuilder")
    @Autowired
    private ObjectProvider<UriComponentsBuilder> taskBuilderProvider;

//    예비용 코드
//    @ModelAttribute("pk")
//    public String injectPk(HttpSession session) {
//        return (String) session.getAttribute("pk");
//    }

    @GetMapping
    public String getProjectList(@AuthenticationPrincipal UserDetails user, Model model /*, @RequestParam String memberId*/) {
        // TODO: MEMBER ID

        log.trace("User: {}", user.getUsername());
//        final String memberId = "123";
        final String memberId = user.getUsername();

        List<ProjectSimpleDto> projectList = projectService.getProjectSimpleListByMemberId(memberId);

        model.addAttribute("projectList", projectList);
        return "project_list";
    }

    @GetMapping("/{projectId}")
    public String getProjectPage(@AuthenticationPrincipal UserDetails user, @PathVariable long projectId, Model model) {
        ProjectDetailDto detail = projectService.getProjectDetailById(projectId);

        UriComponents fetchMemberList = accountBuilderProvider.getIfAvailable().path("/accounts?like=").build();
        UriComponents fetchAddMember = taskBuilderProvider.getIfAvailable().path("/projects/{projectId}/members?memberId=").buildAndExpand(projectId);

//        String username = user.getUsername();
//        boolean isMember = detail.getMembers().stream().anyMatch(member -> member.getId().equals(username));
//
//        if (detail.getAuthorId().equals(username) || isMember) {
        if (true) {
            model.addAttribute("project", detail);
            model.addAttribute("memberRequestPath", fetchMemberList.toUriString());
            model.addAttribute("memberAddPath", fetchAddMember.toUriString());
//            model.addAllAttributes(detail.toModelMap());
            return "project/project_detail";
        } else {
            // TODO: 예외처리
            return "redirect:/";
        }
    }

    @PostMapping("/create")
    public String createProject(ProjectCreateRequest createRequest) {
        projectService.sendCreateRequest(createRequest);
        return "redirect:/project";
    }

    @PostMapping("/modify")
    public String modifyProject(@RequestParam("pid") long projectId, ProjectUpdateRequest updateRequest) {
        projectService.sendUpdateRequest(projectId, updateRequest);
        return "redirect:/project";
    }

    @GetMapping("/delete")
    public String deleteProject(@RequestParam("pid") long projectId) {
        projectService.sendDeleteRequest(projectId);
        return "redirect:/project";
    }

}
