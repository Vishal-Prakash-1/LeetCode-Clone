package com.swapnanil.apiservice.controller;

import com.swapnanil.apiservice.dto.RunRequestDTO;
import com.swapnanil.apiservice.dto.RunResponseDTO;
import com.swapnanil.apiservice.model.Run;
import com.swapnanil.apiservice.service.RunService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/run")
public class RunController {
    private final RunService runService;

    @Autowired
    public RunController(RunService runService) {
        this.runService = runService;
    }

    @GetMapping("/{rid}")
    public ResponseEntity<RunResponseDTO> getRunById(@PathVariable String rid) {
        Optional<Run> run = runService.getRunById(rid);
        if (run.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(RunResponseDTO.from(run.get()));
    }

    @PatchMapping("/{rid}")
    public ResponseEntity<RunResponseDTO> updateRun(@PathVariable String rid, @Valid @RequestBody RunRequestDTO runReq) {
        Run run = runService.getRunById(rid).orElse(null);
        if (run == null) {
            return ResponseEntity.notFound().build();
        }

        run.setStatus(runReq.getStatus());
        run.setExitCode(runReq.getExitCode());
        run.setStdOut(runReq.getStdOut());
        run.setStdErr(runReq.getStdErr());
        run.setVerdict(runReq.getVerdict());
        runService.saveRun(run);

        return ResponseEntity.ok(RunResponseDTO.from(run));
    }
}
