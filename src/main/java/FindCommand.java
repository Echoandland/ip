package chatbot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Represents a command to find tasks matching a keyword.
 */
public class FindCommand extends Command {
    private static final String EMPTY_RESULT_MESSAGE = "No matching tasks found.";
    private static final String TASK_SEPARATOR = ". ";
    private static final String HEADER = "Here are the matching tasks in your list:";

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
        String lowerKeyword = keyword.toLowerCase();
        List<Task> matchingTasks = tasks.stream()
                .filter(task -> task.getDescription().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());

        if (matchingTasks.isEmpty()) {
            return EMPTY_RESULT_MESSAGE;
        }

        String numberedList = IntStream.range(0, matchingTasks.size())
                .mapToObj(i -> (i + 1) + TASK_SEPARATOR + matchingTasks.get(i))
                .collect(Collectors.joining("\n"));
        return HEADER + "\n" + numberedList;
    }
}
