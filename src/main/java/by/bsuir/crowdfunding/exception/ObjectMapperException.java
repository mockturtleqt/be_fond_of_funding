package by.bsuir.crowdfunding.exception;

public class ObjectMapperException extends RuntimeException {

    public ObjectMapperException() {
    }

    public ObjectMapperException(String message) {
        super(message);
    }

    public ObjectMapperException(String message, Throwable cause) {
        super(message, cause);
    }

    public ObjectMapperException(Throwable cause) {
        super(cause);
    }
}
