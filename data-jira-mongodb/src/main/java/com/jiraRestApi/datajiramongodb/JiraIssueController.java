package com.jiraRestApi.datajiramongodb;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:8082")
@RequestMapping("/api")
public class JiraIssueController {

    private final JiraIssueService jiraIssueService;

    @GetMapping("/issues/load/{projectName}")
    public ResponseEntity<JiraIssue> loadIssuesFromJira(@PathVariable String projectName){
        String uri = String.format("https://jira-se.ifi.uni-heidelberg.de/rest/api/2/search?jql=project=%s&maxResults=1000", projectName);
        HttpResponse<JsonNode> response = Unirest.get(uri)
                .basicAuth("btuna", "zCZegf00")
                .header("Accept", "application/json")
                .asJson();
        int totalIssues = Integer.parseInt(response.getBody().getObject().getString("total"));
        List<JiraIssue> jiraIssues = jiraIssueService.getAllIssues();
        Boolean alreadyUsed = false;
        for(int i = 0; i < totalIssues; i ++){
            String id = response.getBody().getObject().getJSONArray("issues").getJSONObject(i).getString("id");
            String key = response.getBody().getObject().getJSONArray("issues").getJSONObject(i).getString("key");
            JiraIssue issue = new JiraIssue(id, key, projectName);
            if(jiraIssues.isEmpty()){
                jiraIssueService.saveJiraIssue(issue);
            }else{
                for(int j =0; j < jiraIssues.size(); j++){
                    if(issue.getKey().equals(jiraIssues.get(j).getKey())){
                        alreadyUsed = true;
                        break;
                    }
                }
                if(!alreadyUsed){
                    jiraIssueService.saveJiraIssue(issue);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping( "/issues/get/{projectName}")
    public ResponseEntity<Map<String, Object>> saveAllIssuesinDB(@PathVariable String projectName, @RequestParam int page, @RequestParam int size) throws Exception {
        try{
            List<JiraIssue> issues = new ArrayList<>();
            Pageable paging = PageRequest.of(page-1, size);
            Page<JiraIssue> pageIssues = jiraIssueService.getAllJiraIssues(projectName, paging);

            issues = pageIssues.getContent();

            Map<String, Object> res = new HashMap<>();
            res.put("issues", issues);
            res.put("currentPage", pageIssues.getNumber());
            res.put("totalItems", pageIssues.getTotalElements());
            res.put("totalPages", pageIssues.getTotalPages());
            return new ResponseEntity<>(res, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
