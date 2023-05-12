package com.jiraRestApi.datajiramongodb.services;

import com.jiraRestApi.datajiramongodb.JiraIssue;
import com.jiraRestApi.datajiramongodb.repositories.JiraIssueRepository;
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
    public Page<JiraIssue> getAllIssuesPaging(Pageable paging){
        return issueRepository.findAll(paging);
    }
    public void dropCollection(){
        this.issueRepository.deleteAll();
    }
}
