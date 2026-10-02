package com.university.ResearchGrid.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.util.HashSet;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
public class ResearchProject implements Serializable {

    // It is highly recommended to add a serialVersionUID
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String researchArea;
    private String status;

    @ManyToOne
    @JoinColumn(name = "investigator_id")
    private Researcher principalInvestigator;

    // FetchType.EAGER tells Hibernate to load this immediately so Redis can cache it safely
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Milestone> milestones;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "project_team",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "researcher_id")
    )
    private Set<Researcher> teamMembers = new HashSet<>();
}