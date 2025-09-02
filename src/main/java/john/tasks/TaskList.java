package john.tasks;

import java.util.ArrayList;
import java.util.List;

import john.exceptions.JohnException;

/**
 * Mutable collection of {@link Task} objects with basic list operations.
 */
public class TaskList {
    private List<Task> tasks;
    private int size;

    /**
     * Creates an empty task list.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
        size = 0;
    }

    /**
     * Creates a task list initialized with the given tasks.
     *
     * @param tasks initial tasks to include
     */
    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
        size = tasks.size();
    }

    /**
     * Appends a task to the end of the list.
     *
     * @param task task to add
     */
    public void addTask(Task task) {
        tasks.add(task);
        size++;
    }

    /**
     * Removes the task at the specified zero-based index.
     *
     * @param index zero-based index of the task to remove
     * @throws JohnException if the index is out of bounds
     */
    public void deleteTask(int index) throws JohnException {
        if (index < 0 || index >= size) {
            throw new JohnException("Task index out of bounds.");
        }
        tasks.remove(index);
        size--;
    }

    /**
     * Prints all tasks with one-based numbering to standard output.
     * Intended for a simple text UI.
     */
    public void printTasks() {
        for (int i = 0; i < size; i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }

    /**
     * Retrieves the task at the specified zero-based index.
     *
     * @param index zero-based index
     * @return the task at the given index
     * @throws JohnException if the index is out of bounds
     */
    public Task getTask(int index) throws JohnException {
        if (index < 0 || index >= size) {
            throw new JohnException("Task index out of bounds.");
        }
        return tasks.get(index);
    }

    /**
     * Returns the number of tasks in this list.
     *
     * @return size of the list
     */
    public int getSize() {
        return size;
    }

    /**
     * Returns the task list.
     *
     * @return internal list of tasks
     */
    public List<Task> getTasks() {
        return tasks;
    }
}
