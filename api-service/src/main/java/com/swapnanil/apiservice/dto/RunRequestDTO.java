package com.swapnanil.apiservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RunRequestDTO {
    @NotBlank
    private String status;

    private long exitCode;
    private String stdOut;
    private String stdErr;

    @NotBlank
    private String verdict;
}
