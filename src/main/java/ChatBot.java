import java.util.ArrayList;

/**
 * Main class for the ChatBot application.
 * Uses an object-oriented design with Command pattern.
 */
public class ChatBot {
    private final Storage storage;
    private final Ui ui;
    private final ArrayList<Task> tasks;

    /**
     * Constructs a ChatBot instance.
     *
     * @param filePath the path to the storage file
     */
    public ChatBot(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        this.tasks = storage.load();
    }

    /**
     * Runs the ChatBot application.
     */
    public void run() {
        ui.showWelcome();

        while (true) {
            String input = ui.readCommand();
            Command command = Parser.parse(input);
            String response = command.execute(tasks, storage);
            ui.showMessage(response);

            if (command.isExit()) {
                break;
            }
        }

        ui.close();
    }

    /**
     * Main entry point of the application.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        new ChatBot("data/duke.txt").run();
    }
}
