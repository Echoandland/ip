import java.util.Scanner;

/**
 * Handles all user interface interactions.
 */
public class Ui {
    private static final String LOGO = " ____        _        \n"
            + "|  _ \\ _   _| | _____ \n"
            + "| | | | | | | |/ / _ \\\n"
            + "| |_| | |_| |   <  __/\n"
            + "|____/ \\__,_|_|\\_\\___|\n";

    private final Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Shows the welcome message.
     */
    public void showWelcome() {
        System.out.println("Hello from\n" + LOGO);
        System.out.println("Hello! I'm ChatBot");
        System.out.println("What can I do for you?");
    }

    /**
     * Reads a line of user input.
     *
     * @return the user input string
     */
    public String readCommand() {
        return scanner.nextLine().trim();
    }

    /**
     * Shows a message to the user.
     *
     * @param message the message to show
     */
    public void showMessage(String message) {
        System.out.println(message);
    }

    /**
     * Shows an error message to the user.
     *
     * @param message the error message to show
     */
    public void showError(String message) {
        System.out.println(message);
    }

    /**
     * Closes the UI and releases resources.
     */
    public void close() {
        scanner.close();
    }
}
