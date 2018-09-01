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

import static javafx.collections.FXCollections.observableArrayList;

@Slf4j
public class MainFrame extends Application {

    CheckBox checkBox;
    private SimpleRandomGenerator generator;

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

        generator = new SimpleRandomGenerator();

        Integer[] passLengthOptions = {6, 8, 10, 12, 16, 20};

        final ChoiceBox<Integer> choiceBox = new ChoiceBox<>(observableArrayList(passLengthOptions));

        checkBox = new CheckBox("Use digits");
        final Tooltip tooltip = new Tooltip("$ tooltip");
        tooltip.setFont(new Font("Arial", 16));
        checkBox.setTooltip(tooltip);
        checkBox.setIndeterminate(false);
        checkBox.setOnAction(event -> useNumbers());

        choiceBox.setOnAction(event -> generator.setPassLength(choiceBox.getValue()));

        StackPane root = new StackPane();
        Scene scene = new Scene(root, 400, 250);

        HBox hBox = new HBox();
        TextArea textArea = new TextArea();
        textArea.setVisible(false);
        StackPane.setMargin(textArea, new Insets(50.0, 50.0, 150.0, 50.0));


        Button button = createButton("Create new password");
        button.setOnAction(event -> generatePassByButtonClick(generator, textArea));

        primaryStage.setTitle("Password generator");
        primaryStage.setScene(scene);
        primaryStage.show();

        hBox.getChildren().addAll(choiceBox, new Label("Set password length"), checkBox);
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.TOP_LEFT);
        hBox.setPadding(new Insets(10, 0, 0, 10));


        root.getChildren().addAll(hBox, button, textArea);
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

    private Button createButton(String buttonLabel) {
        Button button = new Button();
        button.setText(buttonLabel);
        button.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> button.setEffect(new DropShadow()));
        button.addEventHandler(MouseEvent.MOUSE_EXITED, e -> button.setEffect(null));
        StackPane.setMargin(button, new Insets(150.0, 50.0, 10.0, 50.0));
        return button;
    }
}
