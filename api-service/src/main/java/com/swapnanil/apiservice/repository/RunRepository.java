package com.swapnanil.apiservice.repository;

import com.swapnanil.apiservice.model.Run;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RunRepository extends JpaRepository<Run, String> {
}
