package chatbot;

import java.util.ArrayList;

/**
 * Represents a command to show the list of available commands.
 */
public class HelpCommand extends Command {
    private static final String HELP_MESSAGE = String.join("\n",
            "Available commands:",
            "  help",
            "  todo <description>",
            "  deadline <description> /by <date>",
            "  event <description> /from <date-time> /to <date-time>",
            "  dowithin <description> /from <date> /to <date>",
            "  list",
            "  mark <task number>",
            "  unmark <task number>",
            "  delete <task number>",
            "  find <keyword>",
            "  bye");

    @Override
    public String execute(ArrayList<Task> tasks, Storage storage) {
        return HELP_MESSAGE;
    }
}
