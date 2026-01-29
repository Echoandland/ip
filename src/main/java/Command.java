import java.util.ArrayList;

/**
 * Represents a command that can be executed.
 * This is an abstract base class for all commands in the application.
 */
public abstract class Command {
    /**
     * Executes the command and returns a result message.
     *
     * @param tasks the list of tasks
     * @param storage the storage handler
     * @return the result message after executing the command
     */
    public abstract String execute(ArrayList<Task> tasks, Storage storage);

    /**
     * Indicates whether this command should cause the program to exit.
     *
     * @return true if the program should exit after this command, false otherwise
     */
    public boolean isExit() {
        return false;
    }
}
