package John.Storage;

import John.Exceptions.JohnException;
import John.Tasks.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class StorageTest {

    @TempDir
    Path tempDir;

    @Test
    void testLoad_emptyFile() throws JohnException {
        File f = tempDir.resolve("tasks.txt").toFile();
        assertFalse(f.exists());

        Storage storage = new Storage(f.getAbsolutePath());
        ArrayList<Task> tasks = storage.load();

        assertTrue(f.exists());
        assertTrue(tasks.isEmpty());
    }

    @Test
    void testLoad() throws Exception {
        File f = tempDir.resolve("tasks.txt").toFile();
        try (FileWriter fw = new FileWriter(f)) {
            fw.write("T | 1 | buy milk\n");
            fw.write("D | 0 | return book | 2025-01-01T18:00\n");
            fw.write("E | 1 | meet | 2025-01-01 1800 | 2025-01-01T20:00\n");
        }

        Storage storage = new Storage(f.getAbsolutePath());
        ArrayList<Task> tasks = storage.load();

        assertEquals(3, tasks.size());
        assertEquals("[T][X] buy milk", tasks.get(0).toString());
        assertEquals("[D][ ] return book (by: Jan 01 2025 18:00)", tasks.get(1).toString());
        assertEquals("[E][X] meet (from: Jan 01 2025 18:00 to: Jan 01 2025 20:00)", tasks.get(2).toString());
    }

    @Test
    void testSave() throws Exception {
        File f = tempDir.resolve("test.txt").toFile();

        TaskList list = new TaskList();
        Task t1 = new Todo("refactor");
        Task t2 = new Deadline("submit assignment", "2025-09-01 2359");
        Task t3 = new Event("project meeting", "2025-01-01 0000", "2025-01-01 0200");
        t1.markDone();
        t3.markDone();

        list.addTask(t1);
        list.addTask(t2);
        list.addTask(t3);

        // Save
        Storage storage = new Storage(f.getAbsolutePath());
        storage.save(list);
        assertTrue(f.exists());

        ArrayList<Task> loaded = storage.load();
        assertEquals(3, loaded.size());
        assertEquals(t1.toFileString(), loaded.get(0).toFileString());
        assertEquals(t2.toFileString(), loaded.get(1).toFileString());
        assertEquals(t3.toFileString(), loaded.get(2).toFileString());
    }
}
