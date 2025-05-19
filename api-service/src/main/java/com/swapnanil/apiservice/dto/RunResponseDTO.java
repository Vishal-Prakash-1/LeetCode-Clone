package com.swapnanil.apiservice.dto;

import com.swapnanil.apiservice.model.Run;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RunResponseDTO {
    private String id;
    private ProblemResponseDTO problem;
    private String type;
    private String status;
    private String lang;
    private String code;
    private long exitCode;
    private String stdOut;
    private String stdErr;
    private String verdict;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    public static RunResponseDTO from(Run run) {
        return RunResponseDTO.builder()
                .id(run.getId())
                .problem(ProblemResponseDTO.from(run.getProblem()))
                .type(run.getType())
                .status(run.getStatus())
                .lang(run.getLang())
                .code(run.getCode())
                .exitCode(run.getExitCode())
                .stdOut(run.getStdOut())
                .stdErr(run.getStdErr())
                .verdict(run.getVerdict())
                .createdAt(run.getCreatedAt())
                .updatedAt(run.getUpdatedAt())
                .build();
    }
}
