package John.Storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import John.Exceptions.JohnException;
import John.Tasks.*;

public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

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
