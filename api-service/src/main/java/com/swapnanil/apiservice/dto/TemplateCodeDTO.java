package com.swapnanil.apiservice.dto;

import com.swapnanil.apiservice.model.TemplateCode;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TemplateCodeDTO {
    @NotBlank
    private String lang;

    @NotBlank
    private String code;

    public static TemplateCodeDTO from(TemplateCode templateCode) {
        return TemplateCodeDTO.builder()
                .lang(templateCode.getId().getLang())
                .code(templateCode.getCode())
                .build();
    }
}
