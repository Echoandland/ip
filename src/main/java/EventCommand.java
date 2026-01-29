import java.util.ArrayList;
import java.time.LocalDateTime;

/**
 * Represents a command to add an event task.
 */
public class EventCommand extends Command {
    private final String description;
    private final LocalDateTime from;
    private final LocalDateTime to;

    public EventCommand(String description, LocalDateTime from, LocalDateTime to) {
        this.description = description;
        this.from = from;
        this.to = to;
    }

    @Override
    public String execute(ArrayList<Task> tasks, Storage storage) {
        Task task = new Event(description, from, to);
        tasks.add(task);
        storage.save(tasks);
        return "Added: " + task;
    }
}
