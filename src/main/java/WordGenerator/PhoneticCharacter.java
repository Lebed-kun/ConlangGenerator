package main.java.WordGenerator;


import java.util.HashSet;
import java.util.Random;

public class PhoneticCharacter {
    // Values
    private String phChar;
    private HashSet<PhoneticCharacter> phSet;

    // Fields
    public String getPhChar() {
        return phChar;
    }

    public HashSet<PhoneticCharacter> getPhSet() {
        return phSet;
    }

    public void setPhSet(HashSet<PhoneticCharacter> phSet) {
        this.phSet = phSet;
    }

    // Constructors
    public PhoneticCharacter() {
        phChar = "*";
        phSet = null;
    }

    public PhoneticCharacter(String phChar) {
        this.phChar = phChar;
        phSet = null;
    }

    public PhoneticCharacter(String phChar, HashSet<PhoneticCharacter> phSet) {
        this.phChar = phChar;
        this.phSet = phSet;
    }

    // Methods
    public PhoneticCharacter getRandomPhChar() {
        int size = phSet.size();
        int item = new Random().nextInt(size);
        int i = 0;
        for (PhoneticCharacter phCh : phSet) {
            if (i == item)
                return phCh;
            i++;
        }
        return null;
    }
}
