package com.swapnanil.apiservice.service;

import com.swapnanil.apiservice.model.Run;
import com.swapnanil.apiservice.repository.RunRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RunService {
    private final RunRepository runRepository;

    @Autowired
    public RunService(RunRepository runRepository) {
        this.runRepository = runRepository;
    }

    public Optional<Run> getRunById(String rid) {
        return runRepository.findById(rid);
    }

    public Run saveRun(Run run) {
        return runRepository.save(run);
    }
}
