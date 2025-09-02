package john.exceptions;

/**
 * Custom exception type for John application errors.
 */
public class JohnException extends Exception {
    public JohnException(String message) {
        super(message);
    }
}
