package com.university.ResearchGrid.repository;
import com.university.ResearchGrid.model.ResearchProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResearchProjectRepository extends JpaRepository<ResearchProject, Long> {
    // Spring Boot turns this into: SELECT * FROM research_project WHERE status = ?
    List<ResearchProject> findByStatus(String status);

    // Spring Boot turns this into: SELECT * FROM research_project WHERE research_area = ?
    List<ResearchProject> findByResearchArea(String researchArea);
}