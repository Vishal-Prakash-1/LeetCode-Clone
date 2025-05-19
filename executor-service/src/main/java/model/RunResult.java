package model;

public class RunResult {
    public String status;
    public long exitCode;
    public String stdOut;
    public String stdErr;
    public String verdict;

    @Override
    public String toString() {
        return "RunResult{" +
                "status='" + status + '\'' +
                ", exitCode=" + exitCode +
                ", stdOut='" + stdOut + '\'' +
                ", stdErr='" + stdErr + '\'' +
                ", verdict='" + verdict + '\'' +
                '}';
    }
}
