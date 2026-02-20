package chatbot;

import java.util.ArrayList;

/**
 * Represents a command to add a todo task.
 */
public class TodoCommand extends Command {
    private final String description;

    /**
     * Constructs a TodoCommand with the given description.
     *
     * @param description the description of the todo task to add
     */
    public TodoCommand(String description) {
        this.description = description;
    }

    /**
     * Executes the command to add a todo task to the task list.
     *
     * @param tasks the list of tasks
     * @param storage the storage handler
     * @return a message confirming the task was added
     */
    @Override
    public String execute(ArrayList<Task> tasks, Storage storage) {
        Task task = new Todo(description);
        tasks.add(task);
        storage.save(tasks);
        return "Got it! I've added:\n  " + task;
    }
}
