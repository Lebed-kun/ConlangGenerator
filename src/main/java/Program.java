package main.java;

import main.java.WordGenerator.AlreadyExistsException;
import main.java.WordGenerator.InvalidArgumentException;
import main.java.WordGenerator.NotFoundException;

import java.util.Scanner;

public class Program {

    public static boolean isWorking;

    public static void main(String[] args) {
        InputTranslator inputTranslator = new InputTranslator();
        ExntendedProcessor processor = new ExntendedProcessor();
        OutputCompounder outputCompounder = new OutputCompounder();

        isWorking = true;

        Scanner inp = new Scanner(System.in);

        while (isWorking) {
            System.out.print(">> ");
            String input = inp.nextLine();
            String[] arguments = inputTranslator.getArguments(input);
            try {
                String state = processor.proceed(arguments);
                outputCompounder.setOutput(state, processor);
                System.out.println(outputCompounder.getOutput());
            } catch (InvalidArgumentException e) {
                e.printStackTrace();
            } catch (AlreadyExistsException e) {
                e.printStackTrace();
            } catch (NotFoundException e) {
                e.printStackTrace();
            }

            int k = 0; // test
        }
    }

}
