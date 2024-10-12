package com.nhnacademy.minidooray.controller;

import com.nhnacademy.minidooray.model.ProjectDetailDto;
import com.nhnacademy.minidooray.model.rest.task.TaskResponseDto;
import com.nhnacademy.minidooray.util.RestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@RequestMapping("/task")
@Controller
public class TaskController {
    @Qualifier("taskRequestBuilder")
    @Autowired
    private ObjectProvider<UriComponentsBuilder> builderProvider;

    @GetMapping("/{taskId}")
    public ModelAndView getTaskInfo(/* UserDetail user */ @PathVariable long taskId, @RequestParam long pid) {
        UriComponentsBuilder builder = builderProvider.getIfAvailable();
        log.trace("{}", System.identityHashCode(builder));
        UriComponents components = builder.path("/projects/{projectId}/tasks/{taskId}")
                .encode().buildAndExpand(pid, taskId);

        ResponseEntity<TaskResponseDto> resp = RestUtil.doRest(
                components.toUriString(),
                HttpMethod.GET,
                null,
                TaskResponseDto.class
        );

        UriComponentsBuilder projectBuilder = builderProvider.getIfAvailable();
        UriComponents projectComponent  = projectBuilder.path("/projects/{projectId}")
                .encode().buildAndExpand(pid);

        ResponseEntity<ProjectDetailDto> projectResp = RestUtil.doRest(
                projectComponent.toUriString(),
                HttpMethod.GET,
                null,
                ProjectDetailDto.class
        );

        ModelAndView mv = new ModelAndView("task/task_detail");
        mv.addObject("task", resp.getBody());
        mv.addObject("project", projectResp.getBody());
        mv.addObject("ls", System.lineSeparator());

        return mv;
    }
}
