package frames;

import generators.SimpleRandomGenerator;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MainFrame extends Application {

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

        SimpleRandomGenerator generator = new SimpleRandomGenerator();

        final ChoiceBox<Integer> choiceBox = new ChoiceBox<>(
                FXCollections.observableArrayList(6, 8, 10, 15, 20));

        choiceBox.setOnAction(event -> generator.setPassLength(choiceBox.getValue()));

        StackPane root = new StackPane();
        Scene scene = new Scene(root, 300, 250);

        Button button = new Button();
        HBox hBox = new HBox();
        TextArea textArea = new TextArea();
        textArea.setVisible(false);
        StackPane.setMargin(textArea, new Insets(50.0, 50.0, 180.0, 50.0));
        StackPane.setMargin(button, new Insets(150.0, 50.0, 10.0, 50.0));


        button.setText("Create new password");
        button.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> button.setEffect(new DropShadow()));
        button.addEventHandler(MouseEvent.MOUSE_EXITED, e -> button.setEffect(null));

        primaryStage.setTitle("Password generator");
        primaryStage.setScene(scene);
        primaryStage.show();

        hBox.getChildren().addAll(choiceBox, new Label("Set password length"));
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.TOP_LEFT);
        hBox.setPadding(new Insets(10, 0, 0, 10));

        button.setOnAction(event -> {
            textArea.setVisible(true);
            if (generator.getPassLength() != 0) {
                String pass = generator.generate(true, true);
                textArea.setText(pass);
            } else {
                textArea.setText("Please set pass length");
            }
        });

        root.getChildren().addAll(hBox, button, textArea);
    }
}
