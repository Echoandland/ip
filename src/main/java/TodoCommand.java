import java.util.ArrayList;

/**
 * Represents a command to add a todo task.
 */
public class TodoCommand extends Command {
    private final String description;

    public TodoCommand(String description) {
        this.description = description;
    }

    @Override
    public String execute(ArrayList<Task> tasks, Storage storage) {
        Task task = new Todo(description);
        tasks.add(task);
        storage.save(tasks);
        return "Added: " + task;
    }
}
