package chatbot;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Represents a command to list all tasks.
 */
public class ListCommand extends Command {
    private static final int DISPLAY_INDEX_OFFSET = 1;
    private static final String TASK_SEPARATOR = ". ";
    private static final String EMPTY_LIST_MESSAGE = "No tasks yet.";

    @Override
    public String execute(ArrayList<Task> tasks, Storage storage) {
        assert tasks != null && storage != null : "tasks and storage must not be null";
        if (tasks.isEmpty()) {
            return EMPTY_LIST_MESSAGE;
        }
        return IntStream.range(0, tasks.size())
                .mapToObj(i -> (i + DISPLAY_INDEX_OFFSET) + TASK_SEPARATOR + tasks.get(i))
                .collect(Collectors.joining("\n"));
    }
}
