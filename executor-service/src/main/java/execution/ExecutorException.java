package execution;

public class ExecutorException extends Exception {
    public ExecutorException() {
    }

    public ExecutorException(String message) {
        super(message);
    }

    public ExecutorException(String message, Throwable cause) {
        super(message, cause);
    }
}
