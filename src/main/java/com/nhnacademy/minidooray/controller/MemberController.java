package com.nhnacademy.minidooray.controller;

import com.nhnacademy.minidooray.model.Account;
import com.nhnacademy.minidooray.model.Member;
import com.nhnacademy.minidooray.model.ProjectDetailDto;
import com.nhnacademy.minidooray.service.AccountService;
import com.nhnacademy.minidooray.service.MemberService;
import com.nhnacademy.minidooray.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/project/{projectId}/member")
@Controller
public class MemberController {
    private final ProjectService projectService;
    private final MemberService memberService;
    private final AccountService accountService;
//
//    @ModelAttribute("project")
//    public ProjectDetailDto projectDetail(@PathVariable long projectId) {
//        return projectService.getProjectDetailById(projectId);
//    }

//    @GetMapping
//    public ModelAndView getMemberList(@PathVariable long projectId, @RequestParam(required = false) String search) {
//        ModelAndView mv = new ModelAndView("project/member_list");
//        List<Member> members = memberService.getMemberList(projectId);
//
//        if (search != null && !search.isEmpty()) {
//            List<Account> accounts = accountService.searchById(search);
//            mv.addObject("searched", accounts.stream().map(Member::of).toList());
//        } else {
//            mv.addObject("searched", List.of());
//        }
//
//        mv.addObject("members", members);
//        return mv;
//    }

    @GetMapping("/create")
    public String addMember(@PathVariable long projectId, @RequestParam String memberId) {
        memberService.sendCreateRequest(projectId, memberId);
        return "redirect:/project/" + projectId + "/member";
    }

    @GetMapping("/delete")
    public String deleteMember(@PathVariable long projectId, @RequestParam String memberId) {
        memberService.sendDeleteRequest(projectId, memberId);
        return "redirect:/project/" + projectId + "/member";
    }
}
