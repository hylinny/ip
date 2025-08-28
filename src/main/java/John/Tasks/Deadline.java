package John.Tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import John.Exceptions.JohnException;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Deadline other)) return false;
        return Objects.equals(description, other.description)
                && Objects.equals(endDate, other.endDate)
                && isDone == other.isDone;
    }

    @Override
    public int hashCode() {
        // Purpose of overriding hashcode is to ensure that two equal objects have the same hashcode (e.g. if they are added into a HashSet)
        return Objects.hash(description, endDate, isDone);
    }

}
