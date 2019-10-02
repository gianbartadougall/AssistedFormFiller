package Scenes;

import Display.Display;
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

public abstract class BaseLayer {

    private Display display;
    protected Stage stage;
    protected BorderPane frame;
    protected GridPane information;
    protected HBox menuBar;
    protected Label errorLabel;
    protected Button backButton;
    protected SceneUtilities utils;
    protected int width, height;

    public BaseLayer(Stage stage, Display display, Scene parentScene, int width, int height) {
        this.display = display;
        this.stage = stage;
        this.width = width;
        this.height = height;
        utils = new SceneUtilities();
        setUpBackButton(parentScene);
        setUpInformation();
        this.menuBar = createMenuBar();
        this.frame = createFrame();
    }

    private void setUpInformation() {
        information = new GridPane();
        this.information = utils.setUpGridPane(information, 25, 25, 20, Pos.TOP_CENTER,
                width, height, "#b0e0e6", "#b0e0e6");
    }

    private void setUpBackButton(Scene parentScene) {
        backButton = new Button("Back");
        backButton.setMinWidth(80);
        backButton.setStyle("-fx-background-color:  #dcdcdc; -fx-background-radius: 0,0,0,0;");
        backButton.setOnAction(e -> {
            stage.setScene(parentScene);
            display.reloadOptionsBar();
            errorLabel.setText("");
        });
    }

    protected BorderPane createFrame() {
        BorderPane bp = new BorderPane();
        bp.setTop(menuBar);
        bp.setCenter(information);
        bp.setMinSize(width, height);
        return bp;
    }

    protected Label createErrorLabel(int columnSpan) {
        Label label = new Label();
        label.setFont(new Font("Arial", 16));
        label.setAlignment(Pos.CENTER);
        GridPane.setHalignment(label, HPos.CENTER);
        label.setStyle("-fx-text-fill: red;");
        GridPane.setColumnSpan(label, columnSpan);
        return label;
    }

    protected HBox createMenuBar() {
        HBox box = new HBox();

        Label label = new Label();
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
        box.setMinWidth(minWidth);
        for (String s : itemsToAdd) {
            box.getItems().add(s);
        }
        return box;
    }

    protected Button createConfirmButton(int width, int columnSpan) {
        Button button = new Button();
        GridPane.setHalignment(button, HPos.CENTER);
        button.setMinWidth(width);
        GridPane.setColumnSpan(button, columnSpan);
        button.setAlignment(Pos.CENTER);
        return button;
    }

    private Region createRegion() {
        Region r = new Region();
        HBox.setHgrow(r, Priority.ALWAYS);
        return r;
    }
}
