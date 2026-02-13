package chatbot;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Parses user input and returns the corresponding Command object.
 */
public class Parser {
    private static final int COMMAND_TODO_LENGTH = 5;
    private static final int COMMAND_DEADLINE_LENGTH = 9;
    private static final int COMMAND_EVENT_LENGTH = 6;
    private static final int COMMAND_MARK_LENGTH = 5;
    private static final int COMMAND_UNMARK_LENGTH = 7;
    private static final int COMMAND_DELETE_LENGTH = 7;
    private static final int COMMAND_FIND_LENGTH = 5;
    private static final int MIN_PARTS_FOR_DEADLINE = 4;
    private static final int MIN_PARTS_FOR_EVENT = 5;
    private static final int MIN_PARTS_FOR_TASK = 3;
    private static final int INDEX_OFFSET = 1;

    /**
     * Parses the user input and returns the appropriate Command.
     *
     * @param input the user input string
     * @return the Command object corresponding to the input
     */
    public static Command parse(String input) {
        assert input != null : "input must not be null";
        String trimmed = input.trim();

        if (trimmed.equalsIgnoreCase("bye")) {
            return new ByeCommand();
        }

        if (trimmed.equalsIgnoreCase("list")) {
            return new ListCommand();
        }

        if (trimmed.startsWith("find ")) {
            String keyword = trimmed.substring(COMMAND_FIND_LENGTH).trim();
            if (keyword.isEmpty()) {
                return new InvalidCommand("Oops! Please specify a keyword to search for. Usage: find <keyword>");
            }
            return new FindCommand(keyword);
        }

        if (trimmed.startsWith("todo ")) {
            String desc = trimmed.substring(COMMAND_TODO_LENGTH).trim();
            if (desc.isEmpty()) {
                return new InvalidCommand("Oops! Todo description cannot be empty.");
            }
            return new TodoCommand(desc);
        }

        if (trimmed.startsWith("deadline ")) {
            String rest = trimmed.substring(COMMAND_DEADLINE_LENGTH).trim();
            String[] parts = rest.split(" /by ", 2);
            if (parts.length < 2) {
                return new InvalidCommand("Oops! Please use the format: deadline <description> /by <date>");
            }
            String desc = parts[0].trim();
            String byString = parts[1].trim();
            LocalDate by = DateTimeParser.parseDate(byString);
            if (by == null) {
                return new InvalidCommand("Oops! Invalid date format. "
                        + "Please use formats like: 2025-02-01, Feb 1 2025, or 01/02/2025");
            }
            return new DeadlineCommand(desc, by);
        }

        if (trimmed.startsWith("event ")) {
            String rest = trimmed.substring(COMMAND_EVENT_LENGTH).trim();
            String[] descriptionAndTimes = rest.split(" /from ", 2);
            if (descriptionAndTimes.length < 2) {
                return new InvalidCommand("Oops! Please use the format: "
                        + "event <description> /from <date-time> /to <date-time>");
            }
            String desc = descriptionAndTimes[0].trim();
            String[] fromToParts = descriptionAndTimes[1].split(" /to ", 2);
            if (fromToParts.length < 2) {
                return new InvalidCommand("Oops! Please use the format: "
                        + "event <description> /from <date-time> /to <date-time>");
            }
            String fromString = fromToParts[0].trim();
            String toString = fromToParts[1].trim();
            LocalDateTime from = DateTimeParser.parseDateTime(fromString);
            LocalDateTime to = DateTimeParser.parseDateTime(toString);
            if (from == null || to == null) {
                return new InvalidCommand("Oops! Invalid date-time format. "
                        + "Please use formats like: 2025-02-01 1400, Feb 1 2025 2pm, or 01/02/2025 14:00");
            }
            return new EventCommand(desc, from, to);
        }

        if (trimmed.startsWith("mark ")) {
            String rest = trimmed.substring(COMMAND_MARK_LENGTH).trim();
            Integer index = parseTaskIndex(rest);
            if (index != null) {
                return new MarkCommand(index);
            }
            return new InvalidCommand("Oops! Task number must be an integer. Usage: mark <task number>");
        }

        if (trimmed.startsWith("mark")) {
            String[] parts = trimmed.split("\\s+");
            if (parts.length < 2) {
                return new InvalidCommand("Oops! Please specify which task to mark. Usage: mark <task number>");
            }
            Integer index = parseTaskIndex(parts[1]);
            if (index != null) {
                return new MarkCommand(index);
            }
            return new InvalidCommand("Oops! Task number must be an integer. Usage: mark <task number>");
        }

        if (trimmed.startsWith("unmark ")) {
            String rest = trimmed.substring(COMMAND_UNMARK_LENGTH).trim();
            Integer index = parseTaskIndex(rest);
            if (index != null) {
                return new UnmarkCommand(index);
            }
            return new InvalidCommand("Oops! Task number must be an integer. Usage: unmark <task number>");
        }

        if (trimmed.startsWith("delete ")) {
            String rest = trimmed.substring(COMMAND_DELETE_LENGTH).trim();
            Integer index = parseTaskIndex(rest);
            if (index != null) {
                return new DeleteCommand(index);
            }
            return new InvalidCommand("Oops! Task number must be an integer. Usage: delete <task number>");
        }

        // Fallback: treat as todo (maintains backward compatibility)
        if (!trimmed.isEmpty()) {
            return new FallbackTodoCommand(trimmed);
        }

        return new InvalidCommand("I don't understand that command. Please try again.");
    }

    /**
     * Parses a task number string into a 0-based index.
     *
     * @param taskNumberString the string after the command (e.g. "1" or "2")
     * @return the 0-based index, or null if the string is not a valid positive integer
     */
    private static Integer parseTaskIndex(String taskNumberString) {
        try {
            int taskNumber = Integer.parseInt(taskNumberString.trim());
            int index = taskNumber - INDEX_OFFSET;
            return index >= 0 ? index : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
