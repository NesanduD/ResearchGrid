package com.university.ResearchGrid.service;
import com.university.ResearchGrid.model.ResearchProject;
import com.university.ResearchGrid.repository.ResearchProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ResearchProjectRepository repository;

    public ResearchProject createProject(ResearchProject project) {
        // Later, we will add business logic here (like checking if the title already exists)
        return repository.save(project);
    }

    public List<ResearchProject> getAllProjects() {
        return repository.findAll();
    }
}