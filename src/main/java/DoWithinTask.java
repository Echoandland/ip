package chatbot;

import java.time.LocalDate;

/**
 * Represents a task that should be done within a specific time period.
 * A DoWithinTask has a description, a start date, and an end date.
 */
public class DoWithinTask extends Task {
    private final LocalDate from;
    private final LocalDate to;

    /**
     * Constructs a DoWithinTask with the given description, start date, and end date.
     *
     * @param description the description of the task
     * @param from the start date of the period
     * @param to the end date of the period
     */
    public DoWithinTask(String description, LocalDate from, LocalDate to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the start date of the period.
     *
     * @return the start date
     */
    public LocalDate getFrom() {
        return from;
    }

    /**
     * Returns the end date of the period.
     *
     * @return the end date
     */
    public LocalDate getTo() {
        return to;
    }

    /**
     * Returns a string representation of this DoWithinTask.
     * The format is "[P] [X] description (within: start to end)" if done,
     * "[P] [ ] description (within: start to end)" if not done.
     *
     * @return a string representation of the task
     */
    @Override
    public String toString() {
        String formattedFrom = DateTimeParser.formatDate(from);
        String formattedTo = DateTimeParser.formatDate(to);
        return "[P] " + super.toString() + " (within: " + formattedFrom + " to " + formattedTo + ")";
    }
}
