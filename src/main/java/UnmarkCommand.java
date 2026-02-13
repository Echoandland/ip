package chatbot;

import java.util.ArrayList;

/**
 * Represents a command to unmark a task (mark as not done).
 */
public class UnmarkCommand extends Command {
    private final int index;

    /**
     * Constructs an UnmarkCommand with the given task index.
     *
     * @param index the index of the task to unmark (0-based)
     */
    public UnmarkCommand(int index) {
        this.index = index;
    }

    /**
     * Executes the command to mark the specified task as not done.
     *
     * @param tasks the list of tasks
     * @param storage the storage handler
     * @return a message confirming the task was unmarked, or an error message if index is invalid
     */
    @Override
    public String execute(ArrayList<Task> tasks, Storage storage) {
        assert tasks != null && storage != null : "tasks and storage must not be null";
        if (index < 0 || index >= tasks.size()) {
            return "Oops! That task number is out of range.";
        }
        tasks.get(index).unmarkDone();
        storage.save(tasks);
        return "OK, I've marked this task as not done yet:\n  " + tasks.get(index);
    }
}
