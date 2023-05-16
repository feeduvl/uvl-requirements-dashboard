package com.jiraRestApi.datajiramongodb.controller;

import com.jiraRestApi.datajiramongodb.JiraProject;
import com.jiraRestApi.datajiramongodb.repositories.JiraProjectRepository;
import com.jiraRestApi.datajiramongodb.services.JiraIssueService;
import com.jiraRestApi.datajiramongodb.JiraIssue;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@AllArgsConstructor
@RequestMapping("/hitec/jira")
public class JiraIssueController {

    private final JiraIssueService jiraIssueService;
    private final JiraProjectRepository jiraProjectRepository;
    @Autowired
    private final JiraConfiguration jiraConfiguration;

    @GetMapping("/issues/load/issueTypes/{projectName}")
    public List<String> loadIssueTypesFromJiraIssues(@PathVariable String projectName){
        List<String> list = new ArrayList<>();
        System.out.println(jiraConfiguration.username() + ""+ jiraConfiguration.password());
        try {
            String uri = String.format("https://jira-se.ifi.uni-heidelberg.de/rest/api/2/search?jql=project=%s&maxResults=10000", projectName);
            HttpResponse<JsonNode> response = Unirest.get(uri)
                    .basicAuth(jiraConfiguration.username(), jiraConfiguration.password())
                    .header("Accept", "application/json")
                    .asJson();
            setNewProjectNames(response.getBody().getObject().getJSONArray("issues").getJSONObject(0).getJSONObject("fields").getJSONObject("project").getString("name"));
            int totalIssues = Integer.parseInt(response.getBody().getObject().getString("total"));
            boolean alreadyUsed = false;
            for(int i = 0; i < totalIssues; i ++) {
                String issueTyp = response.getBody().getObject().getJSONArray("issues").getJSONObject(i).getJSONObject("fields").getJSONObject("issuetype").getString("name");
                if(list.isEmpty()){
                    list.add(issueTyp);
                }else{
                    for (String s : list) {
                        if (s.equals(issueTyp)) {
                            alreadyUsed = true;
                            break;
                        }
                    }
                    if(!alreadyUsed){
                        list.add(issueTyp);
                    }
                    alreadyUsed = false;
                }
            }
        }catch (Exception e){}
        return list;
    }

    public void setNewProjectNames(String projectName){
        List<JiraProject> jiraProjects = jiraProjectRepository.findAll();
        JiraProject jiraProject = new JiraProject(projectName);
        boolean alreadySaved = false;
        if(jiraProjects.isEmpty()){
            jiraProjectRepository.insert(new JiraProject("-"));
            jiraProjectRepository.insert(jiraProject);
        }else{
            for (JiraProject project : jiraProjects) {
                if (projectName.equals(project.getName())) {
                    alreadySaved = true;
                    break;
                }
            }
            if(!alreadySaved){
                jiraProjectRepository.insert(jiraProject);
            }
        }
    }
    @PostMapping("/issues/load/issues/{projectName}")
    public List<JiraIssue> loadIssuesFromProject(@PathVariable String projectName, @RequestBody Map<String, Object> data){
        List<JiraIssue> list = new ArrayList<>();
        JSONObject js = new JSONObject(data);
        int size = js.getJSONArray("jsonObject").length();
        List<String> issueTypes = new ArrayList<>();
        for (int issue = 0; issue < size; issue++){
            issueTypes.add(js.getJSONArray("jsonObject").getJSONObject(issue).getString("item"));
        }
        for(String issueType : issueTypes) {
            String uri = String.format("https://jira-se.ifi.uni-heidelberg.de/rest/api/2/search?jql=project=%s AND issuetype = '%s'&maxResults=520", projectName, issueType);
            HttpResponse<JsonNode> response = Unirest.get(uri)
                    .basicAuth("btuna", "zCZegf00")
                    .header("Accept", "application/json")
                    .asJson();
            int totalIssues = Integer.parseInt(response.getBody().getObject().getString("total"));
            for (int i = 0; i < totalIssues; i++) {
                String key = response.getBody().getObject().getJSONArray("issues").getJSONObject(i).getString("key");
                String issueTyp = response.getBody().getObject().getJSONArray("issues").getJSONObject(i).getJSONObject("fields").getJSONObject("issuetype").getString("name");
                String projectname = response.getBody().getObject().getJSONArray("issues").getJSONObject(i).getJSONObject("fields").getJSONObject("project").getString("name");
                String summary = response.getBody().getObject().getJSONArray("issues").getJSONObject(i).getJSONObject("fields").getString("summary");
                JiraIssue issue = new JiraIssue(key, issueTyp, projectname, summary);
                list.add(issue);
            }
        }
        return list;
    }
    @PostMapping("/issues/import")
    public List<JiraIssue> addJiraIssues(@RequestBody Map<String, Object> data){
        jiraIssueService.dropCollection();
        List<JiraIssue> list = new ArrayList<>();
        JSONObject js = new JSONObject(data);
        int size = js.getJSONArray("jsonObject").length();
        for (int i = 0; i < size; i++ ){

            String key = js.getJSONArray("jsonObject").getJSONObject(i).getString("key");
            String projectName = js.getJSONArray("jsonObject").getJSONObject(i).getString("projectName");
            String issueType = js.getJSONArray("jsonObject").getJSONObject(i).getString("issueType");
            String summary = js.getJSONArray("jsonObject").getJSONObject(i).getString("summary");
            JiraIssue jiraIssue = new JiraIssue(key, issueType, projectName, summary);
            list.add(jiraIssueService.saveJiraIssue(jiraIssue));
        }
        return list;
    }
    @PostMapping("/issues/add")
    public List<JiraIssue> importJiraIssues(@RequestBody Map<String, Object> data){
        List <JiraIssue> jiraIssues = jiraIssueService.getAllIssues();
        JSONObject js = new JSONObject(data);
        int size = js.getJSONArray("jsonObject").length();
        for (int i = 0; i < size; i++ ){
            String key = js.getJSONArray("jsonObject").getJSONObject(i).getString("key");
            String projectName = js.getJSONArray("jsonObject").getJSONObject(i).getString("projectName");
            String issueType = js.getJSONArray("jsonObject").getJSONObject(i).getString("issueType");
            String summary = js.getJSONArray("jsonObject").getJSONObject(i).getString("summary");
            JiraIssue jiraIssue = new JiraIssue(key, issueType, projectName, summary);
            boolean alreadyUsed = false;
            if(jiraIssues.isEmpty()){
                jiraIssueService.saveJiraIssue(jiraIssue);
            }else{
                for (JiraIssue issue : jiraIssues) {
                    if (jiraIssue.getKey().equals(issue.getKey())) {
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
            if(size == -1){
                size = jiraIssueService.getAllIssues().size();
            }
            Pageable paging = PageRequest.of(page-1, size);
            Page<JiraIssue> pageIssues = jiraIssueService.getAllIssuesPaging(paging);
            List<JiraIssue> issues = pageIssues.getContent();
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
