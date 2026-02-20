package chatbot;

import java.util.ArrayList;

/**
 * Represents a command to exit the application.
 */
public class ByeCommand extends Command {
    @Override
    public String execute(ArrayList<Task> tasks, Storage storage) {
        storage.save(tasks);
        return "See you later! 👋 Take care and stay productive!";
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
