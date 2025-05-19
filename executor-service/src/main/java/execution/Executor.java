package execution;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import config.Constants;
import model.Run;
import model.RunRequest;
import model.RunResult;
import model.TesterCode;
import okhttp3.*;
import org.apache.commons.io.FileUtils;

import java.io.*;

public class Executor {
    private final ContainerService containerService;
    private final OkHttpClient httpClient;
    private final JsonAdapter<Run> runJsonAdapter;
    private final JsonAdapter<RunResult> runResultJsonAdapter;

    public Executor() {
        containerService = new ContainerService();
        httpClient = new OkHttpClient();

        Moshi moshi = new Moshi.Builder().build();
        runJsonAdapter = moshi.adapter(Run.class);
        runResultJsonAdapter = moshi.adapter(RunResult.class);
    }

    public void execute(RunRequest runRequest) throws ExecutorException {
        try {
            Run run = getRun(runRequest.rid);
            setupFiles(run);
            RunResult result = runCode(run);
            result.verdict = judgeCode(run, result);
            System.out.println(result);
            updateRun(run.id, result);
            cleanUp(run);
        } catch (Exception ex) {
            RunResult result = new RunResult();
            result.status = "Processing Failed";
            updateRun(runRequest.rid, result);
            throw new ExecutorException("Failed to execute run", ex);
        }
    }

    private void cleanUp(Run run) {
        File runDir = new File(Constants.RUN_DIR, run.id);
        FileUtils.deleteQuietly(runDir);
    }

    private void updateRun(String runId, RunResult result) throws ExecutorException {
        try {
            RequestBody body = RequestBody.create(
                    runResultJsonAdapter.toJson(result),
                    MediaType.get("application/json")
            );
            Request req = new Request.Builder()
                    .url(String.format("http://localhost:8080/run/%s", runId))
                    .method("PATCH", body)
                    .build();
            Response res =  httpClient.newCall(req).execute();
            res.close();
        } catch (IOException ex) {
            throw new ExecutorException("Failed to update run info", ex);
        }
    }

    private String judgeCode(Run run, RunResult result) throws ExecutorException {
        if (result.exitCode == 124) {
            return "Time Limit Exceeded";
        }
        if (result.exitCode == 137) {
            return "Memory Limit Exceeded";
        }
        if (result.exitCode != 0) {
            return "Error Occurred";
        }
        try {
            if (matchesOutput(run)) {
                return "Accepted";
            }
            return "Wrong Answer";
        } catch (ExecutorException ex) {
            throw new ExecutorException("Failed to judge code", ex);
        }
    }

    private boolean matchesOutput(Run run) throws ExecutorException {
        File runDir = new File(Constants.RUN_DIR, run.id);
        File outputFile = new File(runDir, "OUTPUT.txt");
        File expectedOutputFile = new File(runDir, "EXPECTED_OUTPUT.txt");

        try (
                BufferedReader outputReader = new BufferedReader(new FileReader(outputFile));
                BufferedReader expectedOutputReader = new BufferedReader(new FileReader(expectedOutputFile));
        ) {
            while (true) {
                String outputLine = outputReader.readLine();
                String expectedLine = expectedOutputReader.readLine();

                if (outputLine == null) {
                    break;
                }
                if (expectedLine == null) {
                    return false;
                }
                if (!outputLine.equals(expectedLine)) {
                    System.out.printf("Output: %s%n", outputLine);
                    System.out.printf("Expected: %s%n", expectedLine);
                    return false;
                }
            }

            return true;
        } catch (Exception ex) {
            throw new ExecutorException("Failed to validate output", ex);
        }
    }

    private RunResult runCode(Run run) throws ExecutorException {
        try {
            RunResult result = new RunResult();
            File mountDir = new File(Constants.RUN_DIR, run.id);

            String containerId = containerService.startContainer(mountDir.getAbsolutePath(), run.lang);
            containerService.waitForContainer(containerId);
            String[] logs = containerService.getContainerLogs(containerId);
            long exitCode = containerService.getExitCode(containerId);
            containerService.removeContainer(containerId);

            result.status = "Done";
            result.exitCode = exitCode;
            result.stdOut = logs[0];
            result.stdErr = logs[1];
            return result;
        } catch (ContainerServiceException ex) {
            throw new ExecutorException("Failed to run code", ex);
        }
    }

    private void setupFiles(Run run) throws ExecutorException {
        File runDir = new File(Constants.RUN_DIR, run.id);
        runDir.mkdir();

        setupCodeFiles(runDir, run);
        setupInputFile(runDir, run);
        setupOutputFile(runDir, run);
    }

    private void setupOutputFile(File runDir, Run run) throws ExecutorException {
        try {
            File outputFile = new File(runDir, "EXPECTED_OUTPUT.txt");
            try (PrintWriter outputWriter = new PrintWriter(outputFile)) {
                if (run.type.equals("Submission")) {
                    outputWriter.println(run.problem.hiddenOutputs);
                } else {
                    outputWriter.println(run.problem.sampleOutputs);
                }
            }
        } catch (Exception ex) {
            throw new ExecutorException("Failed to setup output file", ex);
        }
    }

    private void setupInputFile(File runDir, Run run) throws ExecutorException {
        try {
            File inputFile = new File(runDir, "INPUT.txt");
            try (PrintWriter inputWriter = new PrintWriter(inputFile)) {
                if (run.type.equals("Submission")) {
                    inputWriter.println(run.problem.hiddenInputs);
                } else {
                    inputWriter.println(run.problem.sampleInputs);
                }
            }
        } catch (Exception ex) {
            throw new ExecutorException("Failed to setup input file", ex);
        }
    }

    private void setupCodeFiles(File runDir, Run run) throws ExecutorException {
        try {
            String codeFileExtension = getTesterCodeFileExtension(run.lang);

            String testerCodeFilename = String.format("Main%s", codeFileExtension);
            File testerCodeFile = new File(runDir, testerCodeFilename);
            try (PrintWriter testerCodeWriter = new PrintWriter(testerCodeFile)) {
                testerCodeWriter.println(getTesterCode(run));
            }

            String codeFilename = String.format("Solution%s", codeFileExtension);
            File codeFile = new File(runDir, codeFilename);
            try (PrintWriter codeWriter = new PrintWriter(codeFile)) {
                codeWriter.println(run.code);
            }
        } catch (Exception ex) {
            throw new ExecutorException("Failed to setup code file", ex);
        }
    }

    private String getTesterCode(Run run) throws ExecutorException {
        for (TesterCode testerCode : run.problem.testerCodeList) {
            if (testerCode.lang.equals(run.lang)) {
                return testerCode.code;
            }
        }

        throw new ExecutorException("Tester code not found for run language");
    }

    private String getTesterCodeFileExtension(String lang) throws ExecutorException {
        return switch (lang) {
            case "java" -> ".java";
            case "python" -> ".py";
            default -> throw new ExecutorException("Unsupported code language");
        };
    }

    private Run getRun(String runId) throws ExecutorException {
        try {
            Request req = new Request.Builder()
                    .url(String.format("http://localhost:8080/run/%s", runId))
                    .build();
            Response res = httpClient.newCall(req).execute();
            Run run = runJsonAdapter.fromJson(res.body().source());
            res.close();
            return run;
        } catch (IOException ex) {
            throw new ExecutorException("Failed to fetch run info", ex);
        }
    }
}
