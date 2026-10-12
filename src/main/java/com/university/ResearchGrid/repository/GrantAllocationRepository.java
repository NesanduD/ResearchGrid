package com.university.ResearchGrid.repository;

import com.university.ResearchGrid.model.GrantAllocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;

@Repository
public interface GrantAllocationRepository extends JpaRepository<GrantAllocation, Long> {

    @Query("SELECT SUM(g.amount) FROM GrantAllocation g")
    BigDecimal getTotalGrantFunding();
}