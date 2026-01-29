import java.util.ArrayList;

/**
 * Represents an invalid or unrecognized command.
 */
public class InvalidCommand extends Command {
    private final String errorMessage;

    public InvalidCommand(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String execute(ArrayList<Task> tasks, Storage storage) {
        return errorMessage;
    }
}
