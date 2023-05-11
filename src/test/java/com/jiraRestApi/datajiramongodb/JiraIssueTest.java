package com.jiraRestApi.datajiramongodb;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
class JiraIssueTest {
    private final String summary = "test";
    private final String key = "key";
    private final String projectName = "project";
    private final String issueType = "Epic";
    @Test
    public void TestJiraIssuesConstructor(){
        JiraIssue jiraIssue = new JiraIssue(key, issueType, projectName, summary);
        assertEquals("test", jiraIssue.getSummary());
        assertEquals("key", jiraIssue.getKey());
        assertEquals("project", jiraIssue.getProjectName());
        assertEquals("Epic", jiraIssue.getIssueType());
    }
    @Test
    public void GetterSetter(){
        JiraIssue jiraIssue = new JiraIssue();
        jiraIssue.setId("1");
        jiraIssue.setKey(key);
        jiraIssue.setProjectName(projectName);
        jiraIssue.setIssueType(issueType);
        assertEquals("1", jiraIssue.getId());
        assertEquals("key", jiraIssue.getKey());
        assertEquals("project", jiraIssue.getProjectName());
        assertEquals("Epic", jiraIssue.getIssueType());
    }
    @Test
    void testEquals() {
        JiraIssue jiraIssue = new JiraIssue(key, issueType, projectName, summary);
        JiraIssue emptyIssue = new JiraIssue();
        jiraIssue.setId("1");
        assertTrue(emptyIssue.equals(emptyIssue));
        assertTrue(jiraIssue.equals(jiraIssue));
        assertTrue(jiraIssue.canEqual(jiraIssue));
        assertEquals(jiraIssue.hashCode(), jiraIssue.hashCode());
    }
    @Test
    void testToString() {
        JiraIssue jiraIssue = new JiraIssue(key, issueType, projectName, summary);
        jiraIssue.setId("1");
        assertEquals(jiraIssue.toString(), jiraIssue.toString());
    }
}