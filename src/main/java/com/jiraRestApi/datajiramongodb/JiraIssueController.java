package com.jiraRestApi.datajiramongodb;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
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
@CrossOrigin(origins = "http://localhost:9707")
@RequestMapping("/hitec/jira")
public class JiraIssueController {

    private final JiraIssueService jiraIssueService;

    @GetMapping("/issues/load/proj/{projectName}")
    public List<JiraIssue> loadIssuesFromProject(@PathVariable String projectName){
        String uri = String.format("https://jira-se.ifi.uni-heidelberg.de/rest/api/2/search?jql=project=%s&maxResults=1000", projectName);
        HttpResponse<JsonNode> response = Unirest.get(uri)
                .basicAuth("btuna", "zCZegf00")
                .header("Accept", "application/json")
                .asJson();
        int totalIssues = Integer.parseInt(response.getBody().getObject().getString("total"));
        List<JiraIssue> list = new ArrayList<>();
        for(int i = 0; i < totalIssues; i ++) {
            String id = response.getBody().getObject().getJSONArray("issues").getJSONObject(i).getString("id");
            String key = response.getBody().getObject().getJSONArray("issues").getJSONObject(i).getString("key");
            String issueTyp = response.getBody().getObject().getJSONArray("issues").getJSONObject(i).getJSONObject("fields").getJSONObject("issuetype").getString("name");
            JiraIssue issue = new JiraIssue(id, key, projectName, issueTyp);
            list.add(issue);
        }
        return list;
    }
    @PostMapping("/issues")
    public List<JiraIssue> saveJiraIssues(@RequestBody Map<String, Object> data){
        List <JiraIssue> jiraIssues = jiraIssueService.getAllIssues();
        JSONObject js = new JSONObject(data);
        int size = js.getJSONArray("jsonObject").length();
        for (int i = 0; i < size; i++ ){
            String issueId = js.getJSONArray("jsonObject").getJSONObject(i).getString("issueId");
            String key = js.getJSONArray("jsonObject").getJSONObject(i).getString("key");
            String projectName = js.getJSONArray("jsonObject").getJSONObject(i).getString("projectName");
            String issueType = js.getJSONArray("jsonObject").getJSONObject(i).getString("issueType");
            JiraIssue jiraIssue = new JiraIssue(issueId, key, projectName, issueType);
            Boolean alreadyUsed = false;
            if(jiraIssues.isEmpty()){
                jiraIssueService.saveJiraIssue(jiraIssue);
            }else{
                for(int j =0; j < jiraIssues.size(); j++){
                    if(jiraIssue.getKey().equals(jiraIssues.get(j).getKey())){
                        alreadyUsed = true;
                        break;
                    }
                }
                if(!alreadyUsed){
                    jiraIssueService.saveJiraIssue(jiraIssue);
                }
            }
        }
        jiraIssues = jiraIssueService.getAllIssues();
        return jiraIssues;
    }
    @GetMapping("/issues/all")
    public ResponseEntity<Map<String, Object>> getAllJiraIssuesfromDB(@RequestParam int page, @RequestParam int size){
        try{
            List<JiraIssue> issues = new ArrayList<>();
            Pageable paging = PageRequest.of(page-1, size);
            Page<JiraIssue> pageIssues = jiraIssueService.getAllIssuesPaging(paging);
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
