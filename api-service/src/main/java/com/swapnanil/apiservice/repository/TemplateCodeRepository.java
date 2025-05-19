package com.swapnanil.apiservice.repository;

import com.swapnanil.apiservice.model.TemplateCode;
import com.swapnanil.apiservice.model.TemplateCodeKey;
import com.swapnanil.apiservice.model.TesterCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TemplateCodeRepository extends JpaRepository<TemplateCode, TemplateCodeKey> {
    List<TesterCode> findAllByIdPid(String pid);
}
