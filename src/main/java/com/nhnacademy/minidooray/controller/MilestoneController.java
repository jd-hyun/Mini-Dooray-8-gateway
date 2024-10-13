package com.nhnacademy.minidooray.controller;

import com.nhnacademy.minidooray.model.Milestone;
import com.nhnacademy.minidooray.model.ProjectDetailDto;
import com.nhnacademy.minidooray.model.rest.milestone.MilestoneCreateRequest;
import com.nhnacademy.minidooray.service.MilestoneService;
import com.nhnacademy.minidooray.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/project/{projectId}/milestone")
@Controller
public class MilestoneController {
    private final MilestoneService milestoneService;
    private final ProjectService projectService;

    @GetMapping
    public ModelAndView getMilestoneList(@PathVariable long projectId) {
        List<Milestone> list = milestoneService.findMilestonesByProjectId(projectId);
        ProjectDetailDto project = projectService.getProjectDetailById(projectId);

        ModelAndView mv = new ModelAndView("project/milestone_list");
        mv.addObject("milestones", list);
        mv.addObject("project", project);
        return mv;
    }

    @PostMapping
    public String createMilestone(@PathVariable long projectId, @ModelAttribute MilestoneCreateRequest createRequest) {
        milestoneService.sendCreateRequest(projectId, createRequest);
        return "redirect:/project/" + projectId + "/milestone";
    }

    @GetMapping("/delete")
    public String deleteMilestone(@PathVariable long projectId, @RequestParam("mid") long milestoneId) {
        milestoneService.sendDeleteRequest(projectId, milestoneId);
        return "redirect:/project/" + projectId + "/milestone";
    }

}
