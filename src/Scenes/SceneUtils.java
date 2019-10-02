package Scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class SceneUtils {


    public SceneUtils() {
    }

    public void configureMenuBar(Stage stage, Scene mainScene, Button backButton, HBox menuBar, String title) {
        backButton.setMinWidth(80);
        backButton.setStyle("-fx-background-color:  #dcdcdc; -fx-background-radius: 0,0,0,0;");
        backButton.setOnMouseClicked(e -> stage.setScene(mainScene));
        Label label = new Label(title);
        label.setFont(new Font("Arial", 20));
        Region r1 = new Region();
        HBox.setHgrow(r1, Priority.ALWAYS);
        Region r2 = new Region();
        HBox.setHgrow(r2, Priority.ALWAYS);
        menuBar.getChildren().addAll(backButton,r1, label, r2, new Label(""));
        menuBar.setPadding(new Insets(20, 20, 20, 20));
        menuBar.setAlignment(Pos.CENTER);
        menuBar.setStyle("-fx-background-color:  #b0e0e6; -fx-border-color: #b0e0e6;");
    }

}
