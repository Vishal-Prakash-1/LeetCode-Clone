package com.swapnanil.apiservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"title"}),
        @UniqueConstraint(columnNames = {"prompt"}),
})
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private String id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String prompt;

    @Column(nullable = false)
    private String difficulty;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String sampleInputs;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String sampleOutputs;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String hiddenInputs;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String hiddenOutputs;

    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL)
    private List<Submission> submissionList;

    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL)
    private List<Run> runList;

    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL)
    private List<TesterCode> testerCodeList;

    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL)
    private List<TemplateCode> templateCodeList;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private ZonedDateTime updatedAt;
}
