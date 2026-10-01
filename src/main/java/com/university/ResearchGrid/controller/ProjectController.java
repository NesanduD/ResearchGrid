package com.university.ResearchGrid.controller;
import com.university.ResearchGrid.model.Milestone;
import com.university.ResearchGrid.model.ResearchProject;
import com.university.ResearchGrid.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping
    public ResearchProject createProject(@RequestBody ResearchProject project) {
        return projectService.createProject(project);
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

        // If they don't provide a search term, just return all of them
        return projectService.getAllProjects();
    }
}