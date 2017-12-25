package by.bsuir.crowdfunding.exception;

public class UnknownDataJobTypeException extends Exception {

    public UnknownDataJobTypeException() {
        super();
    }

    public UnknownDataJobTypeException(String name) {
        super(name);
    }
}
