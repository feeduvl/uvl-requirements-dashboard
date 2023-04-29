package com.jiraRestApi.datajiramongodb;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JiraIssueRepository extends MongoRepository<JiraIssue, String> {

    Page<JiraIssue> findAll(Pageable pageable);
    Page<JiraIssue> findByProjectName(String projectName, Pageable pageable);
}
