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

public class MainFrame extends Application {

    public static void start(String... args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        final ChoiceBox<String> cb = new ChoiceBox<>(
                FXCollections.observableArrayList("5", "10", "12", "15"));

        cb.getSelectionModel().selectedIndexProperty()
                .addListener((ov, value, new_value) -> {

                });

        Button btn = new Button();
        btn.setText("Create new password");

        SimpleRandomGenerator generator = new SimpleRandomGenerator();



        StackPane root = new StackPane();

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Password generator");
        primaryStage.setScene(scene);
        primaryStage.show();

        HBox hb = new HBox();
        hb.getChildren().addAll(cb, new Label("Set password length"));
        hb.setSpacing(30);
        hb.setAlignment(Pos.TOP_LEFT);
        hb.setPadding(new Insets(10, 0, 0, 10));

        btn.setOnAction(event -> {
            String pass = generator.generate(10, true, true);
            System.out.println(pass);
        });

        root.getChildren().add(hb);
        root.getChildren().add(btn);
    }
}
