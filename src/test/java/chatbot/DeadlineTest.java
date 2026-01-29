package chatbot;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class DeadlineTest {

    @Test
    public void testDeadlineCreation() {
        LocalDate date = LocalDate.of(2025, 2, 1);
        Deadline deadline = new Deadline("Return book", date);
        assertEquals("Return book", deadline.getDescription());
        assertEquals(date, deadline.getBy());
        assertFalse(deadline.isDone());
    }

    @Test
    public void testDeadlineToString() {
        LocalDate date = LocalDate.of(2025, 2, 1);
        Deadline deadline = new Deadline("Return book", date);
        String expected = "[D] [ ] Return book (by: Feb 1 2025)";
        assertEquals(expected, deadline.toString());
        
        deadline.markDone();
        String expectedDone = "[D] [X] Return book (by: Feb 1 2025)";
        assertEquals(expectedDone, deadline.toString());
    }
}
