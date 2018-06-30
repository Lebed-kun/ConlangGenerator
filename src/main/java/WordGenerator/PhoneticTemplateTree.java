package main.java.WordGenerator;

import java.util.Random;

public class PhoneticTemplateTree {
    // Values
    private TemplNode root;
    private PhoneticTreeSet phSet;

    // Fields

    public TemplNode getRoot() {
        return root;
    }

    public PhoneticTreeSet getPhSet() {
        return phSet;
    }

    // Constructors
    public PhoneticTemplateTree(PhoneticTreeSet phSet) {
        root = null;
        this.phSet = phSet;
    }

    // Methods for finding phonetic character
    public String makeWord() throws NullPointerException {
        return makeWord(root);
    }

    private String makeWord(TemplNode node) {
        String word = "";

        if (node.getLeft() != null && doGenerate(node.getLeft()))
            word += makeWord(node.getLeft());

        if (node.getPhoneme() != null && doGenerate(node)) {
            boolean generate = true;
            word += node.getRandomPhChar().getPhChar();
        }

        if (node.getRight() != null && doGenerate(node.getRight()))
            word += makeWord(node.getRight());

        return word;

    }

    private boolean doGenerate(TemplNode node) {
        if (node.isOptional())
            return new Random().nextBoolean();
        else
            return true;
    }


    // Methods for creating tree
    public void createTree(String template) throws SyntaxErrorException {
        root = createTree(template, 0, template.length()- 1);
    }

    private TemplNode createTree(String line, int begin, int end) throws SyntaxErrorException {
        if (end - begin < 0)
            throw new SyntaxErrorException(line);

        TemplNode node;

        if (isAlpha(line.charAt(begin))) {
            node = new TemplNode();
            PhoneticCharacter phCh = phSet.findChar("" + line.charAt(begin));

            if (isTailEmpty(begin, end)) {
                if (phCh != null)
                    node.setPhoneme(phCh);
                else
                    node.setPhoneme(new PhoneticCharacter("" + line.charAt(begin)));
            } else {
                TemplNode left = new TemplNode();
                if (phCh != null)
                    left.setPhoneme(phCh);
                else
                    left.setPhoneme(new PhoneticCharacter("" + line.charAt(begin)));

                TemplNode right = createTree(line, begin + 1, end);

                node.setLeft(left);
                node.setRight(right);
            }
        } else if (line.charAt(begin) == '(') {
            String head = "(";
            int balance = 1;
            int i = begin + 1;
            while (i <= end && balance != 0) {
                head += line.charAt(i);
                if (line.charAt(i) == '(')
                    balance++;
                else if (line.charAt(i) == ')')
                    balance--;
                i++;
            }

            if (balance != 0)
                throw new SyntaxErrorException(line);

            if (isTailEmpty(begin + head.length() - 1, end)) {
                node = createTree(head, 1, head.length() - 2);
                if (node != null)
                    node.setOptional(true);
            } else {
                node = new TemplNode();
                TemplNode left = createTree(head, 1, head.length() - 2);
                TemplNode right = createTree(line, begin + head.length(), end);

                left.setOptional(true);
                node.setLeft(left);
                node.setRight(right);
            }
        } else
            throw new SyntaxErrorException(line);

        return node;
    }

    private boolean isTailEmpty(int headEnd, int end)  {
        return headEnd >= end;
    }

    private boolean isAlpha(char ch) {
        return ch != ' ' && ch != '(' && ch != ')';
    }

}
