public class TaskList {
    private Task[] tasks;
    private int size;

    public TaskList(int capacity) {
        tasks = new Task[capacity];
        size = 0;
    }

    public void addTask(Task task) throws JohnException {
        if (size >= tasks.length) {
            throw new JohnException("Task list is full. Cannot add more tasks.");
        }
        tasks[size++] = task;
    }

    public void printTasks() {
        for (int i = 0; i < size; i++) {
            System.out.println((i + 1) + ". " + tasks[i]);
        }
    }

    public Task getTask(int index) throws JohnException {
        if (index < 0 || index >= size) {
            throw new JohnException("Task index out of bounds.");
        }
        return tasks[index];
    }

    public int getSize() {
        return size;
    }
}
