package com.swapnanil.apiservice.service;

import com.swapnanil.apiservice.dto.ProblemRequestDTO;
import com.swapnanil.apiservice.dto.TemplateCodeDTO;
import com.swapnanil.apiservice.dto.TesterCodeDTO;
import com.swapnanil.apiservice.model.*;
import com.swapnanil.apiservice.repository.ProblemRepository;
import com.swapnanil.apiservice.repository.TemplateCodeRepository;
import com.swapnanil.apiservice.repository.TesterCodeRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProblemService {
    private final ProblemRepository problemRepository;
    private final TesterCodeRepository testerCodeRepository;
    private final TemplateCodeRepository templateCodeRepository;

    @Autowired
    public ProblemService(
            ProblemRepository problemRepository,
            TesterCodeRepository testerCodeRepository,
            TemplateCodeRepository templateCodeRepository
    ) {
        this.problemRepository = problemRepository;
        this.testerCodeRepository = testerCodeRepository;
        this.templateCodeRepository = templateCodeRepository;
    }

    public List<Problem> getAllProblems() {
        return problemRepository.findAll();
    }

    public Optional<Problem> getProblemById(String qid) {
        return problemRepository.findById(qid);
    }

    public Problem saveProblem(ProblemRequestDTO problemReq) {
        Problem problem = Problem.builder()
                .title(problemReq.getTitle())
                .prompt(problemReq.getPrompt())
                .difficulty(problemReq.getDifficulty())
                .sampleInputs(problemReq.getSampleInputs())
                .sampleOutputs(problemReq.getSampleOutputs())
                .hiddenInputs(problemReq.getHiddenInputs())
                .hiddenOutputs(problemReq.getHiddenOutputs())
                .build();
        problemRepository.save(problem);

        List<TesterCode> testerCodeList = new ArrayList<>();
        for (TesterCodeDTO testerCodeDTO : problemReq.getTesterCodeList()) {
            TesterCodeKey id = TesterCodeKey.builder()
                    .pid(problem.getId())
                    .lang(testerCodeDTO.getLang())
                    .build();
            testerCodeList.add(TesterCode.builder()
                    .id(id)
                    .problem(problem)
                    .code(testerCodeDTO.getCode())
                    .build());
        }
        problem.setTesterCodeList(testerCodeList);

        List<TemplateCode> templateCodeList = new ArrayList<>();
        for (TemplateCodeDTO templateCodeDTO : problemReq.getTemplateCodeList()) {
            TemplateCodeKey id = TemplateCodeKey.builder()
                    .pid(problem.getId())
                    .lang(templateCodeDTO.getLang())
                    .build();
            templateCodeList.add(TemplateCode.builder()
                    .id(id)
                    .problem(problem)
                    .code(templateCodeDTO.getCode())
                    .build());
        }
        problem.setTemplateCodeList(templateCodeList);

        problemRepository.save(problem);

        return problem;
    }

    @SneakyThrows
    public Problem updateProblem(Problem problem, ProblemRequestDTO problemReq) {
        problem.setTitle(problemReq.getTitle());
        problem.setPrompt(problemReq.getPrompt());
        problem.setDifficulty(problemReq.getDifficulty());
        problem.setSampleInputs(problemReq.getSampleInputs());
        problem.setSampleOutputs(problemReq.getSampleOutputs());
        problem.setHiddenInputs(problemReq.getHiddenInputs());
        problem.setHiddenOutputs(problemReq.getHiddenOutputs());

        Map<String, String> testerCodeDTOMap = new HashMap<>();
        Map<String, String> templateCodeDTOMap = new HashMap<>();
        for (TesterCodeDTO testerCodeDTO : problemReq.getTesterCodeList()) {
            testerCodeDTOMap.put(testerCodeDTO.getLang(), testerCodeDTO.getCode());
        }
        for (TemplateCodeDTO templateCodeDTO : problemReq.getTemplateCodeList()) {
            templateCodeDTOMap.put(templateCodeDTO.getLang(), templateCodeDTO.getCode());
        }

        for (TesterCode testerCode : problem.getTesterCodeList()) {
            testerCode.setCode(testerCodeDTOMap.get(testerCode.getId().getLang()));
        }
        for (TemplateCode templateCode : problem.getTemplateCodeList()) {
            templateCode.setCode(templateCodeDTOMap.get(templateCode.getId().getLang()));
        }

        problemRepository.save(problem);

        return problem;
    }

    public void deleteQuestionById(String pid) {
        problemRepository.deleteById(pid);
    }

}
