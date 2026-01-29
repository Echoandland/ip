package chatbot;

import java.util.ArrayList;

/**
 * Represents a command to list all tasks.
 */
public class ListCommand extends Command {
    private static final int DISPLAY_INDEX_OFFSET = 1;
    private static final String TASK_SEPARATOR = ". ";
    private static final String NEWLINE = "\n";

    @Override
    public String execute(ArrayList<Task> tasks, Storage storage) {
        if (tasks.isEmpty()) {
            return "No tasks yet.";
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            result.append(i + DISPLAY_INDEX_OFFSET).append(TASK_SEPARATOR).append(tasks.get(i));
            if (i < tasks.size() - DISPLAY_INDEX_OFFSET) {
                result.append(NEWLINE);
            }
        }
        return result.toString();
    }
}
