package com.university.ResearchGrid.service;
import com.university.ResearchGrid.model.Researcher;
import com.university.ResearchGrid.repository.ResearcherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResearcherService {

    @Autowired
    private ResearcherRepository researcherRepository;

    public Researcher createResearcher(Researcher researcher) {
        return researcherRepository.save(researcher);
    }

    public List<Researcher> getAllResearchers() {
        return researcherRepository.findAll();
    }
}