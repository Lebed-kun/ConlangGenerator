package test.java;

public class InvalidCommandException extends Exception {
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public InvalidCommandException(String command) {
        message = "\"" + command + "\" isn't valid command";
    }
}
