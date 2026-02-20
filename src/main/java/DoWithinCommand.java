package chatbot;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Represents a command to add a DoWithinTask (a task to be done within a period).
 */
public class DoWithinCommand extends Command {
    private static final String SUCCESS_PREFIX = "Done! I've added this task:\n  ";
    private static final String TASK_COUNT_FORMAT = "\nNow you have %d tasks in the list.";

    private final String description;
    private final LocalDate from;
    private final LocalDate to;

    /**
     * Constructs a DoWithinCommand with the given description, start date, and end date.
     *
     * @param description the description of the task
     * @param from the start date of the period
     * @param to the end date of the period
     */
    public DoWithinCommand(String description, LocalDate from, LocalDate to) {
        this.description = description;
        this.from = from;
        this.to = to;
    }

    /**
     * Executes the command to add a DoWithinTask to the task list.
     *
     * @param tasks the list of tasks
     * @param storage the storage handler
     * @return a message confirming the task was added
     */
    @Override
    public String execute(ArrayList<Task> tasks, Storage storage) {
        DoWithinTask task = new DoWithinTask(description, from, to);
        tasks.add(task);
        storage.save(tasks);
        return SUCCESS_PREFIX + task + String.format(TASK_COUNT_FORMAT, tasks.size());
    }
}
