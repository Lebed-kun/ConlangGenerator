package main.java.WordGenerator;

public class InvalidArgumentException extends Exception {
    // Values
    private String message;

    // Fieilds
    @Override
    public String getMessage() {
        return message;
    }

    // Constructors
    public InvalidArgumentException(String command, String info) {
        message += command + " takes " + info;
    }
}
