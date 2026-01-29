import java.util.ArrayList;

/**
 * Represents a command to list all tasks.
 */
public class ListCommand extends Command {
    @Override
    public String execute(ArrayList<Task> tasks, Storage storage) {
        if (tasks.isEmpty()) {
            return "No tasks yet.";
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            result.append((i + 1)).append(". ").append(tasks.get(i));
            if (i < tasks.size() - 1) {
                result.append("\n");
            }
        }
        return result.toString();
    }
}
