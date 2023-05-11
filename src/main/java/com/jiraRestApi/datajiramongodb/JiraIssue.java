package com.jiraRestApi.datajiramongodb;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class JiraIssue {
    @Id
    private String id;
    private String key;
    private String projectName;
    private String issueType;
    private String summary;

    public JiraIssue(){}
    public JiraIssue(String key, String issueType, String projectName, String summary) {
        this.key = key;
        this.projectName = projectName;
        this.issueType = issueType;
        this.summary = summary;
    }
    public String getIssueType() {
        return issueType;
    }
    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
