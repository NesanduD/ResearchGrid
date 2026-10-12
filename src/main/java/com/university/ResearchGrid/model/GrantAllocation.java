package com.university.ResearchGrid.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
public class GrantAllocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fundingAgency;

    private BigDecimal amount;

    private LocalDate allocationDate;

    // Links this grant to a specific Research Project
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    @JsonIgnore // Prevents infinite JSON loops when returning API responses
    private ResearchProject project;
}