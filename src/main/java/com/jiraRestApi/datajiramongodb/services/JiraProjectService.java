package com.jiraRestApi.datajiramongodb.services;
import com.jiraRestApi.datajiramongodb.JiraProject;
import com.jiraRestApi.datajiramongodb.repositories.JiraProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class JiraProjectService {

    private final JiraProjectRepository jiraProjectRepository;

    public List<JiraProject> getAllProjectsNames(){
        return jiraProjectRepository.findAll();
    }
}
