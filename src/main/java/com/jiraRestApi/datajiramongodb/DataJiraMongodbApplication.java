package com.jiraRestApi.datajiramongodb;
import com.jiraRestApi.datajiramongodb.controller.JiraConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(JiraConfiguration.class)
public class DataJiraMongodbApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataJiraMongodbApplication.class, args);
	}


}
