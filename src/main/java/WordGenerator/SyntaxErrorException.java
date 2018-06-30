package main.java.WordGenerator;

public class SyntaxErrorException extends Exception {
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public SyntaxErrorException(String line) {
        message = "invalid format of string at \"" + line + "\"";
    }
}
