package com.nhnacademy.minidooray.controller;

import com.nhnacademy.minidooray.model.ProjectDetailDto;
import com.nhnacademy.minidooray.model.ProjectSimpleDto;
import com.nhnacademy.minidooray.model.rest.project.ProjectCreateRequest;
import com.nhnacademy.minidooray.model.rest.project.ProjectUpdateRequest;
import com.nhnacademy.minidooray.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/project")
@Slf4j
@Controller
public class ProjectController {
    private final ProjectService projectService;

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

    @PostMapping("/create")
    public String createProject(ProjectCreateRequest createRequest) {
        projectService.sendCreateRequest(createRequest);
        return "redirect:/project";
    }

    @PostMapping("/modify")
    public String modifyProject(@RequestParam("pid") long projectId, @RequestBody ProjectUpdateRequest updateRequest) {
        projectService.sendUpdateRequest(projectId, updateRequest);
        return "redirect:/project/"+projectId;
    }

    @GetMapping("/delete")
    public String deleteProject(@RequestParam("pid") long projectId) {
        projectService.sendDeleteRequest(projectId);
        return "redirect:/project";
    }
}
