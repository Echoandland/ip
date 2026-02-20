package chatbot;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ParserTest {

    @Test
    public void testParseBye() {
        Command cmd = Parser.parse("bye");
        assertTrue(cmd instanceof ByeCommand);
        assertTrue(cmd.isExit());
    }

    @Test
    public void testParseList() {
        Command cmd = Parser.parse("list");
        assertTrue(cmd instanceof ListCommand);
    }

    @Test
    public void testParseTodo() {
        Command cmd = Parser.parse("todo read book");
        assertTrue(cmd instanceof TodoCommand);
    }

    @Test
    public void testParseInvalidCommand() {
        Command cmd = Parser.parse("");
        assertTrue(cmd instanceof InvalidCommand);
    }

    @Test
    public void testParseFind() {
        Command cmd = Parser.parse("find meeting");
        assertTrue(cmd instanceof FindCommand);
    }

    @Test
    public void testParseFallbackTodo() {
        Command cmd = Parser.parse("some random text");
        assertTrue(cmd instanceof FallbackTodoCommand);
    }

    @Test
    public void testParseMark() {
        Command cmd = Parser.parse("mark 1");
        assertTrue(cmd instanceof MarkCommand);
    }

    @Test
    public void testParseDelete() {
        Command cmd = Parser.parse("delete 1");
        assertTrue(cmd instanceof DeleteCommand);
    }
}
