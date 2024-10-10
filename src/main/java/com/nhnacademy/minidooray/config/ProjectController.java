package com.nhnacademy.minidooray.config;

import com.nhnacademy.minidooray.model.Member;
import com.nhnacademy.minidooray.model.ProjectDetailDto;
import com.nhnacademy.minidooray.model.ProjectSimpleDto;
import com.nhnacademy.minidooray.util.RestUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/project")
@Slf4j
@Controller
public class ProjectController {
    private final RestTemplate restTemplate;

    private final ParameterizedTypeReference<List<ProjectSimpleDto>> reference = new ParameterizedTypeReference<List<ProjectSimpleDto>>() {};

    @GetMapping
    public String getProjectList(/* UserDetails User */ Model model) {
        // TODO: MEMBER ID
        final String memberId = "123";
        ResponseEntity<List<ProjectSimpleDto>> resp = RestUtil.doRest(
                "http://localhost:8082/api/project?memberId="+memberId,
                HttpMethod.GET,
                null,
                reference
        );
        model.addAttribute("projectList", resp.getBody());
        return "project_list";
    }

    @GetMapping("/{projectId}")
    public String getProjectPage(UserDetails user, @PathVariable String projectId, Model model) {
        ResponseEntity<ProjectDetailDto> resp = restTemplate.exchange(
                "http://localhost:8082/api/project/"+projectId,
                HttpMethod.GET,
                null,
                ProjectDetailDto.class
        );
        if (!resp.getStatusCode().is2xxSuccessful()) {
            return "redirect:/";
        }
        ProjectDetailDto detail =  resp.getBody();
        String username = user.getUsername();
        boolean isMember = detail.getMembers().stream().anyMatch(member -> member.getId().equals(username));

        if (detail.getAuthorId().equals(username) || isMember) {
            model.addAllAttributes(detail.toModelMap());
            return "project/project_detail";
        } else {
            // TODO: 예외처리
            return "redirect:/";
        }
    }
}
