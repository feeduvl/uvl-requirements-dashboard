package com.jiraRestApi.datajiramongodb;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class JiraIssue {
    @Id
    private String id;
    private String issueId;
    private String key;
    private String projectName;

    private String issueType;

    public JiraIssue(String issueId, String key, String projectName, String issueType) {
        this.issueId = issueId;
        this.key = key;
        this.projectName = projectName;
        this.issueType = issueType;
    }
    public String getIssueType() {
        return issueType;
    }
    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public String getIssueId() {
        return issueId;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
