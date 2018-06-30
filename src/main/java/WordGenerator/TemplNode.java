package main.java.WordGenerator;

public class TemplNode {
    // Values
    private PhoneticCharacter phoneme;
    private boolean optional;
    private TemplNode left;
    private TemplNode right;

    // Fields

    public PhoneticCharacter getPhoneme() {
        return phoneme;
    }

    public void setPhoneme(PhoneticCharacter phoneme) {
        this.phoneme = phoneme;
    }

    public boolean isOptional() {
        return optional;
    }

    public void setOptional(boolean optional) {
        this.optional = optional;
    }

    public TemplNode getLeft() {
        return left;
    }

    public void setLeft(TemplNode left) {
        this.left = left;
    }

    public TemplNode getRight() {
        return right;
    }

    public void setRight(TemplNode right) {
        this.right = right;
    }

    // Constructors
    public TemplNode() {
        phoneme = null;
        optional = false;
        left = null;
        right = null;
    }

    // Methods
    public PhoneticCharacter getRandomPhChar() {
        PhoneticCharacter phChar = phoneme;
        while (phChar.getPhSet() != null)
            phChar = phChar.getRandomPhChar();
        return phChar;
    }
}
