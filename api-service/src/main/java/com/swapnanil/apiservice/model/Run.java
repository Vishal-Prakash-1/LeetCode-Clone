package com.swapnanil.apiservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Run {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private String id;

    @ManyToOne
    @JoinColumn(name = "pid", referencedColumnName = "id", nullable = false,  updatable = false)
    private Problem problem;

    @OneToOne(mappedBy = "run", cascade = CascadeType.ALL)
    private Submission submission;

    @Column(nullable = false, updatable = false)
    private String type;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false, updatable = false)
    private String lang;

    @Column(columnDefinition = "TEXT", nullable = false, updatable = false)
    private String code;

    private long exitCode;

    @Column(columnDefinition = "TEXT")
    private String stdOut;

    @Column(columnDefinition = "TEXT")
    private String stdErr;

    private String verdict;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private ZonedDateTime updatedAt;
}
