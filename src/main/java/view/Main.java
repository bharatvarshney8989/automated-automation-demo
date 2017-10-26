package view;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.nio.file.Paths;

import static javafx.stage.StageStyle.TRANSPARENT;

/**
 * Created by kohlih on 26-10-2017.
 */
public class Main extends Application{

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage) throws Exception{
        String fxmlpath="src/main/resources/app.fxml";
        Parent root = FXMLLoader.load(Paths.get(fxmlpath).toUri().toURL());
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            }
        });
        primaryStage.setTitle("REST API Automation");
        Scene scene = new Scene(root, 400, 625);
        primaryStage.setScene(scene);
        scene.setFill(null);
        primaryStage.setResizable(false);
        primaryStage.initStyle(TRANSPARENT);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
