import java.util.ArrayList;

/**
 * Represents a command to unmark a task (mark as not done).
 */
public class UnmarkCommand extends Command {
    private final int index;

    public UnmarkCommand(int index) {
        this.index = index;
    }

    @Override
    public String execute(ArrayList<Task> tasks, Storage storage) {
        if (index < 0 || index >= tasks.size()) {
            return "Oops! That task number is out of range.";
        }
        tasks.get(index).unmarkDone();
        storage.save(tasks);
        return "OK, I've marked this task as not done yet:\n  " + tasks.get(index);
    }
}
