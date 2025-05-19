package com.swapnanil.apiservice.repository;

import com.swapnanil.apiservice.model.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, String> {
}
