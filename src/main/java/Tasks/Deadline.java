package Tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import Exceptions.JohnException;

public class Deadline extends Task {
    protected LocalDateTime endDate;

    public Deadline(String description, String endDate) throws JohnException {
        super(description);
        this.endDate = this.parseDateTime(endDate);
    }

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
}
