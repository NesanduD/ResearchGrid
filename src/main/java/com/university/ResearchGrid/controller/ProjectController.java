package com.university.ResearchGrid.controller;
import com.university.ResearchGrid.model.ResearchProject;
import com.university.ResearchGrid.repository.ResearchProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ResearchProjectRepository repository;

    @PostMapping
    public ResearchProject createProject(@RequestBody ResearchProject project) {
        return repository.save(project);
    }

    @GetMapping
    public List<ResearchProject> getAllProjects() {
        return repository.findAll();
    }
}