package chatbot;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TodoTest {

    @Test
    public void testTodoCreation() {
        Todo todo = new Todo("Read book");
        assertEquals("Read book", todo.getDescription());
        assertFalse(todo.isDone());
    }

    @Test
    public void testTodoToString() {
        Todo todo = new Todo("Read book");
        assertEquals("[T] [ ] Read book", todo.toString());
        
        todo.markDone();
        assertEquals("[T] [X] Read book", todo.toString());
    }
}
