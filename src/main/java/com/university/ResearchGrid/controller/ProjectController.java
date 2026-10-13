package com.university.ResearchGrid.controller;

import org.springframework.web.bind.annotation.GetMapping;
import com.university.ResearchGrid.repository.ResearchProjectRepository;
import com.university.ResearchGrid.model.ResearchProject;
import org.springframework.cache.annotation.CacheEvict;
import com.university.ResearchGrid.model.Milestone;
import com.university.ResearchGrid.service.ProjectService;
import com.university.ResearchGrid.service.GeminiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ResearchProjectRepository projectRepository;

    @Autowired
    private GeminiService geminiService; // Injected the GeminiService instance here

    @PostMapping
    @CacheEvict(value = "dashboardMetrics", allEntries = true) // Wipes the Redis cache when a new project is saved!
    public ResponseEntity<?> createProject(@RequestBody ResearchProject project) {
        ResearchProject savedProject = projectRepository.save(project);
        return ResponseEntity.ok(savedProject);
    }

    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
    }

    @GetMapping
    public ResponseEntity<List<ResearchProject>> getAllProjects() {
        List<ResearchProject> projects = projectRepository.findAll();
        return ResponseEntity.ok(projects);
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

    @PostMapping("/ai/summarize")
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