package com.university.ResearchGrid.controller;
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
}