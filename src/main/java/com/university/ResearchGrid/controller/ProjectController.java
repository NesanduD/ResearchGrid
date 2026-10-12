package com.university.ResearchGrid.controller;

import com.university.ResearchGrid.model.Milestone;
import com.university.ResearchGrid.model.ResearchProject;
import com.university.ResearchGrid.service.ProjectService;
import com.university.ResearchGrid.service.GeminiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private GeminiService geminiService; // Injected the GeminiService instance here

    @PostMapping
    public ResearchProject createProject(@RequestBody ResearchProject project) {
        return projectService.createProject(project);
    }

    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
    }

    @GetMapping
    public List<ResearchProject> getAllProjects() {
        return projectService.getAllProjects();
    }

    @PostMapping("/{projectId}/milestones")
    public Milestone addMilestone(@PathVariable Long projectId, @RequestBody Milestone milestone) {
        return projectService.addMilestoneToProject(projectId, milestone);
    }

    @PutMapping("/{projectId}/investigator/{researcherId}")
    public ResearchProject assignInvestigator(@PathVariable Long projectId, @PathVariable Long researcherId) {
        return projectService.assignInvestigator(projectId, researcherId);
    }

    @PostMapping("/{projectId}/members/{researcherId}")
    public ResearchProject addTeamMember(@PathVariable Long projectId, @PathVariable Long researcherId) {
        return projectService.addTeamMember(projectId, researcherId);
    }

    @GetMapping("/search")
    public List<ResearchProject> searchProjects(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String researchArea){

        if (status != null) {
            return projectService.getProjectsByStatus(status);
        } else if (researchArea != null) {
            return projectService.getProjectsByArea(researchArea);
        }

        return projectService.getAllProjects();
    }

    @GetMapping("/{projectId}/ai-milestones")
    public String generateAiMilestones(@PathVariable Long projectId){
        return projectService.getAiMilestoneSuggestions(projectId);
    }

    @PostMapping("/ai/summarize-abstract")
    public String summarizeAbstract(@RequestBody Map<String, String> payload) {
        String abstractText = payload.get("abstractText");
        // Now using the injected lowercase instance
        return geminiService.summarizeAbstract(abstractText);
    }

    @PostMapping("/ai/classify-domain")
    public String classifyDomain(@RequestBody Map<String, String> payload) {
        String title = payload.getOrDefault("title", "");
        String description = payload.getOrDefault("description", "");
        // Now using the injected lowercase instance
        return geminiService.classifyDomain(title, description);
    }
}