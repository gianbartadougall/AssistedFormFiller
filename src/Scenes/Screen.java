package Scenes;

import DataTransfer.Data;
import DataTransfer.DataSaver;
import Display.Display;
import Enums.Enum;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public abstract class Screen {

    protected Display display;
    protected Data data;
    protected Stage stage;
    protected Scene parentScene;
    protected BorderPane frame;
    protected HBox menuBar;
    protected int width, height;
    protected Label errorLabel;
    protected Button backButton, confirmButton;

    public Screen(Data data, Stage stage, Scene scene, int width, int height, Display display) {
        this.data = data;
        this.stage = stage;
        this.parentScene = scene;
        this.width = width;
        this.height = height;
        this.frame = createFrame();

        backButton = new Button("Back");
        backButton.setMinWidth(80);
        backButton.setStyle("-fx-background-color:  #dcdcdc; -fx-background-radius: 0,0,0,0;");
        backButton.setOnMouseClicked(e -> {
        stage.setScene(parentScene);
        display.reloadOptionsBar();
        });

        this.menuBar = createMenuBar("Title", backButton);
        this.display = display;
        this.confirmButton = createConfirmButton();
        errorLabel = createErrorLabel();
        createScene();
    }

    protected abstract void createScene();
    protected abstract Boolean dataIsInvalid();

    protected BorderPane createFrame() {
        BorderPane bp = new BorderPane();
        bp.setTop(menuBar);
        return bp;
    }

    protected Label createErrorLabel() {
        Label label = new Label();
        label.setFont(new Font("Arial", 20));
        label.setStyle("-fx-text-fill: red;");
        label.setVisible(false);
        GridPane.setColumnSpan(label, 2);
        return label;
    }

    protected HBox createMenuBar(String message, Button backButton) {
        HBox box = new HBox();

        //backButton = new Button("Back");
        //backButton.setMinWidth(80);
        //backButton.setStyle("-fx-background-color:  #dcdcdc; -fx-background-radius: 0,0,0,0;");
        //backButton.setOnMouseClicked(e -> {
            //stage.setScene(parentScene);
            //display.reloadOptionsBar();
        //});

        Label label = new Label(message);
        label.setFont(new Font("Arial", 20));

        Region r1 = createRegion(), r2 = createRegion();

        box.getChildren().addAll(backButton,r1, label, r2, new Label(""));
        box.setPadding(new Insets(20, 20, 20, 20));
        box.setAlignment(Pos.CENTER);
        box.setStyle("-fx-background-color: #b0e0e6; -fx-border-color: #b0e0e6;");
        return box;
    }

    protected ComboBox createComboBox(int minWidth, String[] itemsToAdd) {
        ComboBox box = new ComboBox();
        box.setMinWidth(200);
        for (String s : itemsToAdd) {
            box.getItems().add(s);
        }
        return box;
    }

    private Region createRegion() {
        Region r = new Region();
        HBox.setHgrow(r, Priority.ALWAYS);
        return r;
    }

    protected void saveDataToDatabase() {
        DataSaver saver = new DataSaver(data);
        saver.saveData(Enum.dataPath);
    }

    private Button createConfirmButton() {
        Button button = new Button();
        GridPane.setHalignment(button, HPos.CENTER);
        button.setMinWidth(300);
        GridPane.setColumnSpan(button, 2);
        button.setAlignment(Pos.CENTER);
        return button;
    }
}
