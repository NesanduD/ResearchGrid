package com.university.ResearchGrid.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Milestone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String status;

    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonIgnore
    private ResearchProject project;
}