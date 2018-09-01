package frames;

import generators.SimpleRandomGenerator;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

import static javafx.collections.FXCollections.observableArrayList;
import static javafx.geometry.Pos.*;

@Slf4j
public class MainFrame extends Application {

    private CheckBox checkBox;
    private SimpleRandomGenerator generator = new SimpleRandomGenerator();
    private ChoiceBox<Integer> choiceBox;
    private Button button;
    private List<Integer> passLengthOptions = Arrays.asList(6, 8, 10, 12, 16, 20);
    private TextArea textArea;


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


        createCheckBox();
        checkBox.setOnAction(event -> useNumbers());

        createChoiceBox((Integer[])passLengthOptions.toArray());
        choiceBox.setOnAction(event -> generator.setPassLength(choiceBox.getValue()));

        StackPane root = new StackPane();
        Scene scene = new Scene(root, 400, 250);

        createTextArea();

        createButton("Create new password");
        button.setOnAction(event -> generatePassByButtonClick(generator, textArea));

        primaryStage.setTitle("Password generator");
        primaryStage.setScene(scene);
        primaryStage.show();

        HBox hBox = new HBox();
        configureHBox(hBox, TOP_LEFT);
        hBox.getChildren().addAll(choiceBox, new Label("Set password length"), checkBox);

        root.getChildren().addAll(hBox, button, textArea);
    }

    private void configureHBox(HBox hBox, Pos alignment) {
        hBox.setSpacing(10);
        hBox.setAlignment(alignment);
        hBox.setPadding(new Insets(10, 0, 0, 10));
    }

    private void createTextArea() {
        textArea = new TextArea();
        textArea.setVisible(false);
        StackPane.setMargin(textArea, new Insets(50.0, 50.0, 150.0, 50.0));
    }

    private void createChoiceBox(Integer[] passLengthOptions) {
        choiceBox = new ChoiceBox<>(observableArrayList(passLengthOptions));
    }

    private void createCheckBox() {
        checkBox = new CheckBox("Use digits");
        final Tooltip tooltip = new Tooltip("$ tooltip");
        tooltip.setFont(new Font("Arial", 16));
        checkBox.setTooltip(tooltip);
        checkBox.setIndeterminate(false);
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
        if (generator.getPassLength() != 0) {
            String pass = generator.generate(true);
            textArea.setText(pass);
        } else {
            textArea.setText("Please set pass length");
        }
    }

    private void createButton(String buttonLabel) {
        button = new Button();
        button.setText(buttonLabel);
        button.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> button.setEffect(new DropShadow()));
        button.addEventHandler(MouseEvent.MOUSE_EXITED, e -> button.setEffect(null));
        StackPane.setMargin(button, new Insets(150.0, 50.0, 10.0, 50.0));
    }
}
