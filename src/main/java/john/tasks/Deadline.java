package john.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import john.exceptions.JohnException;

/**
 * A task with a single deadline date/time.
 */
public class Deadline extends Task {
    protected LocalDateTime endDate;

    /**
     * Constructs a Deadline with the given description and deadline string.
     * The date/time string is parsed using {@link Task#parseDateTime(String)}.
     *
     * @param description description of the task
     * @param endDate     deadline (date/time) in a supported format
     * @throws JohnException if the date/time cannot be parsed
     */
    public Deadline(String description, String endDate) throws JohnException {
        super(description);
        this.endDate = this.parseDateTime(endDate);
    }

    /**
     * Returns the serialized representation of this deadline for storage.
     *
     * @return a one-line string suitable for persistence
     */
    public String toFileString() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + endDate;
    }

    @Override
    public String toString() {
        return "[D]"
                + super.toString()
                + " (by: "
                + endDate.format(DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm"))
                + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Deadline other)) {
            return false;
        }
        return Objects.equals(description, other.description)
                && Objects.equals(endDate, other.endDate)
                && isDone == other.isDone;
    }

    @Override
    public int hashCode() {
        // Purpose of overriding hashcode is to ensure that two equal objects have the same hashcode
        // (e.g. if they are added into a HashSet)
        return Objects.hash(description, endDate, isDone);
    }

}
