package com.jiraRestApi.datajiramongodb;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = JiraIssueService.class)
@ExtendWith(SpringExtension.class)
class JiraIssueServiceTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    JiraIssueService jiraIssueService;

    @MockBean
    JiraIssueRepository jiraIssueRepository;

    private final String issueId = "1";
    private final String key = "key";
    private final String projectName = "project";
    private final String issueType = "Epic";
    @Test
    void saveJiraIssue() {
        JiraIssue jiraIssue = new JiraIssue(issueId, key, projectName, issueType);
        when(jiraIssueRepository.insert(jiraIssue)).thenReturn(jiraIssue);
        JiraIssue issue = jiraIssueService.saveJiraIssue(jiraIssue);
        assertEquals("key", issue.getKey());
    }

    @Test
    void getAllIssues() {
        JiraIssue jiraIssue = new JiraIssue(issueId, key, projectName, issueType);
        List<JiraIssue> list = new ArrayList<>();
        list.add(jiraIssue);
        when(jiraIssueRepository.findAll()).thenReturn(list);
        List<JiraIssue> issues = jiraIssueService.getAllIssues();
        assertEquals(1, issues.size());
    }

    @Test
    void getAllIssuesPaging() {
        JiraIssue jiraIssue = new JiraIssue(issueId, key, projectName, issueType);
        List<JiraIssue> list = new ArrayList<>();
        list.add(jiraIssue);
        Page<JiraIssue> page = new PageImpl<>(list);
        Pageable paging = PageRequest.of(0, 1);
        when(jiraIssueRepository.findAll(paging)).thenReturn(page);
        Page<JiraIssue> issuesPage = jiraIssueService.getAllIssuesPaging(paging);
        assertEquals(1, issuesPage.getTotalElements());
    }
}