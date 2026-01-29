package chatbot;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class EventTest {

    @Test
    public void testEventCreation() {
        LocalDateTime from = LocalDateTime.of(2025, 2, 1, 14, 0);
        LocalDateTime to = LocalDateTime.of(2025, 2, 1, 16, 0);
        Event event = new Event("Project meeting", from, to);
        assertEquals("Project meeting", event.getDescription());
        assertEquals(from, event.getFrom());
        assertEquals(to, event.getTo());
        assertFalse(event.isDone());
    }

    @Test
    public void testEventToString() {
        LocalDateTime from = LocalDateTime.of(2025, 2, 1, 14, 0);
        LocalDateTime to = LocalDateTime.of(2025, 2, 1, 16, 0);
        Event event = new Event("Project meeting", from, to);
        String expected = "[E] [ ] Project meeting (from: Feb 1 2025 1400 to: Feb 1 2025 1600)";
        assertEquals(expected, event.toString());
        
        event.markDone();
        String expectedDone = "[E] [X] Project meeting (from: Feb 1 2025 1400 to: Feb 1 2025 1600)";
        assertEquals(expectedDone, event.toString());
    }
}
