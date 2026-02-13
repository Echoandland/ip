package chatbot;

import java.util.ArrayList;

/**
 * Represents a command to delete a task.
 */
public class DeleteCommand extends Command {
    private final int index;

    /**
     * Constructs a DeleteCommand with the given task index.
     *
     * @param index the index of the task to delete (0-based)
     */
    public DeleteCommand(int index) {
        this.index = index;
    }

    /**
     * Executes the command to delete the specified task from the task list.
     *
     * @param tasks the list of tasks
     * @param storage the storage handler
     * @return a message confirming the task was deleted, or an error message if index is invalid
     */
    @Override
    public String execute(ArrayList<Task> tasks, Storage storage) {
        assert tasks != null && storage != null : "tasks and storage must not be null";
        if (index < 0 || index >= tasks.size()) {
            return "Oops! That task number is out of range.";
        }
        Task removed = tasks.remove(index);
        storage.save(tasks);
        return "Noted. I've removed this task:\n  " + removed
                + "\nNow you have " + tasks.size() + " tasks in the list.";
    }
}
