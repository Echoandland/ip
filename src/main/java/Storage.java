package chatbot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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
    private static final int MIN_PARTS_FOR_DOWITHIN = 5;
    private static final int INDEX_TYPE = 0;
    private static final int INDEX_DONE_FLAG = 1;
    private static final int INDEX_DESCRIPTION = 2;
    private static final int INDEX_DEADLINE_DATE = 3;
    private static final int INDEX_EVENT_FROM = 3;
    private static final int INDEX_EVENT_TO = 4;
    private static final int INDEX_DOWITHIN_FROM = 3;
    private static final int INDEX_DOWITHIN_TO = 4;
    private static final String DONE_FLAG = "1";
    private static final String UNDONE_FLAG = "0";
    private static final String TYPE_TODO = "T";
    private static final String TYPE_DEADLINE = "D";
    private static final String TYPE_EVENT = "E";
    private static final String TYPE_DOWITHIN = "P";
    private static final String DELIMITER = " \\| ";
    private static final String STORAGE_DELIMITER = " | ";

    private final String filePath;

    /**
     * Constructs a Storage instance with the given file path.
     *
     * @param filePath the path to the storage file
     */
    public Storage(String filePath) {
        assert filePath != null && !filePath.isEmpty() : "filePath must not be null or empty";
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
     * Expected formats: T|1|desc ; D|0|desc|by ; E|1|desc|from|to ; P|0|desc|from|to
     *
     * @param line the line to parse
     * @return the parsed Task, or null if parsing fails
     */
    private Task parseTask(String line) {
        String[] parts = line.split(DELIMITER);
        if (parts.length < MIN_PARTS_FOR_TASK) {
            return null;
        }
        String type = parts[INDEX_TYPE];
        String doneFlag = parts[INDEX_DONE_FLAG];
        String description = parts[INDEX_DESCRIPTION];

        Task task = parseTaskByType(type, parts, description);
        if (task != null && DONE_FLAG.equals(doneFlag)) {
            task.markDone();
        }
        return task;
    }

    /**
     * Parses a task from storage parts by type (T/D/E/P).
     */
    private Task parseTaskByType(String type, String[] parts, String description) {
        switch (type) {
        case TYPE_TODO:
            return new Todo(description);
        case TYPE_DEADLINE:
            return parseDeadlineFromParts(parts, description);
        case TYPE_EVENT:
            return parseEventFromParts(parts, description);
        case TYPE_DOWITHIN:
            return parseDoWithinFromParts(parts, description);
        default:
            return null;
        }
    }

    private Task parseDeadlineFromParts(String[] parts, String description) {
        if (parts.length < MIN_PARTS_FOR_DEADLINE) {
            return null;
        }
        String byString = parts[INDEX_DEADLINE_DATE];
        java.time.LocalDate by = DateTimeParser.parseDateFromStorage(byString);
        if (by == null) {
            by = DateTimeParser.parseDate(byString);
        }
        return by != null ? new Deadline(description, by) : null;
    }

    private Task parseEventFromParts(String[] parts, String description) {
        if (parts.length < MIN_PARTS_FOR_EVENT) {
            return null;
        }
        String fromString = parts[INDEX_EVENT_FROM];
        String toString = parts[INDEX_EVENT_TO];
        java.time.LocalDateTime from = DateTimeParser.parseDateTimeFromStorage(fromString);
        java.time.LocalDateTime to = DateTimeParser.parseDateTimeFromStorage(toString);
        if (from == null || to == null) {
            from = DateTimeParser.parseDateTime(fromString);
            to = DateTimeParser.parseDateTime(toString);
        }
        return (from != null && to != null) ? new Event(description, from, to) : null;
    }

    private Task parseDoWithinFromParts(String[] parts, String description) {
        if (parts.length < MIN_PARTS_FOR_DOWITHIN) {
            return null;
        }
        String fromStr = parts[INDEX_DOWITHIN_FROM];
        String toStr = parts[INDEX_DOWITHIN_TO];
        java.time.LocalDate from = DateTimeParser.parseDateFromStorage(fromStr);
        java.time.LocalDate to = DateTimeParser.parseDateFromStorage(toStr);
        if (from == null || to == null) {
            from = DateTimeParser.parseDate(fromStr);
            to = DateTimeParser.parseDate(toStr);
        }
        return (from != null && to != null) ? new DoWithinTask(description, from, to) : null;
    }

    /**
     * Formats a Task object into a string for storage.
     *
     * @param task the task to format
     * @return the formatted string representation of the task
     */
    private String formatTask(Task task) {
        String doneFlag = task.isDone() ? DONE_FLAG : UNDONE_FLAG;

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
        } else if (task instanceof DoWithinTask) {
            DoWithinTask p = (DoWithinTask) task;
            String fromStorage = DateTimeParser.formatDateForStorage(p.getFrom());
            String toStorage = DateTimeParser.formatDateForStorage(p.getTo());
            return String.join(STORAGE_DELIMITER, TYPE_DOWITHIN, doneFlag, p.getDescription(), fromStorage, toStorage);
        } else {
            // Fallback: store as a generic todo-like task.
            return String.join(STORAGE_DELIMITER, TYPE_TODO, doneFlag, task.getDescription());
        }
    }
}

