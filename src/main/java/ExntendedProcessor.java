package main.java;

import main.java.WordGenerator.*;

import java.util.HashMap;

public class ExntendedProcessor extends Processor {
    // Values
    private HashMap<String, PhoneticTemplateTree> templates;

    // Fields
    public HashMap<String, PhoneticTemplateTree> getTemplates() {
        return templates;
    }

    // Constructors
    public ExntendedProcessor() {
        super();
        templates = new HashMap<>();
    }

    // Methods
    @Override
    public String proceed(String[] args) throws InvalidArgumentException, AlreadyExistsException, NotFoundException {
        String res = super.proceed(args);
        if (res.equals("@unknown_command")) {
            switch (args[0]) {
                case "add_template":
                    if (args.length < 2)
                        throw new InvalidArgumentException(args[0], "2 arguments at least");

                    addTemplates(args);

                    res = "@add_template_succ";
                    break;

                case "del_template":
                    if (args.length < 2)
                        throw new InvalidArgumentException(args[0], "2 arguments at least");

                    delTemplates(args);

                    res = "@del_template_succ";
                    break;

                case "help":
                    printHelp();
                    res = "@help_succ";
                    break;

                    default:
                        res = "@unknown_command";
                        break;
            }
        }

        return res;
    }

    @Override
    protected String makeWord(String phoneticTemplate) {
        templExisted(phoneticTemplate);

        String word = "";
        try {
            word = templates.get(phoneticTemplate).makeWord();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return word;
    }

    // Tool methods
    private boolean templExisted(String phoneticTemplate) {

        if (!templates.containsKey(phoneticTemplate)) {
            PhoneticTemplateTree templateTree = new PhoneticTemplateTree(this.getPhoneticSet());
            try {
                templateTree.createTree(phoneticTemplate);
                templates.put(phoneticTemplate, templateTree);
            } catch (SyntaxErrorException e) {
                e.printStackTrace();
            }
            return false;
        }

        return true;
    }

    protected void printHelp() {
        System.out.println("\"set_phoneme <wildcard> <phoneme2> [phoneme3] ... [phonemeN]\" - sets phonemes to wildcard");
        System.out.println("\"add_phoneme <phoneme1> [phoneme2] ... [phonemeN]\" - adds <phoneme1> to a list (or adds [phoneme2] ... [phonemeN] to making <phoneme1> a wildcard)");
        System.out.println("\"del_phoneme <phoneme1> [phoneme2] ... [phonemeN]\" - deletes <phoneme1> [phoneme2] ... [phonemeN] from a list");
        System.out.println("\"add_template\" <template1> [template2] ... [templateN] - adds phonetic templates of syllables/words to a list");
        System.out.println("\"del_template\" <template1> [template2] ... [templateN] - deletes phonetic templates of syllables/words to a list");
        System.out.println("\"gen_words <template> <amount> - generate words by template");
        System.out.println("\"exit\" - exit from a program");
    }

    protected void addTemplates(String[] args) throws AlreadyExistsException {
        for (int i = 1; i < args.length; i++) {
            if (templExisted(args[i]))
                throw new AlreadyExistsException(args[1]);
        }
    }

    protected void delTemplates(String[] args) throws NotFoundException {
        for (int i = 1; i < args.length; i++) {
            if (!templates.containsKey(args[i]))
                throw new NotFoundException(args[i]);
            templates.remove(args[i]);
        }
    }


}
