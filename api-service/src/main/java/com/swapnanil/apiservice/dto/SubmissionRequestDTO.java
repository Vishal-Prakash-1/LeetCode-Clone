package com.swapnanil.apiservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubmissionRequestDTO {
    @NotBlank
    private String pid;

    @NotBlank
    private String code;

    @NotNull
    private String lang;
}
