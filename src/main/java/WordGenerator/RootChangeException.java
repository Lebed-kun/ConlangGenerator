package main.java.WordGenerator;

public class RootChangeException extends Exception {
    // Values
    private String message;

    // Fields
    @Override
    public String getMessage() {
        return message;
    }

    // Constructors
    public RootChangeException() {
        message = "root of phonetic set can't be changed";
    }
}
