package com.jiraRestApi.datajiramongodb.repositories;

import com.jiraRestApi.datajiramongodb.JiraProject;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JiraProjectRepository extends MongoRepository<JiraProject, String> {
}
