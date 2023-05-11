package com.jiraRestApi.datajiramongodb;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoOperations;

@SpringBootApplication
public class DataJiraMongodbApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataJiraMongodbApplication.class, args);
	}
	@Bean
	public CommandLineRunner initData(MongoOperations mongo) {
		return (String... args) -> {
			mongo.dropCollection(JiraIssue.class);
		};
	}

}
