package chatbot;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles loading and saving tasks to the hard disk.
 *
 * Level-7 requirement: tasks are persisted between runs.
 * This class is deliberately silent on errors so that
 * the user-facing behaviour (console output) remains
 * identical to earlier levels.
 */
public class Storage {
    private static final int MIN_PARTS_FOR_TASK = 3;
    private static final int MIN_PARTS_FOR_DEADLINE = 4;
    private static final int MIN_PARTS_FOR_EVENT = 5;
    private static final int INDEX_TYPE = 0;
    private static final int INDEX_DONE_FLAG = 1;
    private static final int INDEX_DESCRIPTION = 2;
    private static final int INDEX_DEADLINE_DATE = 3;
    private static final int INDEX_EVENT_FROM = 3;
    private static final int INDEX_EVENT_TO = 4;
    private static final String DONE_FLAG = "1";
    private static final String TYPE_TODO = "T";
    private static final String TYPE_DEADLINE = "D";
    private static final String TYPE_EVENT = "E";
    private static final String DELIMITER = " \\| ";
    private static final String STORAGE_DELIMITER = " | ";

    private final String filePath;

    /**
     * Constructs a Storage instance with the given file path.
     *
     * @param filePath the path to the storage file
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the storage file.
     * If the file (or its parent directory) does not exist
     * or cannot be read, an empty list is returned instead.
     */
    public ArrayList<Task> load() {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            // No previous data; start with an empty list.
            return tasks;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                Task task = parseTask(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
        } catch (FileNotFoundException e) {
            // Treat as no data; return whatever has been loaded so far.
            return new ArrayList<>();
        }

        return tasks;
    }

    /**
     * Saves the given list of tasks to the storage file.
     * Any existing content is overwritten.
     */
    public void save(ArrayList<Task> tasks) {
        File file = new File(filePath);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        try (FileWriter writer = new FileWriter(file)) {
            for (Task task : tasks) {
                String line = formatTask(task);
                writer.write(line);
                writer.write(System.lineSeparator());
            }
        } catch (IOException e) {
            // Intentionally ignore I/O errors to keep text UI unchanged.
        }
    }

    /**
     * Parses a single line from the storage file into a Task object.
     *
     * @param line the line to parse
     * @return the parsed Task, or null if parsing fails
     */
    /**
     * Parses a single line from the storage file into a Task object.
     *
     * @param line the line to parse
     * @return the parsed Task, or null if parsing fails
     */
    private Task parseTask(String line) {
        // Expected formats:
        // T | 1 | description
        // D | 0 | description | by
        // E | 1 | description | from | to
        String[] parts = line.split(DELIMITER);
        if (parts.length < MIN_PARTS_FOR_TASK) {
            return null;
        }

        String type = parts[INDEX_TYPE];
        String doneFlag = parts[INDEX_DONE_FLAG];
        String description = parts[INDEX_DESCRIPTION];

        Task task;
        switch (type) {
        case TYPE_TODO:
            task = new Todo(description);
            break;
        case TYPE_DEADLINE:
            if (parts.length < MIN_PARTS_FOR_DEADLINE) {
                return null;
            }
            String byString = parts[INDEX_DEADLINE_DATE];
            java.time.LocalDate by = DateTimeParser.parseDateFromStorage(byString);
            if (by == null) {
                // Fallback: try parsing as user input format for backward compatibility
                by = DateTimeParser.parseDate(byString);
            }
            if (by == null) {
                return null;
            }
            task = new Deadline(description, by);
            break;
        case TYPE_EVENT:
            if (parts.length < MIN_PARTS_FOR_EVENT) {
                return null;
            }
            String fromString = parts[INDEX_EVENT_FROM];
            String toString = parts[INDEX_EVENT_TO];
            java.time.LocalDateTime from = DateTimeParser.parseDateTimeFromStorage(fromString);
            java.time.LocalDateTime to = DateTimeParser.parseDateTimeFromStorage(toString);
            if (from == null || to == null) {
                // Fallback: try parsing as user input format for backward compatibility
                from = DateTimeParser.parseDateTime(fromString);
                to = DateTimeParser.parseDateTime(toString);
            }
            if (from == null || to == null) {
                return null;
            }
            task = new Event(description, from, to);
            break;
        default:
            return null;
        }

        if (DONE_FLAG.equals(doneFlag)) {
            task.markDone();
        }

        return task;
    }

    /**
     * Formats a Task object into a string for storage.
     *
     * @param task the task to format
     * @return the formatted string representation of the task
     */
    private String formatTask(Task task) {
        String doneFlag = task.isDone() ? DONE_FLAG : "0";

        if (task instanceof Todo) {
            return String.join(STORAGE_DELIMITER, TYPE_TODO, doneFlag, task.getDescription());
        } else if (task instanceof Deadline) {
            Deadline d = (Deadline) task;
            String byStorage = DateTimeParser.formatDateForStorage(d.getBy());
            return String.join(STORAGE_DELIMITER, TYPE_DEADLINE, doneFlag, d.getDescription(), byStorage);
        } else if (task instanceof Event) {
            Event e = (Event) task;
            String fromStorage = DateTimeParser.formatDateTimeForStorage(e.getFrom());
            String toStorage = DateTimeParser.formatDateTimeForStorage(e.getTo());
            return String.join(STORAGE_DELIMITER, TYPE_EVENT, doneFlag, e.getDescription(), fromStorage, toStorage);
        } else {
            // Fallback: store as a generic todo-like task.
            return String.join(STORAGE_DELIMITER, TYPE_TODO, doneFlag, task.getDescription());
        }
    }
}

