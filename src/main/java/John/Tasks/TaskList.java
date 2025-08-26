package John.Tasks;

import java.util.ArrayList;
import java.util.List;

import John.Exceptions.JohnException;

public class TaskList {
    private List<Task> tasks;
    private int size;

    public TaskList() {
        this.tasks = new ArrayList<>();
        size = 0;
    }

    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
        size = tasks.size();
    }

    public void addTask(Task task) {
        tasks.add(task);
        size++;
    }

    public void deleteTask(int index) throws JohnException {
        if (index < 0 || index >= size) {
            throw new JohnException("Task index out of bounds.");
        }
        tasks.remove(index);
        size--;
    }

    public void printTasks() {
        for (int i = 0; i < size; i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }

    public Task getTask(int index) throws JohnException {
        if (index < 0 || index >= size) {
            throw new JohnException("Task index out of bounds.");
        }
        return tasks.get(index);
    }

    public int getSize() {
        return size;
    }

    public List<Task> getTasks() {
        return tasks;
    }
}
