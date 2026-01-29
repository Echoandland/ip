import java.util.ArrayList;

/**
 * Represents a command to delete a task.
 */
public class DeleteCommand extends Command {
    private final int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public String execute(ArrayList<Task> tasks, Storage storage) {
        if (index < 0 || index >= tasks.size()) {
            return "Oops! That task number is out of range.";
        }
        Task removed = tasks.remove(index);
        storage.save(tasks);
        return "Noted. I've removed this task:\n  " + removed + "\nNow you have " + tasks.size() + " tasks in the list.";
    }
}
