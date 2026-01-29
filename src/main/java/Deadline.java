import java.time.LocalDate;

public class Deadline extends Task {
    private final LocalDate by;

    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    public LocalDate getBy() {
        return by;
    }

    @Override
    public String toString() {
        String formattedDate = DateTimeParser.formatDate(by);
        return "[D] " + super.toString() + " (by: " + formattedDate + ")";
    }
}
