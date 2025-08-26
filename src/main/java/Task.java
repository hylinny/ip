import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public abstract class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return "[" + (isDone ? "X" : " ") + "]";
    }

    public void markDone() {
        this.isDone = true;
    }

    public void markUndone() {
        this.isDone = false;
    }

    public abstract String toFileString();

    @Override
    public String toString() {
        return getStatusIcon() + " " + description;
    }

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
