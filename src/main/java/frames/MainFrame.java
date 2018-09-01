package frames;

import generators.SimpleRandomGenerator;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

import static javafx.collections.FXCollections.observableArrayList;
import static javafx.geometry.Pos.TOP_LEFT;

@Slf4j
public class MainFrame extends Application {

    private CheckBox checkBox;
    private SimpleRandomGenerator generator = new SimpleRandomGenerator();
    private ChoiceBox choiceBox;
    private Button generatePassButton;
    private Button copyToClipboardButton;
    private List<Integer> passLengthOptions = Arrays.asList(6, 8, 10, 12, 16, 20);
    private TextArea textArea;
    private StackPane stackPane;


    public static void start(String... args) {
        try {
            launch(args);
        } catch (Exception e) {
            log.debug(e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) {

        checkBox = createCheckBox();
        checkBox.setOnAction(event -> useNumbers());

        choiceBox = createChoiceBox((Integer[])passLengthOptions.toArray());
        choiceBox.setOnAction(event -> generator.setPassLength((Integer) choiceBox.getValue()));

        textArea = createTextArea();

        generatePassButton = createButton("Create new password", 150.0, 10.0, 50.0, 50.0, true);
        generatePassButton.setOnAction(event -> generatePassByButtonClick(generator, textArea));

        copyToClipboardButton = createButton("Copy to \nclipboard", 50.0, 150.0, 295.0, 15.0, false);
        copyToClipboardButton.setOnAction(event -> copyToClipboard(textArea));

        HBox hBox = new HBox();
        configureHBox(hBox, TOP_LEFT);
        hBox.getChildren().addAll(choiceBox, new Label("Set password length"), checkBox);

        stackPane = createStackPane(primaryStage, "Password generator", 400, 250);
        stackPane.getChildren().addAll(hBox, generatePassButton, textArea, copyToClipboardButton);
    }

    private void copyToClipboard(TextArea textArea) {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(textArea.getText());
        clipboard.setContent(content);
    }

    private StackPane createStackPane(Stage primaryStage, String title, int width, int height) {
        stackPane = new StackPane();
        Scene scene = new Scene(stackPane, width, height);
        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.show();
        return stackPane;
    }

    private void configureHBox(HBox hBox, Pos alignment) {
        hBox.setSpacing(10);
        hBox.setAlignment(alignment);
        hBox.setPadding(new Insets(10, 0, 0, 10));
    }

    private TextArea createTextArea() {
        textArea = new TextArea();
        textArea.setVisible(false);
        StackPane.setMargin(textArea, new Insets(50.0, 100.0, 150.0, 25.0));
        return textArea;
    }

    private ChoiceBox createChoiceBox(Integer[] passLengthOptions) {
        choiceBox = new ChoiceBox<>(observableArrayList(passLengthOptions));
        return choiceBox;
    }

    private CheckBox createCheckBox() {
        CheckBox checkBox = new CheckBox("Use digits");
        final Tooltip tooltip = new Tooltip("$ tooltip");
        tooltip.setFont(new Font("Arial", 16));
        checkBox.setTooltip(tooltip);
        checkBox.setIndeterminate(false);
        return checkBox;
    }

    private void useNumbers() {
        if (checkBox.isSelected()){
            generator.setNumbers(true);
        } else {
            generator.setNumbers(false);
        }
    }

    private void generatePassByButtonClick(SimpleRandomGenerator generator, TextArea textArea) {
        textArea.setVisible(true);
        copyToClipboardButton.setVisible(true);
        if (generator.getPassLength() != 0) {
            String pass = generator.generate(true);
            textArea.setText(pass);
        } else {
            textArea.setText("Please set pass length");
        }
    }

    private Button createButton(String buttonLabel, double top, double bottom, double left, double right, boolean visible) {
        Button button = new Button();
        button.setVisible(visible);
        button.setText(buttonLabel);
        button.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> button.setEffect(new DropShadow()));
        button.addEventHandler(MouseEvent.MOUSE_EXITED, e -> button.setEffect(null));
        StackPane.setMargin(button, new Insets(top, right, bottom, left));
        return button;
    }
}
