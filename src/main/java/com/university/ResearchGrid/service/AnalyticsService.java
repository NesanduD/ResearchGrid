package com.university.ResearchGrid.service;

import com.university.ResearchGrid.repository.GrantAllocationRepository;
import com.university.ResearchGrid.repository.PublicationRepository;
import com.university.ResearchGrid.repository.ResearchProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class AnalyticsService {

    @Autowired
    private ResearchProjectRepository projectRepo;

    @Autowired
    private PublicationRepository pubRepo;

    @Autowired
    private GrantAllocationRepository grantRepo;

    // This annotation tells Spring to intercept requests and check Redis first
    @Cacheable(value = "analytics_cache", key = "'dashboard_metrics'")
    public Map<String, Object> getDashboardMetrics() {

        // Simulating a heavy, complex analytical SQL query delay (2 seconds)
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        Map<String, Object> metrics = new HashMap<>();
        metrics.put("totalProjects", projectRepo.count());
        metrics.put("totalPublications", pubRepo.count());

        BigDecimal totalFunding = grantRepo.getTotalGrantFunding();
        metrics.put("totalGrantFunding", totalFunding != null ? totalFunding : BigDecimal.ZERO);

        return metrics;
    }
}