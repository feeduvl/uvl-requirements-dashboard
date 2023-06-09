package com.jiraRestApi.datajiramongodb;

import com.jiraRestApi.datajiramongodb.controller.JiraIssueController;
import com.jiraRestApi.datajiramongodb.services.JiraIssueService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class JiraIssueControllerTest {

    @Autowired
    JiraIssueController jiraIssueController;

    @MockBean
    JiraIssueService jiraIssueService;

    private final String summary = "test";
    private final String key = "key";
    private final String projectName = "project";
    private final String issueType = "Epic";
//    @Test
//    void loadIssuesFromProject() {
//        List<JiraIssue> jiraIssueList = jiraIssueController.loadIssuesFromProject("feeduvl");
//        assertEquals(512, jiraIssueList.size());
//    }

    @Test
    void saveJiraIssues() {
        JiraIssue jiraIssue = new JiraIssue(key, issueType, projectName, summary);
        when(jiraIssueService.saveJiraIssue(any())).thenReturn(jiraIssue);
        List<JiraIssue> issues = new ArrayList<>();
        issues.add(jiraIssue);
        Map<String, Object> map = new HashMap<>();
        map.put("jsonObject", issues);
        List<JiraIssue> jiraIssueList = jiraIssueController.importJiraIssues(map);
        List<JiraIssue> emptyList = new ArrayList<>();
        assertEquals(emptyList, jiraIssueList);
    }

    @Test
    void getAllJiraIssuesfromDB() {
        Pageable paging = PageRequest.of(0, 2);
        JiraIssue jiraIssue = new JiraIssue(key, issueType, projectName, summary);
        List<JiraIssue> issues = new ArrayList<>();
        issues.add(jiraIssue);
        Page<JiraIssue> page = new PageImpl<>(issues);
        when(jiraIssueService.getAllIssuesPaging(paging)).thenReturn(page);
        ResponseEntity<Map<String, Object>> res = jiraIssueController.getAllJiraIssuesfromDB(0,2);
        assertEquals(null, res.getBody());
    }
}
