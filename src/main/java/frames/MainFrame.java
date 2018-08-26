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

        Button button = new Button();
        button.setText("Create new password");



        StackPane root = new StackPane();

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Password generator");
        primaryStage.setScene(scene);
        primaryStage.show();

        HBox hb = new HBox();
        hb.getChildren().addAll(choiceBox, new Label("Set password length"));
        hb.setSpacing(10);
        hb.setAlignment(Pos.TOP_LEFT);
        hb.setPadding(new Insets(10, 0, 0, 10));

        button.setOnAction(event -> {
            String pass = generator.generate(true, true);
            System.out.println(pass);
        });

        root.getChildren().add(hb);
        root.getChildren().add(button);
    }
}
