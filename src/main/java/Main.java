import chatbot.ChatBot;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * JavaFX GUI entry point for the ChatBot application.
 */
public class Main extends Application {

    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;
    private Button sendButton;
    private Scene scene;

    private final ChatBot chatBot = new ChatBot("data/duke.txt");

    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }

    @Override
    public void start(Stage stage) {
        setupComponents();
        AnchorPane mainLayout = buildMainLayout();
        scene = new Scene(mainLayout);
        stage.setScene(scene);

        applyStageAndLayout(stage, mainLayout);
        setupScrollOnNewMessage();
        showGreeting();
        setupInputHandlers();

        stage.show();
    }

    private void setupComponents() {
        scrollPane = new ScrollPane();
        dialogContainer = new VBox();
        scrollPane.setContent(dialogContainer);
        userInput = new TextField();
        sendButton = new Button("Send");
    }

    private AnchorPane buildMainLayout() {
        AnchorPane mainLayout = new AnchorPane();
        mainLayout.getChildren().addAll(scrollPane, userInput, sendButton);
        return mainLayout;
    }

    private void applyStageAndLayout(Stage stage, AnchorPane mainLayout) {
        stage.setTitle("ChatBot");
        stage.setResizable(false);
        stage.setMinHeight(600.0);
        stage.setMinWidth(400.0);
        mainLayout.setPrefSize(400.0, 600.0);

        scrollPane.setPrefSize(385, 535);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVvalue(1.0);
        scrollPane.setFitToWidth(true);
        dialogContainer.setFillWidth(true);
        dialogContainer.setAlignment(Pos.TOP_LEFT);
        userInput.setPrefWidth(325.0);
        sendButton.setPrefWidth(55.0);

        AnchorPane.setTopAnchor(scrollPane, 1.0);
        AnchorPane.setBottomAnchor(scrollPane, 45.0);
        AnchorPane.setLeftAnchor(userInput, 1.0);
        AnchorPane.setBottomAnchor(userInput, 1.0);
        AnchorPane.setRightAnchor(sendButton, 1.0);
        AnchorPane.setBottomAnchor(sendButton, 1.0);
    }

    private void setupScrollOnNewMessage() {
        dialogContainer.heightProperty().addListener(observable -> scrollPane.setVvalue(1.0));
    }

    private void showGreeting() {
        dialogContainer.getChildren().add(
                DialogBox.getChatBotDialog("Hello! I'm ChatBot.", "What can I do for you?"));
    }

    private void setupInputHandlers() {
        sendButton.setOnMouseClicked(event -> handleUserInput());
        userInput.setOnAction(event -> handleUserInput());
    }

    /**
     * Handles user input and displays both user and ChatBot responses.
     */
    private void handleUserInput() {
        String input = userInput.getText();
        if (input == null || input.trim().isEmpty()) {
            return;
        }
        String response = chatBot.getResponse(input);

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input),
                DialogBox.getChatBotDialog(response)
        );

        userInput.clear();

        if (chatBot.isExitCommand(input)) {
            Platform.exit();
        }
    }
}

