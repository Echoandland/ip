package chatbot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class DateTimeParserTest {

    @Test
    public void testParseDateIsoFormat() {
        LocalDate result = DateTimeParser.parseDate("2025-02-20");
        assertEquals(LocalDate.of(2025, 2, 20), result);
    }

    @Test
    public void testParseDateSlashFormat() {
        LocalDate result = DateTimeParser.parseDate("20/02/2025");
        assertEquals(LocalDate.of(2025, 2, 20), result);
    }

    @Test
    public void testParseDateTextFormat() {
        LocalDate result = DateTimeParser.parseDate("Feb 20 2025");
        assertEquals(LocalDate.of(2025, 2, 20), result);
    }

    @Test
    public void testParseDateNull() {
        assertNull(DateTimeParser.parseDate(null));
        assertNull(DateTimeParser.parseDate(""));
        assertNull(DateTimeParser.parseDate("   "));
    }

    @Test
    public void testParseDateInvalid() {
        assertNull(DateTimeParser.parseDate("invalid"));
        assertNull(DateTimeParser.parseDate("32/13/2025"));
    }

    @Test
    public void testFormatDate() {
        LocalDate date = LocalDate.of(2025, 2, 20);
        assertEquals("Feb 20 2025", DateTimeParser.formatDate(date));
    }

    @Test
    public void testFormatDateForStorage() {
        LocalDate date = LocalDate.of(2025, 2, 20);
        assertEquals("2025-02-20", DateTimeParser.formatDateForStorage(date));
    }

    @Test
    public void testParseDateTime() {
        LocalDateTime result = DateTimeParser.parseDateTime("2025-02-20 1400");
        assertEquals(LocalDateTime.of(2025, 2, 20, 14, 0), result);
    }

    @Test
    public void testParseDateFromStorage() {
        LocalDate result = DateTimeParser.parseDateFromStorage("2025-02-20");
        assertEquals(LocalDate.of(2025, 2, 20), result);
    }

    @Test
    public void testParseDateFromStorageInvalid() {
        assertNull(DateTimeParser.parseDateFromStorage("invalid"));
        assertNull(DateTimeParser.parseDateFromStorage(null));
    }
}
