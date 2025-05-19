package com.swapnanil.apiservice.dto;

import com.swapnanil.apiservice.model.TesterCode;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TesterCodeDTO {
    @NotBlank
    private String lang;

    @NotBlank
    private String code;

    public static TesterCodeDTO from(TesterCode testerCode) {
        return TesterCodeDTO.builder()
                .lang(testerCode.getId().getLang())
                .code(testerCode.getCode())
                .build();
    }
}
