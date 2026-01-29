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

    private final String filePath;

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

    private Task parseTask(String line) {
        // Expected formats:
        // T | 1 | description
        // D | 0 | description | by
        // E | 1 | description | from | to
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            return null;
        }

        String type = parts[0];
        String doneFlag = parts[1];
        String description = parts[2];

        Task task;
        switch (type) {
        case "T":
            task = new Todo(description);
            break;
        case "D":
            if (parts.length < 4) {
                return null;
            }
            String by = parts[3];
            task = new Deadline(description, by);
            break;
        case "E":
            if (parts.length < 5) {
                return null;
            }
            String from = parts[3];
            String to = parts[4];
            task = new Event(description, from, to);
            break;
        default:
            return null;
        }

        if ("1".equals(doneFlag)) {
            task.markDone();
        }

        return task;
    }

    private String formatTask(Task task) {
        String doneFlag = task.isDone() ? "1" : "0";

        if (task instanceof Todo) {
            return String.join(" | ", "T", doneFlag, task.getDescription());
        } else if (task instanceof Deadline) {
            Deadline d = (Deadline) task;
            return String.join(" | ", "D", doneFlag, d.getDescription(), d.getBy());
        } else if (task instanceof Event) {
            Event e = (Event) task;
            return String.join(" | ", "E", doneFlag, e.getDescription(), e.getFrom(), e.getTo());
        } else {
            // Fallback: store as a generic todo-like task.
            return String.join(" | ", "T", doneFlag, task.getDescription());
        }
    }
}

