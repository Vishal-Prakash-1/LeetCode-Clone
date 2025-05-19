package com.swapnanil.apiservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class TemplateCode {
    @EmbeddedId
    private TemplateCodeKey id;

    @ManyToOne
    @JoinColumn(name = "pid", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Problem problem;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String code;
}
