package main.java.WordGenerator;

public class AlreadyExistsException extends  Exception {
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public AlreadyExistsException(String item) {
        message = item + " already exists";
    }
}
