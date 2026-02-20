package chatbot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class DoWithinTest {

    @Test
    public void testDoWithinCreation() {
        LocalDate from = LocalDate.of(2025, 2, 1);
        LocalDate to = LocalDate.of(2025, 2, 28);
        DoWithinTask task = new DoWithinTask("Complete project", from, to);
        assertEquals("Complete project", task.getDescription());
        assertEquals(from, task.getFrom());
        assertEquals(to, task.getTo());
        assertFalse(task.isDone());
    }

    @Test
    public void testDoWithinToString() {
        LocalDate from = LocalDate.of(2025, 2, 1);
        LocalDate to = LocalDate.of(2025, 2, 28);
        DoWithinTask task = new DoWithinTask("Complete project", from, to);
        String expected = "[P] [ ] Complete project (within: Feb 1 2025 to Feb 28 2025)";
        assertEquals(expected, task.toString());

        task.markDone();
        String expectedDone = "[P] [X] Complete project (within: Feb 1 2025 to Feb 28 2025)";
        assertEquals(expectedDone, task.toString());
    }

    @Test
    public void testDoWithinSameDay() {
        LocalDate date = LocalDate.of(2025, 2, 15);
        DoWithinTask task = new DoWithinTask("Submit report", date, date);
        assertEquals(date, task.getFrom());
        assertEquals(date, task.getTo());
        String expected = "[P] [ ] Submit report (within: Feb 15 2025 to Feb 15 2025)";
        assertEquals(expected, task.toString());
    }
}
