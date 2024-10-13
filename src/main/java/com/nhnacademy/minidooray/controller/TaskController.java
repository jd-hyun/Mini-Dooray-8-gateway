package com.nhnacademy.minidooray.controller;

import com.nhnacademy.minidooray.model.Milestone;
import com.nhnacademy.minidooray.model.ProjectDetailDto;
import com.nhnacademy.minidooray.model.rest.task.TaskCreateRequest;
import com.nhnacademy.minidooray.model.rest.task.TaskResponseDto;
import com.nhnacademy.minidooray.service.MilestoneService;
import com.nhnacademy.minidooray.service.ProjectService;
import com.nhnacademy.minidooray.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/task")
@Controller
public class TaskController {
    private final TaskService taskService;
    private final ProjectService projectService;
    private final MilestoneService milestoneService;

    @GetMapping("/{taskId}")
    public ModelAndView getTaskInfo( @PathVariable long taskId, @RequestParam long pid) {
        TaskResponseDto taskResponse = taskService.getTaskDetail(pid, taskId);

        ProjectDetailDto projectDetail = projectService.getProjectDetailById(pid);

        ModelAndView mv = new ModelAndView("task/task_detail");
        mv.addObject("task", taskResponse);
        mv.addObject("project", projectDetail);
        mv.addObject("ls", System.lineSeparator());

        return mv;
    }

    @GetMapping("/create")
    public ModelAndView createForm(@RequestParam("pid") long projectId) {
        ModelAndView mv = new ModelAndView("task/create_form");
        ProjectDetailDto projectDetail = projectService.getProjectDetailById(projectId);

        List<Milestone> milestones = milestoneService.findMilestonesByProjectId(projectId);

        mv.addObject("milestones", milestones);
        mv.addObject("project", projectDetail);
        return mv;
    }

    @PostMapping("/create")
    public String createTask(@RequestParam("pid") long projectId, TaskCreateRequest createRequest) {
        taskService.sendCreateRequest(projectId, createRequest);
        return "redirect:/project/"+projectId;
    }

    @GetMapping("/delete")
    public String deleteTask(@RequestParam("pid") long projectId, @RequestParam("tid") long taskId) {
        taskService.sendDeleteRequest(projectId, taskId);
        return "redirect:/project/"+projectId;
    }
}
