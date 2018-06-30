package main.java;

public class InputTranslator {
    // Methods
    public String[] getArguments(String input) {
        String[] args = input.split("[ |]");
        return args;
    }
}
