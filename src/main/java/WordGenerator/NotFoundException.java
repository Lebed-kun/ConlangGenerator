package main.java.WordGenerator;

public class NotFoundException extends Exception {
    // Values
    private String message;

    // Fields
    @Override
    public String getMessage() {
        return message;
    }

    // Constructors
    public NotFoundException(String item) {
        message = item + " isn't found";
    }
}
