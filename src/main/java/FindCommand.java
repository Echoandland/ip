package chatbot;

import java.util.ArrayList;

/**
 * Represents a command to find tasks matching a keyword.
 */
public class FindCommand extends Command {
    private static final String EMPTY_RESULT_MESSAGE = "No matching tasks found.";
    private static final String TASK_SEPARATOR = ". ";
    private static final String NEWLINE = "\n";

    private final String keyword;

    /**
     * Constructs a FindCommand with the given keyword.
     *
     * @param keyword the keyword to search for
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Executes the command to find tasks containing the keyword.
     *
     * @param tasks the list of tasks
     * @param storage the storage handler (not used)
     * @return a message listing all matching tasks, or a message indicating no matches found
     */
    @Override
    public String execute(ArrayList<Task> tasks, Storage storage) {
        assert tasks != null && storage != null : "tasks and storage must not be null";
        ArrayList<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                matchingTasks.add(task);
            }
        }

        if (matchingTasks.isEmpty()) {
            return EMPTY_RESULT_MESSAGE;
        }

        StringBuilder result = new StringBuilder();
        result.append("Here are the matching tasks in your list:");
        for (int i = 0; i < matchingTasks.size(); i++) {
            result.append(NEWLINE);
            result.append(i + 1).append(TASK_SEPARATOR).append(matchingTasks.get(i));
        }
        return result.toString();
    }
}
