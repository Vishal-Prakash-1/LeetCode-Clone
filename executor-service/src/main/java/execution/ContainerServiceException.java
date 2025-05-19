package execution;

public class ContainerServiceException extends Exception {
    public ContainerServiceException() {
    }

    public ContainerServiceException(String message) {
        super(message);
    }

    public ContainerServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
