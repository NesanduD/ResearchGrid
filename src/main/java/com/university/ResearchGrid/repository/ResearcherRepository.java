package com.university.ResearchGrid.repository;
import com.university.ResearchGrid.model.Researcher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResearcherRepository extends JpaRepository<Researcher, Long> {

    // Spring Boot magically writes the SQL for this custom search just based on the method name!
    Optional<Researcher> findByEmail(String email);
}