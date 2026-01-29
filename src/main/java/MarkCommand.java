import java.util.ArrayList;

/**
 * Represents a command to mark a task as done.
 */
public class MarkCommand extends Command {
    private final int index;

    public MarkCommand(int index) {
        this.index = index;
    }

    @Override
    public String execute(ArrayList<Task> tasks, Storage storage) {
        if (index < 0 || index >= tasks.size()) {
            return "Oops! That task number is out of range.";
        }
        tasks.get(index).markDone();
        storage.save(tasks);
        return "Nice! I've marked this task as done:\n  " + tasks.get(index);
    }
}
