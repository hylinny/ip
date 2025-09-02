package john.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import john.exceptions.JohnException;
import john.tasks.Deadline;
import john.tasks.Event;
import john.tasks.Task;
import john.tasks.TaskList;
import john.tasks.Todo;

/**
 * Handles persistence of tasks to and from a flat file on disk.
 * <p>
 * The storage format is one line per task using pipe-separated fields, e.g.:
 * <pre>
 * T | 0 | description
 * D | 1 | description | 2019-12-02T18:00
 * E | 0 | description | 2019-12-02T18:00 | 2019-12-02T20:00
 * </pre>
 */
public class Storage {
    private String filePath;

    /**
     * Creates a new Storage instance bound to the specified file path.
     * Parent directories are created on first save/load if missing.
     *
     * @param filePath absolute or relative path to the data file
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the backing file. If the file does not exist, it is created
     * and an empty list is returned. Corrupted lines are skipped with a message.
     *
     * @return the list of tasks loaded from disk (possibly empty)
     * @throws JohnException if an IO error occurs while accessing the file
     */
    public ArrayList<Task> load() throws JohnException {
        File f = new File(filePath);
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
                    System.out.println("Skipped corrupted line: " + e.getMessage()); // skip corrupted line
                }
            }
            s.close();
        } catch (IOException e) {
            throw new JohnException("Error loading tasks from file: " + e.getMessage());
        }
        return tasks;
    }

    /**
     * Parses a single line from the storage file into a {@link Task}.
     *
     * @param line the raw line content
     * @return a reconstructed Task instance
     * @throws JohnException if the line is malformed or references an unknown type
     */
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

    /**
     * Saves all tasks to the backing file, overwriting existing content.
     *
     * @param tasklist the tasks to persist
     */
    public void save(TaskList tasklist) {
        try {
            List<Task> tasks = tasklist.getTasks();
            FileWriter fw = new FileWriter(filePath);
            for (Task task : tasks) {
                fw.write(task.toFileString() + System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            System.err.println("Error saving tasks: " + e.getMessage());
        }
    }
}
