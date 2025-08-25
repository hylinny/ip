import static java.lang.System.exit;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TaskList {
    private List<Task> tasks;
    private int size;
    private static final String filePath = "./data/john.txt";

    public TaskList() {
        this.tasks = TaskList.load();
        size = tasks.size();
    }

    public static ArrayList<Task> load() {
        File f = new File(TaskList.filePath);
        ArrayList<Task> tasks = new ArrayList<>();

        try {
            if (!f.exists()) {
                f.getParentFile().mkdirs();
                f.createNewFile();
                return tasks;
            }

            Scanner s = new Scanner(f);
            while (s.hasNextLine()) {
                String line = s.nextLine();
                try {
                    Task task = parseTask(line);
                    tasks.add(task);
                } catch (JohnException e) {
                    System.err.println("Skipped corrupted line: " + e.getMessage()); // skip corrupted line
                }
            }
            s.close();
            return tasks;
        } catch (IOException e) {
            System.err.println("File not found: " + e.getMessage());
            exit(1);
        }
        return tasks;
    }

    private static Task parseTask(String line) throws JohnException {
        String[] parts = line.split("\\s*\\|\\s*");
        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];
        Task task;
        switch (type) {
        case "T":
            task = new Todo(description);
            break;
        case "D":
            String date = parts[3];
            task = new Deadline(description, date);
            break;
        case "E":
            String startDate = parts[3];
            String endDate = parts[4];
            task = new Event(description, startDate, endDate);
            break;
        default:
            throw new JohnException("Unknown task type in file: " + type);
        }
        if (isDone) {
            task.markDone();
        }
        return task;
    }

    public void save() {
        try {
            FileWriter fw = new FileWriter(TaskList.filePath);
            for (Task task : tasks) {
                fw.write(task.toFileString() + System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            System.err.println("Error saving tasks: " + e.getMessage());
        }
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
}
