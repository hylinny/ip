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
}
