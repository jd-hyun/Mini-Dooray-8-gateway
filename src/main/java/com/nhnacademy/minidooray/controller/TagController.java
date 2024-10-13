package com.nhnacademy.minidooray.controller;

import com.nhnacademy.minidooray.model.ProjectDetailDto;
import com.nhnacademy.minidooray.model.rest.tag.TagCreateRequest;
import com.nhnacademy.minidooray.model.rest.tag.TagResponse;
import com.nhnacademy.minidooray.service.ProjectService;
import com.nhnacademy.minidooray.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/project/{projectId}/tag")
@Controller
public class TagController {
    private final ProjectService projectService;
    private final TagService tagService;

    @GetMapping
    public ModelAndView tagProperties(@PathVariable long projectId) {
        ModelAndView mv = new ModelAndView("tag/tag_list");
        ProjectDetailDto detail = projectService.getProjectDetailById(projectId);

        List<TagResponse> tags = tagService.getTagLists(projectId);

        mv.addObject("tags", tags);
        mv.addObject("project", detail);
        return mv;
    }

    @PostMapping
    public String createTag(@PathVariable long projectId, TagCreateRequest createRequest) {
        tagService.sendCreateRequest(projectId, createRequest);
        return "redirect:/project/"+projectId+"/tag";
    }

    @GetMapping("/delete")
    public String deleteTag(@PathVariable long projectId, @RequestParam long tagId) {
        tagService.sendDeleteRequest(projectId, tagId);
        return "redirect:/project/" + projectId + "/tag";
    }


}
