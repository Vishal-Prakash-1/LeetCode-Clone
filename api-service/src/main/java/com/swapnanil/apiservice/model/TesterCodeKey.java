package com.swapnanil.apiservice.model;

import jakarta.persistence.Embeddable;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@Embeddable
public class TesterCodeKey {
    private String pid;
    private String lang;
}
