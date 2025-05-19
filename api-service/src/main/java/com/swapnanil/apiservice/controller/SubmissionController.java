package com.swapnanil.apiservice.controller;

import com.swapnanil.apiservice.dto.SubmissionRequestDTO;
import com.swapnanil.apiservice.dto.TestSubmissionResponseDTO;
import com.swapnanil.apiservice.dto.SubmissionResponseDTO;
import com.swapnanil.apiservice.model.Problem;
import com.swapnanil.apiservice.model.Submission;
import com.swapnanil.apiservice.service.ProblemService;
import com.swapnanil.apiservice.service.SubmissionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/submission")
public class SubmissionController {
    private final SubmissionService submissionService;
    private final ProblemService problemService;

    @Autowired
    public SubmissionController(SubmissionService submissionService, ProblemService problemService) {
        this.submissionService = submissionService;
        this.problemService = problemService;
    }

    @GetMapping("/{sid}")
    public ResponseEntity<SubmissionResponseDTO> getSubmissionById(@PathVariable String sid) {
        Optional<Submission> submission = submissionService.getSubmissionById(sid);
        if (submission.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(SubmissionResponseDTO.from(submission.get()));
    }

    @PostMapping
    public ResponseEntity<SubmissionResponseDTO> submitSolution(
            @Valid @RequestBody SubmissionRequestDTO submissionReq
    ) {
        Optional<Problem> question = problemService.getProblemById(submissionReq.getPid());
        if (question.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        SubmissionResponseDTO submissionRes = submissionService.submit(question.get(), submissionReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(submissionRes);
    }

    @PostMapping("/test")
    public ResponseEntity<TestSubmissionResponseDTO> testSolution(
            @Valid @RequestBody SubmissionRequestDTO testSubmissionReq
    ) {
        Optional<Problem> question = problemService.getProblemById(testSubmissionReq.getPid());
        if (question.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        TestSubmissionResponseDTO testSubmissionRes = submissionService.submitTest(question.get(), testSubmissionReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(testSubmissionRes);
    }
}
