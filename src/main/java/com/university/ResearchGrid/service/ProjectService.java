package com.university.ResearchGrid.service;
import com.university.ResearchGrid.model.Milestone;
import com.university.ResearchGrid.model.ResearchProject;
import com.university.ResearchGrid.repository.MilestoneRepository;
import com.university.ResearchGrid.repository.ResearchProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ResearchProjectRepository projectRepository;

    @Autowired
    private MilestoneRepository milestoneRepository;

    public ResearchProject createProject(ResearchProject project) {
        return projectRepository.save(project);
    }

    public List<ResearchProject> getAllProjects() {
        return projectRepository.findAll();
    }

    public Milestone addMilestoneToProject(Long projectId, Milestone milestone) {
        // 1. Find the parent project, or throw an error if it doesn't exist
        ResearchProject project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with ID: " + projectId));

        // 2. Attach the project to the milestone
        milestone.setProject(project);

        // 3. Save the milestone to the database
        return milestoneRepository.save(milestone);
    }
}