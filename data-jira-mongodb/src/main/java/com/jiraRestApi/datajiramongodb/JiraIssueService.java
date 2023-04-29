package com.jiraRestApi.datajiramongodb;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class JiraIssueService {

    private final JiraIssueRepository issueRepository;

    public JiraIssue saveJiraIssue(JiraIssue issue){
        return issueRepository.insert(issue);
    }

    public List<JiraIssue> getAllIssues(){
        return issueRepository.findAll();
    }
    public Page<JiraIssue> getAllJiraIssues(String projectName, Pageable paging){
        return issueRepository.findByProjectName(projectName, paging);
    }
}
