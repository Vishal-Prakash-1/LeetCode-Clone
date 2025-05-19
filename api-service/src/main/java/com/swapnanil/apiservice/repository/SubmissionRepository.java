package com.swapnanil.apiservice.repository;

import com.swapnanil.apiservice.model.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission, String> {
    List<Submission> findByProblemId(String pid);
}
