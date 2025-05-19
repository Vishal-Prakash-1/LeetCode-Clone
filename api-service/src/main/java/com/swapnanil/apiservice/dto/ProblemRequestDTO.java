package com.swapnanil.apiservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProblemRequestDTO {
    @NotBlank
    private String title;

    @NotBlank
    private String prompt;

    @NotBlank
    private String difficulty;

    @NotBlank
    private String sampleInputs;

    @NotBlank
    private String sampleOutputs;

    @NotBlank
    private String hiddenInputs;

    @NotBlank
    private String hiddenOutputs;

    @NotEmpty
    @NotNull
    @Valid
    private List<TesterCodeDTO> testerCodeList;

    @NotEmpty
    @NotNull
    @Valid
    private List<TemplateCodeDTO> templateCodeList;
}
