import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

/**
 * Utility class for parsing and formatting dates and times.
 * Supports multiple input formats commonly used by users.
 */
public class DateTimeParser {

    // Common date formats
    private static final List<DateTimeFormatter> DATE_FORMATTERS = Arrays.asList(
        DateTimeFormatter.ofPattern("yyyy-MM-dd"),
        DateTimeFormatter.ofPattern("dd/MM/yyyy"),
        DateTimeFormatter.ofPattern("MM/dd/yyyy"),
        DateTimeFormatter.ofPattern("dd-MM-yyyy"),
        DateTimeFormatter.ofPattern("MMM dd yyyy"),
        DateTimeFormatter.ofPattern("MMM d yyyy"),
        DateTimeFormatter.ofPattern("d MMM yyyy"),
        DateTimeFormatter.ofPattern("dd MMM yyyy")
    );

    // Common date-time formats
    private static final List<DateTimeFormatter> DATETIME_FORMATTERS = Arrays.asList(
        DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"),
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"),
        DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm"),
        DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"),
        DateTimeFormatter.ofPattern("MMM dd yyyy HHmm"),
        DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm")
    );

    // Output formatter for dates
    private static final DateTimeFormatter OUTPUT_DATE_FORMATTER = 
        DateTimeFormatter.ofPattern("MMM d yyyy");

    // Output formatter for date-times
    private static final DateTimeFormatter OUTPUT_DATETIME_FORMATTER = 
        DateTimeFormatter.ofPattern("MMM d yyyy HHmm");

    /**
     * Parses a date string into a LocalDate.
     * Tries multiple formats until one succeeds.
     *
     * @param dateString the date string to parse
     * @return the parsed LocalDate, or null if parsing fails
     */
    public static LocalDate parseDate(String dateString) {
        if (dateString == null || dateString.trim().isEmpty()) {
            return null;
        }

        String trimmed = dateString.trim();

        // Try date formats first
        for (DateTimeFormatter formatter : DATE_FORMATTERS) {
            try {
                return LocalDate.parse(trimmed, formatter);
            } catch (DateTimeParseException e) {
                // Continue to next formatter
            }
        }

        // Try date-time formats and extract date
        for (DateTimeFormatter formatter : DATETIME_FORMATTERS) {
            try {
                LocalDateTime dateTime = LocalDateTime.parse(trimmed, formatter);
                return dateTime.toLocalDate();
            } catch (DateTimeParseException e) {
                // Continue to next formatter
            }
        }

        return null;
    }

    /**
     * Parses a date-time string into a LocalDateTime.
     * Tries multiple formats until one succeeds.
     *
     * @param dateTimeString the date-time string to parse
     * @return the parsed LocalDateTime, or null if parsing fails
     */
    public static LocalDateTime parseDateTime(String dateTimeString) {
        if (dateTimeString == null || dateTimeString.trim().isEmpty()) {
            return null;
        }

        String trimmed = dateTimeString.trim();

        // Try date-time formats first
        for (DateTimeFormatter formatter : DATETIME_FORMATTERS) {
            try {
                return LocalDateTime.parse(trimmed, formatter);
            } catch (DateTimeParseException e) {
                // Continue to next formatter
            }
        }

        // Try date formats and assume time is 00:00
        LocalDate date = parseDate(trimmed);
        if (date != null) {
            return date.atStartOfDay();
        }

        return null;
    }

    /**
     * Formats a LocalDate into a user-friendly string.
     *
     * @param date the date to format
     * @return formatted date string (e.g., "Feb 1 2025")
     */
    public static String formatDate(LocalDate date) {
        if (date == null) {
            return "";
        }
        return date.format(OUTPUT_DATE_FORMATTER);
    }

    /**
     * Formats a LocalDateTime into a user-friendly string.
     *
     * @param dateTime the date-time to format
     * @return formatted date-time string (e.g., "Feb 1 2025 1400")
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        return dateTime.format(OUTPUT_DATETIME_FORMATTER);
    }

    /**
     * Formats a LocalDate for storage (ISO format: yyyy-MM-dd).
     *
     * @param date the date to format
     * @return ISO formatted date string
     */
    public static String formatDateForStorage(LocalDate date) {
        if (date == null) {
            return "";
        }
        return date.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    /**
     * Formats a LocalDateTime for storage (ISO format: yyyy-MM-ddTHHmm).
     *
     * @param dateTime the date-time to format
     * @return ISO formatted date-time string
     */
    public static String formatDateTimeForStorage(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HHmm"));
    }

    /**
     * Parses a date string from storage format (ISO: yyyy-MM-dd).
     *
     * @param dateString the ISO formatted date string
     * @return the parsed LocalDate, or null if parsing fails
     */
    public static LocalDate parseDateFromStorage(String dateString) {
        if (dateString == null || dateString.trim().isEmpty()) {
            return null;
        }
        try {
            return LocalDate.parse(dateString.trim(), DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Parses a date-time string from storage format (ISO: yyyy-MM-ddTHHmm).
     *
     * @param dateTimeString the ISO formatted date-time string
     * @return the parsed LocalDateTime, or null if parsing fails
     */
    public static LocalDateTime parseDateTimeFromStorage(String dateTimeString) {
        if (dateTimeString == null || dateTimeString.trim().isEmpty()) {
            return null;
        }
        try {
            return LocalDateTime.parse(dateTimeString.trim(), 
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HHmm"));
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}
