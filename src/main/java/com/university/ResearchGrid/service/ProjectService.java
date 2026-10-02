package com.university.ResearchGrid.service;
import com.university.ResearchGrid.model.Milestone;
import com.university.ResearchGrid.model.ResearchProject;
import com.university.ResearchGrid.model.Researcher;
import com.university.ResearchGrid.repository.MilestoneRepository;
import com.university.ResearchGrid.repository.ResearchProjectRepository;
import com.university.ResearchGrid.repository.ResearcherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ResearchProjectRepository projectRepository;

    @Autowired
    private MilestoneRepository milestoneRepository;

    @Autowired
    private ResearcherRepository researcherRepository;

    public ResearchProject createProject(ResearchProject project) {
        return projectRepository.save(project);
    }

    @Cacheable(value = "project_cache")
    public List<ResearchProject> getAllProjects() {
        System.out.println("Fetching all projects from PostgreSQL...");
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

    public ResearchProject assignInvestigator(Long projectId, Long researcherId) {
        // 1. Find the project
        ResearchProject project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with ID: " + projectId));

        // 2. Find the researcher
        Researcher researcher = researcherRepository.findById(researcherId)
                .orElseThrow(() -> new RuntimeException("Researcher not found with ID: " + researcherId));

        // 3. Link them and save
        project.setPrincipalInvestigator(researcher);
        return projectRepository.save(project);
    }

    public ResearchProject addTeamMember(Long projectId, Long researcherId) {
        ResearchProject project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with ID: " + projectId));

        Researcher researcher = researcherRepository.findById(researcherId)
                .orElseThrow(() -> new RuntimeException("Researcher not found with ID: " + researcherId));

        project.getTeamMembers().add(researcher);
        return projectRepository.save(project);
    }
    @Cacheable(value = "project_cache", key = "#status")
    public List<ResearchProject> getProjectsByStatus(String status){
        System.out.println("Fetching projects by status from PostgreSQL...");
        return projectRepository.findByStatus(status);
    }
    @Cacheable(value = "project_cache", key = "#researchArea")
    public List<ResearchProject> getProjectsByArea(String researchArea){
        System.out.println("Fetching projects by area from PostgreSQL...");
        return projectRepository.findByResearchArea(researchArea);
    }
}
