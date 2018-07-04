package main.java.WordGenerator;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

public class PhoneticTreeSet implements Serializable {
    // Values
    private PhoneticCharacter rootChar;

    // Fields
    public PhoneticCharacter getRootChar() {
        return rootChar;
    }

    // Constructors
    public PhoneticTreeSet() {
        rootChar = new PhoneticCharacter();
    }

    // Methods
    public PhoneticCharacter findChar(String phChar) {
        HashSet<PhoneticCharacter> visited = new HashSet<>();

        LinkedList<PhoneticCharacter> queue = new LinkedList<>();

        visited.add(rootChar);
        queue.add(rootChar);

        while (queue.size() != 0) {
            PhoneticCharacter s = queue.poll();
            if (s.getPhChar().equals(phChar))
                return s;

            if (s.getPhSet() != null) {
                Iterator<PhoneticCharacter> i = s.getPhSet().iterator();
                while (i.hasNext()) {
                    PhoneticCharacter phCh = i.next();
                    if (!visited.contains(phCh)) {
                        visited.add(phCh);
                        queue.add(phCh);
                    }
                }
            }
        }

        return null;
    }

    private PhoneticCharacter findAncestorChar(PhoneticCharacter phChar) {
        HashSet<PhoneticCharacter> visited = new HashSet<>();

        LinkedList<PhoneticCharacter> queue = new LinkedList<>();

        visited.add(rootChar);
        queue.add(rootChar);

        while (queue.size() != 0) {
            PhoneticCharacter s = queue.poll();
            if (s.getPhSet() != null && s.getPhSet().contains(phChar)) // Error!
                return s;

            if (s.getPhSet() != null) {
                Iterator<PhoneticCharacter> i = s.getPhSet().iterator();
                while (i.hasNext()) {
                    PhoneticCharacter phCh = i.next();
                    if (!visited.contains(phCh)) {
                        visited.add(phCh);
                        queue.add(phCh);
                    }
                }
            }

        }

        return null;
    }

    public void setChar(String phChar, HashSet<PhoneticCharacter> phSet) throws RootChangeException {
        PhoneticCharacter phCh = findChar(phChar);

        if (phCh == null) {
            phCh = new PhoneticCharacter(phChar, phSet);
            if (rootChar.getPhSet() == null)
                rootChar.setPhSet(new HashSet<>());
            rootChar.getPhSet().add(phCh);
        } else if (!phCh.getPhChar().equals("*")){
            phCh.setPhSet(phSet);
        } else
            throw new RootChangeException();
    }

    public void addChar(String phChar) throws RootChangeException, AlreadyExistsException {
        PhoneticCharacter phCh = findChar(phChar);

        if (phCh == null) {
            phCh = new PhoneticCharacter(phChar);
            if (rootChar.getPhSet() == null)
                rootChar.setPhSet(new HashSet<>());
            rootChar.getPhSet().add(phCh);
        } else if (!phCh.getPhChar().equals("*"))
            throw new AlreadyExistsException(phChar);
        else
            throw new RootChangeException();
    }

    public void addChar(String phChar, HashSet<PhoneticCharacter> phSet) throws RootChangeException {
        PhoneticCharacter phCh = findChar(phChar);

        if (phCh == null) {
            phCh = new PhoneticCharacter(phChar, phSet);
            if (rootChar.getPhSet() == null)
                rootChar.setPhSet(new HashSet<>());
            rootChar.getPhSet().add(phCh);
        } else if (!phCh.getPhChar().equals("*")) {
            for (PhoneticCharacter ch : phSet) {
                PhoneticCharacter currentPhChar = findChar(ch.getPhChar());
                PhoneticCharacter currentAncPhChar = findAncestorChar(currentPhChar);

                if (currentAncPhChar == null)
                    throw new RootChangeException();
                if (currentPhChar == null)
                    phCh.getPhSet().add(ch);
                else {
                    currentAncPhChar.getPhSet().remove(currentPhChar);
                    phCh.getPhSet().add(currentPhChar);
                }

            }
        } else
            throw new RootChangeException();
    }

    public void delChar(String phChar) throws RootChangeException, NullPointerException {
        PhoneticCharacter phCh = findChar(phChar);
        PhoneticCharacter phAncChar = findAncestorChar(phCh);

        if (phCh == null)
            throw new NullPointerException();
        else if (phAncChar == null)
            throw new RootChangeException();
        else {
            if (phCh.getPhSet() != null)
                for (PhoneticCharacter ch : phCh.getPhSet()) {
                    phAncChar.getPhSet().add(ch);
                }
            phAncChar.getPhSet().remove(phCh);
        }

    }

    public void delChar(PhoneticCharacter phChar) throws RootChangeException, NullPointerException {
        PhoneticCharacter phAncChar = findAncestorChar(phChar);

        if (phChar == null)
            throw new NullPointerException();
        else if (phAncChar == null)
            throw new RootChangeException();
        else {
            if (phChar.getPhSet() != null)
                for (PhoneticCharacter ch : phChar.getPhSet()) {
                    phAncChar.getPhSet().add(ch);
                }
            phAncChar.getPhSet().remove(phChar);
        }
    }

}
