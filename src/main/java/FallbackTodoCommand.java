import java.util.ArrayList;

/**
 * Represents a fallback command that treats unrecognized input as a todo.
 * This maintains backward compatibility with the original behavior where
 * the input itself is also printed after creating the todo.
 */
public class FallbackTodoCommand extends Command {
    private final String input;

    public FallbackTodoCommand(String input) {
        this.input = input;
    }

    @Override
    public String execute(ArrayList<Task> tasks, Storage storage) {
        Task task = new Todo(input);
        tasks.add(task);
        storage.save(tasks);
        return "Added: " + task + "\n" + input;
    }
}
