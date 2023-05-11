package com.jiraRestApi.datajiramongodb;

import com.jiraRestApi.datajiramongodb.repositories.JiraIssueRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class JiraIssueRepositoryTest {

    @MockBean
    JiraIssueRepository jiraIssueRepository;
    @Test
    void findAll() {
        Pageable paging = PageRequest.of(0, 1);
        jiraIssueRepository.findAll(paging);
        verify(jiraIssueRepository, times(1)).findAll(paging);
    }
}