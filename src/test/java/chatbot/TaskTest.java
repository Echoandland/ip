package chatbot;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaskTest {

    @Test
    public void testTaskCreation() {
        Task task = new Task("Test task");
        assertEquals("Test task", task.getDescription());
        assertFalse(task.isDone());
    }

    @Test
    public void testMarkDone() {
        Task task = new Task("Test task");
        assertFalse(task.isDone());
        task.markDone();
        assertTrue(task.isDone());
    }

    @Test
    public void testUnmarkDone() {
        Task task = new Task("Test task");
        task.markDone();
        assertTrue(task.isDone());
        task.unmarkDone();
        assertFalse(task.isDone());
    }

    @Test
    public void testToString() {
        Task task = new Task("Test task");
        assertEquals("[ ] Test task", task.toString());
        
        task.markDone();
        assertEquals("[X] Test task", task.toString());
    }
}
