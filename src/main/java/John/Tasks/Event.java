package John.Tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import John.Exceptions.JohnException;

public class Event extends Task {
    protected LocalDateTime startDate;
    protected LocalDateTime endDate;

    public Event(String description, String startDate, String endDate) throws JohnException {
        super(description);
        this.startDate = this.parseDateTime(startDate);
        this.endDate = this.parseDateTime(endDate);
    }

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
