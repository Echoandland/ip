package chatbot;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Utility class for parsing and formatting dates and times.
 * Supports multiple input formats commonly used by users.
 */
public class DateTimeParser {
    private static final Locale INPUT_LOCALE = Locale.ENGLISH;

    // Common date formats
    private static final List<DateTimeFormatter> DATE_FORMATTERS = Arrays.asList(
        createFormatter("uuuu-MM-dd"),
        createFormatter("dd/MM/uuuu"),
        createFormatter("MM/dd/uuuu"),
        createFormatter("dd-MM-uuuu"),
        createFormatter("MMM dd uuuu"),
        createFormatter("MMM d uuuu"),
        createFormatter("d MMM uuuu"),
        createFormatter("dd MMM uuuu")
    );

    // Common date-time formats
    private static final List<DateTimeFormatter> DATETIME_FORMATTERS = Arrays.asList(
        createFormatter("uuuu-MM-dd HHmm"),
        createFormatter("uuuu-MM-dd HH:mm"),
        createFormatter("dd/MM/uuuu HHmm"),
        createFormatter("dd/MM/uuuu HH:mm"),
        createFormatter("MMM dd uuuu HHmm"),
        createFormatter("MMM d uuuu HHmm"),
        createFormatter("MMM dd uuuu HH:mm"),
        createFormatter("MMM d uuuu HH:mm"),
        createFormatter("MMM dd uuuu h:mma"),
        createFormatter("MMM d uuuu h:mma"),
        createFormatter("MMM dd uuuu ha"),
        createFormatter("MMM d uuuu ha")
    );

    // Output formatter for dates
    private static final DateTimeFormatter OUTPUT_DATE_FORMATTER =
            DateTimeFormatter.ofPattern("MMM d uuuu", Locale.ENGLISH);

    // Output formatter for date-times
    private static final DateTimeFormatter OUTPUT_DATETIME_FORMATTER =
            DateTimeFormatter.ofPattern("MMM d uuuu HHmm", Locale.ENGLISH);

    private static DateTimeFormatter createFormatter(String pattern) {
        return new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern(pattern)
                .toFormatter(INPUT_LOCALE)
                .withResolverStyle(ResolverStyle.STRICT);
    }

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
