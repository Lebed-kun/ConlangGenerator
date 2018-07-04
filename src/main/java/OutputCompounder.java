package main.java;

public class OutputCompounder {
    // Values
    private String output;

    // Constructors
    public OutputCompounder() {
        output = "";
    }

    // Fields
    public String getOutput() {
        return output;
    }

    public void setOutput(String state, Processor proc) {
        output = "";
        switch (state) {
            case "@set_phoneme_succ":
                setPhoneme();
                break;

            case "@add_phoneme_succ":
                addPhoneme();
                break;

            case "@del_phoneme_succ":
                delPhoneme();
                break;

            case "@gen_words_succ":
                genWords(proc);
                break;

            case "@exit_succ":
                exit();
                break;

            case "@add_template_succ":
                addTemplate();
                break;

            case "@del_template_succ":
                delTemplate();
                break;

            case "@unknown_command":
                System.out.println("Unknown command");
                break;

            case "@save_state_succ":
                saveValues();
                break;

            case "@load_state_succ":
                loadValues();
                break;

            case "@save_words_succ":
                saveWords();
                break;
        }
    }

    protected void setPhoneme() {
        output += "Phoneme set\n";
    }

    protected void addPhoneme() {
        output += "Phonemes added\n";
    }

    protected void delPhoneme() {
        output += "Phonemes removed\n";
    }

    protected void genWords(Processor proc) {
        output += "Generated words:\n";
        for (int i = 0; i < proc.getWords().length; i++) {
            output += proc.getWords()[i];
            if (i % 10 < 9)
                output += " ";
            else
                output += "\n";
        }
    }

    protected void exit() {
        output += "Good bye :3";
    }

    protected void addTemplate() {
        output += "Phonemic template added\n";
    }

    protected void delTemplate() {
        output += "Phonemic template deleted\n";
    }

    protected void saveValues() {
        output += "Phonetic set and templates saved\n";
    }

    protected  void loadValues() {
        output += "Phonetic set and templated loaded\n";
    }

    protected void saveWords() {
        output += "Words saved\n";
    }
}
