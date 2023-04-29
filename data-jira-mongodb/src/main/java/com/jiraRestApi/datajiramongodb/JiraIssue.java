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

    public JiraIssue(String issueId, String key, String projectName) {
        this.issueId = issueId;
        this.key = key;
        this.projectName = projectName;
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
