package com.university.ResearchGrid.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;
import java.util.ArrayList;

@Entity
@Data
public class ResearchProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String researchArea;
    private String status;

    @ManyToOne
    @JoinColumn(name = "investigator_id")
    private Researcher principalInvestigator;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Milestone> milestones;

    @ManyToMany
    @JoinTable(
            name = "project_team",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "researcher_id")
    )
    private List<Researcher> teamMembers = new ArrayList<>();
}