package John.Tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import John.Exceptions.JohnException;

/**
 * A task representing an event that spans a start and end date/time.
 */
public class Event extends Task {
    protected LocalDateTime startDate;
    protected LocalDateTime endDate;

    /**
     * Constructs an Event with the given description, start and end date/time strings.
     * The date/time strings are parsed using {@link Task#parseDateTime(String)}.
     *
     * @param description description of the event
     * @param startDate   start date/time in a supported format
     * @param endDate     end date/time in a supported format
     * @throws JohnException if either date/time cannot be parsed
     */
    public Event(String description, String startDate, String endDate) throws JohnException {
        super(description);
        this.startDate = this.parseDateTime(startDate);
        this.endDate = this.parseDateTime(endDate);
    }

    /**
     * Returns the serialized representation of this event for storage.
     *
     * @return a one-line string suitable for persistence
     */
    public String toFileString() {
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | " + startDate + " | " + endDate;
    }

    @Override
    public String toString() {
        return "[E]"
                + super.toString()
                + " (from: "
                + startDate.format(DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm"))
                + " to: "
                + endDate.format(DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm"))
                + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event other)) return false;
        return description.equals(other.description)
                && startDate.equals(other.startDate)
                && endDate.equals(other.endDate)
                && isDone == other.isDone;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(description, startDate, endDate, isDone);
    }
}
