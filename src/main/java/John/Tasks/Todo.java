package John.Tasks;

/**
 * A simple task without any date/time attached.
 */
public class Todo extends Task {
    /**
     * Constructs a Todo with the given description.
     *
     * @param description description of the todo item
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns the serialized representation of this todo for storage.
     *
     * @return a one-line string suitable for persistence
     */
    public String toFileString() {
        return "T | " + (isDone ? "1" : "0") + " | " + description;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
