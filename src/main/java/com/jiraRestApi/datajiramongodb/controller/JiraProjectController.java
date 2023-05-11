package com.jiraRestApi.datajiramongodb.controller;

import com.jiraRestApi.datajiramongodb.JiraProject;
import com.jiraRestApi.datajiramongodb.services.JiraProjectService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@AllArgsConstructor
@RequestMapping("/hitec/jira")
public class JiraProjectController {

    private final JiraProjectService jiraProjectService;

    @GetMapping("/projectNames")
    public List<JiraProject> getAllProjectsNames(){
        return jiraProjectService.getAllProjectsNames();
    }
}
