package com.jiraRestApi.datajiramongodb.controller;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("jira")
public record JiraConfiguration(String username, String password) {
}
