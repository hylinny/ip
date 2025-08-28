package John.Tasks;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import John.Exceptions.JohnException;

/**
 * Base abstraction for all task types in John.
 * <p>
 * A Task has a textual {@code description} and a completion status
 * represented by {@code isDone}. Subclasses provide additional data
 * (e.g., dates) and must implement {@link #toFileString()} for
 * persistence.
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Creates a new task with the given description. Newly created tasks are not done.
     *
     * @param description human-readable description of the task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the status icon in bracket form, e.g., "[X]" for done or "[ ]" for not done.
     *
     * @return status icon string
     */
    public String getStatusIcon() {
        return "[" + (isDone ? "X" : " ") + "]";
    }

    /** Marks this task as completed. */
    public void markDone() {
        this.isDone = true;
    }

    /** Marks this task as not completed. */
    public void markUndone() {
        this.isDone = false;
    }

    /**
     * Serializes this task into a single-line file representation understood by {@code Storage}.
     *
     * @return string representation for persistence
     */
    public abstract String toFileString();

    @Override
    public String toString() {
        return getStatusIcon() + " " + description;
    }

    // Supported date/time formats for parsing user input and stored data.
    private static final DateTimeFormatter[] DATE_TIME_FORMATS = new DateTimeFormatter[] {
            DateTimeFormatter.ISO_LOCAL_DATE_TIME,            // 2019-12-02T18:00. Official format, also saved in local file.
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"),  // 2019-12-02 1800
            DateTimeFormatter.ofPattern("d/M/uuuu HHmm"),     // 2/12/2019 1800
    };

    private static final DateTimeFormatter[] DATE_ONLY_FORMATS = new DateTimeFormatter[] {
            DateTimeFormatter.ISO_LOCAL_DATE,               // 2019-12-02. Official format, also saved in local file.
            DateTimeFormatter.ofPattern("d/M/uuuu"),        // 2/12/2019
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),      // 2019-12-02
    };

    /**
     * Attempts to parse the provided date/time string into a {@link LocalDateTime}.
     *
     * @param dateTime the input string to parse
     * @return a parsed {@link LocalDateTime}
     * @throws JohnException if the string does not match any supported format
     */
    public LocalDateTime parseDateTime(String dateTime) throws JohnException {
        for (DateTimeFormatter f : DATE_TIME_FORMATS) {
            try {
                return LocalDateTime.parse(dateTime, f);
            }
            catch (DateTimeParseException ignore) {
            }
        }
        for (DateTimeFormatter f : DATE_ONLY_FORMATS) {
            try {
                return LocalDate.parse(dateTime, f).atStartOfDay();
            }
            catch (DateTimeParseException ignore) {
            }
        }

        throw new JohnException("Invalid date/time format provided.");
    }
}
