package com.university.ResearchGrid.controller;
import com.university.ResearchGrid.model.Researcher;
import com.university.ResearchGrid.service.ResearcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/researchers")
public class ResearcherController {

    @Autowired
    private ResearcherService researcherService;

    @PostMapping
    public Researcher createResearcher(@RequestBody Researcher researcher) {
        return researcherService.createResearcher(researcher);
    }

    @GetMapping
    public List<Researcher> getAllResearchers() {
        return researcherService.getAllResearchers();
    }
}