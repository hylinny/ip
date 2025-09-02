package john.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class JohnUiTest {
    @Test
    public void testPrintLine() {
        JohnUi ui = new JohnUi();
        String expectedLine = "__________________________________________________";

        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));
        ui.printLine();
        assertEquals(expectedLine + System.lineSeparator(), outContent.toString());
    }
}
