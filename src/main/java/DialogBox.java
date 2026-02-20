import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * Represents a dialog box consisting of a label containing text from the speaker.
 * A-BetterGui: Styled with colors, rounded corners, and clear visual distinction.
 */
public class DialogBox extends HBox {

    private static final String USER_STYLE = "-fx-background-color: #E3F2FD; -fx-background-radius: 12; "
            + "-fx-padding: 10 14 10 14; -fx-font-size: 13px;";
    private static final String BOT_STYLE = "-fx-background-color: #F5F5F5; -fx-background-radius: 12; "
            + "-fx-padding: 10 14 10 14; -fx-font-size: 13px; -fx-border-color: #E0E0E0; "
            + "-fx-border-radius: 12; -fx-border-width: 1;";

    private final Label text;

    private DialogBox(String message) {
        text = new Label(message);
        text.setWrapText(true);
        text.setMaxWidth(Region.USE_PREF_SIZE);

        this.setAlignment(Pos.TOP_LEFT);
        this.getChildren().add(text);
        this.setPadding(new Insets(6, 8, 6, 8));
        this.setSpacing(10);
        this.setMaxWidth(Region.USE_PREF_SIZE);
    }

    /**
     * Creates a dialog box for the user (right-aligned, blue tint).
     *
     * @param message user message
     * @return dialog box node
     */
    public static DialogBox getUserDialog(String message) {
        DialogBox box = new DialogBox(message);
        box.setAlignment(Pos.TOP_RIGHT);
        box.setStyle(USER_STYLE);
        return box;
    }

    /**
     * Creates a dialog box for the ChatBot (left-aligned, gray tint).
     *
     * @param messages one or more chatbot message lines
     * @return dialog box node
     */
    public static DialogBox getChatBotDialog(String... messages) {
        String combined = String.join("\n", messages);
        DialogBox box = new DialogBox(combined);
        box.setStyle(BOT_STYLE);
        return box;
    }
}

