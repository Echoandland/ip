package chatbot;

import java.util.ArrayList;

/**
 * Represents a command to mark a task as done.
 */
public class MarkCommand extends Command {
    private final int index;

    /**
     * Constructs a MarkCommand with the given task index.
     *
     * @param index the index of the task to mark (0-based)
     */
    public MarkCommand(int index) {
        this.index = index;
    }

    /**
     * Executes the command to mark the specified task as done.
     *
     * @param tasks the list of tasks
     * @param storage the storage handler
     * @return a message confirming the task was marked, or an error message if index is invalid
     */
    @Override
    public String execute(ArrayList<Task> tasks, Storage storage) {
        assert tasks != null && storage != null : "tasks and storage must not be null";
        if (index < 0 || index >= tasks.size()) {
            return "Oops! That task number is out of range.";
        }
        tasks.get(index).markDone();
        storage.save(tasks);
        return "Nice! I've marked this task as done:\n  " + tasks.get(index);
    }
}
