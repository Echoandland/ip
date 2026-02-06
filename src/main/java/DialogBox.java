import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * Represents a dialog box consisting of a label containing text from the speaker.
 * This is a simplified version without avatar images.
 */
public class DialogBox extends HBox {

    private final Label text;

    private DialogBox(String message) {
        text = new Label(message);
        text.setWrapText(true);

        this.setAlignment(Pos.TOP_LEFT);
        this.getChildren().add(text);
        this.setPadding(new Insets(8, 10, 8, 10));
        this.setSpacing(10);
    }

    /**
     * Creates a dialog box for the user.
     *
     * @param message user message
     * @return dialog box node
     */
    public static DialogBox getUserDialog(String message) {
        DialogBox box = new DialogBox(message);
        box.setAlignment(Pos.TOP_RIGHT);
        return box;
    }

    /**
     * Creates a dialog box for the ChatBot using varargs.
     *
     * @param messages one or more chatbot message lines
     * @return dialog box node
     */
    public static DialogBox getChatBotDialog(String... messages) {
        String combined = String.join("\n", messages);
        return new DialogBox(combined);
    }
}

