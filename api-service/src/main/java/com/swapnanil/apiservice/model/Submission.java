package com.swapnanil.apiservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private String id;

    @ManyToOne
    @JoinColumn(name = "pid", referencedColumnName = "id", nullable = false, updatable = false)
    private Problem problem;

    @OneToOne
    @JoinColumn(name="rid", referencedColumnName = "id", nullable = false, updatable = false)
    private Run run;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private ZonedDateTime createdAt;
}
