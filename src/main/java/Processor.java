package main.java;

import main.java.WordGenerator.*;
import javafx.util.Pair;

import java.util.HashSet;

public class Processor {
    // Values
    private PhoneticTreeSet phoneticSet;
    private String[] words;

    // Fields
    public PhoneticTreeSet getPhoneticSet() {
        return phoneticSet;
    }

    public String[] getWords() {
        return words;
    }

    // Constructors
    public Processor() {
        phoneticSet = new PhoneticTreeSet();
        words = null;
    }

    // Method for choosing command
    public String proceed(String[] args) throws InvalidArgumentException, AlreadyExistsException, NotFoundException {
        String res = "";

        switch (args[0]) {
            case "set_phoneme":
                if (args.length >= 3) {
                    Pair<String, HashSet<PhoneticCharacter>> data = getDataForTreeOps(args);
                    setChar(data.getKey(), data.getValue());
                    res = "@set_phoneme_succ";
                } else {
                    throw new InvalidArgumentException(args[0], "2 arguments");
                }
                break;

            case "add_phoneme":
                if (args.length == 2) {
                    Pair<String, HashSet<PhoneticCharacter>> data = getDataForTreeOps(args);
                    addChar(data.getKey());
                    res = "@add_phoneme_succ";
                } else if (args.length >= 3) {
                    Pair<String, HashSet<PhoneticCharacter>> data = getDataForTreeOps(args);
                    addChar(data.getKey(), data.getValue());
                    res = "@add_phoneme_succ";
                } else
                    throw new InvalidArgumentException(args[0], "1-2 arguments");
                break;

            case "del_phoneme":
                if (args.length == 2) {
                    Pair<String, HashSet<PhoneticCharacter>> data = getDataForTreeOps(args);
                    delChar(data.getKey());
                    res = "@del_phoneme_succ";
                } else if (args.length >= 3) {
                    Pair<String, HashSet<PhoneticCharacter>> data = getDataForTreeOps(args);
                    delChar(data.getKey(), data.getValue());
                    res = "@del_phoneme_succ";
                } else
                    throw new InvalidArgumentException(args[0], "1-2 arguments");
                break;

            case "gen_words":
                if (args.length == 3) {
                    Pair<String, Integer> data = getDataForGenWords(args);
                    genWords(data.getKey(), data.getValue());
                    res = "@gen_words_succ";
                } else
                    throw new InvalidArgumentException(args[0], "2 arguments");
                break;

            case "exit":
                if (args.length == 1) {
                    Program.isWorking = false;
                    res += "@exit_succ";
                }
                else
                    throw new InvalidArgumentException(args[0], "no arguments");
                break;

                default:
                    res += "@unknown_command";
                    break;

        }

        return res;
    }

    // Helpful methods for preparing data
    private Pair<String, HashSet<PhoneticCharacter>> getDataForTreeOps(String[] args) {
        String phChar = args[1];
        HashSet<PhoneticCharacter> phSet = new HashSet<>();
        for (int i = 2; i < args.length; i++) {
            phSet.add(new PhoneticCharacter(args[i]));
        }

        return new Pair<>(phChar, phSet);
    }

    private Pair<String, Integer> getDataForGenWords(String[] args) {
        String phoneticTemplate = args[1];
        Integer count = Integer.parseInt(args[2]);

        return new Pair<>(phoneticTemplate, count);
    }


    // Methods for changing phonetic set
    protected void setChar(String phChar, HashSet<PhoneticCharacter> phSet) {
        try {
            phoneticSet.setChar(phChar, phSet);
        } catch (RootChangeException e) {
            e.printStackTrace();
        }
    }

    protected void addChar(String phChar) {
        try {
            phoneticSet.addChar(phChar);
        } catch (RootChangeException e) {
            e.printStackTrace();
        } catch (AlreadyExistsException e) {
            e.printStackTrace();
        }
    }

    protected void addChar(String phChar, HashSet<PhoneticCharacter> phSet) {
        try {
            phoneticSet.addChar(phChar, phSet);
        } catch (RootChangeException e) {
            e.printStackTrace();
        }
    }

    protected void delChar(String phChar) {
        try {
            phoneticSet.delChar(phChar);
        } catch (RootChangeException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    protected void delChar(String phChar, HashSet<PhoneticCharacter> phSet) {
        try {
            phoneticSet.delChar(phChar);
            for (PhoneticCharacter phCh : phSet) {
                phoneticSet.delChar(phCh);
            }
        }
         catch (NullPointerException e) {
            e.printStackTrace();
        } catch (RootChangeException e) {
            e.printStackTrace();
        }
    }

    // Methods for generating words
    protected String makeWord(String phoneticTemplate) {
        String res = "";

        for (int i = 0; i < phoneticTemplate.length(); i++) {
            PhoneticCharacter ch = phoneticSet.findChar(phoneticTemplate.charAt(i) + "");

            if (ch == null)
                res += phoneticTemplate.charAt(i);
            else {
                while (ch.getPhSet() != null)
                    ch = ch.getRandomPhChar();
                res += ch.getPhChar();
            }
        }

        return res;
    }

    protected void genWords(String phoneticTemplate, int count) throws InvalidArgumentException {
        if (count <= 0)
            throw new InvalidArgumentException("gen_words", "positive integer to amount of words");

        words = new String[count];

        for (int i = 0; i < count; i++) {
            words[i] = makeWord(phoneticTemplate);
        }
    }
}
