public class TaskList {
    private Task[] tasks;
    private int size;

    public TaskList(int capacity) {
        tasks = new Task[capacity];
        size = 0;
    }

    public void addTask(Task task) {
        tasks[size++] = task;
    }

    public void printTasks() {
        for (int i = 0; i < size; i++) {
            System.out.println((i + 1) + ". " + tasks[i]);
        }
    }

    public Task getTask(int index) {
        if (index < 0 || index >= size) {
            System.out.println("Invalid task index.");
            return null;
        }
        return tasks[index];
    }

    public int getSize() {
        return size;
    }
}
