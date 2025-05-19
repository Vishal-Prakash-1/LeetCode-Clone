package com.swapnanil.apiservice.dto;

import com.swapnanil.apiservice.model.Problem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProblemResponseDTO {
    private String id;
    private String title;
    private String prompt;
    private String difficulty;
    private String sampleInputs;
    private String sampleOutputs;
    private String hiddenInputs;
    private String hiddenOutputs;
    private List<TesterCodeDTO> testerCodeList;
    private List<TemplateCodeDTO> templateCodeList;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    public static ProblemResponseDTO from(Problem problem) {
        return ProblemResponseDTO.builder()
                .id(problem.getId())
                .title(problem.getTitle())
                .prompt(problem.getPrompt())
                .difficulty(problem.getDifficulty())
                .sampleInputs(problem.getSampleInputs())
                .sampleOutputs(problem.getSampleOutputs())
                .hiddenInputs(problem.getHiddenInputs())
                .hiddenOutputs(problem.getHiddenOutputs())
                .testerCodeList(problem.getTesterCodeList().stream().map(TesterCodeDTO::from).toList())
                .templateCodeList(problem.getTemplateCodeList().stream().map(TemplateCodeDTO::from).toList())
                .createdAt(problem.getCreatedAt())
                .updatedAt(problem.getUpdatedAt())
                .build();
    }
}
