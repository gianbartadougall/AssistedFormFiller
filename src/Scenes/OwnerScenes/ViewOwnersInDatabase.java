package Scenes.OwnerScenes;

import DataTransfer.Data;
import DataTransfer.DataSaver;
import Display.Display;
import Enums.Enum;
import Objects.Owner;
import Scenes.SceneUtilities;
import Scenes.SceneUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Map;

public class ViewOwnersInDatabase {

    private Display display;
    private SceneUtils su = new SceneUtils();
    private Stage stage;
    private Data data;
    private HBox menuBar;
    private Scene scene, mainScene;
    private EditOwner editOwner;
    private BorderPane borderPane;
    private GridPane listOfOwners;
    private int width, height;
    private Button backButton;
    private SceneUtilities util;

    public ViewOwnersInDatabase(Stage stage, Scene scene,  Data data, int width, int height, Display display) {
        this.stage = stage;
        this.data = data;
        this.mainScene = scene;
        this.width = width;
        this.height = height;
        this.display = display;
        util = new SceneUtilities();
        createScene();
    }

    private void createScene() {
        borderPane = new BorderPane();
        listOfOwners = new GridPane();
        menuBar = new HBox();
        ScrollPane scrollPane = new ScrollPane(borderPane);
        configureScrollPane(scrollPane);
        scene = new Scene(scrollPane);
        setUpEditScene();
        configureBorderPane();
        configureVbox();
        createLabels();
        configureMenuBar();
    }

    private void setUpEditScene() {
        editOwner = new EditOwner(stage, display, scene, width, height, data, this);
    }

    private void configureScrollPane(ScrollPane scrollPane) {
        scrollPane.setMaxSize(width, height);
        scrollPane.setMinSize(width, height);
    }

    private void configureBorderPane() {
        borderPane.setTop(menuBar);
        borderPane.setCenter(listOfOwners);
    }

    private void configureMenuBar() {
        backButton = new Button("Back");
        su.configureMenuBar(stage, scene, backButton, menuBar, "Owners in data base");
        backButton.setOnMouseClicked(e -> {
            display.reloadOptionsBar();
            stage.setScene(mainScene);
        });
    }

    private void createLabels() {
        ArrayList<GridPane> sortedList = new ArrayList<>();

        for (Map.Entry<String, Owner> owner : data.getOwners().entrySet()) {
            GridPane ownerInformation = createGridPane();
            Button edit = configureButton("Edit", owner.getKey());
            edit.setOnMouseClicked(e -> stage.setScene(editOwner.getScene(data.getOwners().get(edit.getId()))));
            Label name = new Label(owner.getKey());
            name.setId(owner.getKey());
            Label emptySpace = new Label();
            emptySpace.setMinHeight(20);
            ownerInformation.add(name, 0, 0);
            ownerInformation.add(edit, 0, 1);
            ownerInformation.add(emptySpace, 0, 2);
            Button delete = util.configureButton("Delete", "#f08080", 2, 200, 25);
            delete.setOnAction(e -> createAlert(owner.getKey()));
            for (Map.Entry<String,String> attributes : owner.getValue().attributes().entrySet()) {
                int index = ownerInformation.getChildren().size();
                ownerInformation.add(new Label(attributes.getKey()), 0, index);
                if (attributes.getKey().equals("ownerReceiveEmails")) {
                    ownerInformation.add(new Label(attributes.getValue().equals("0") ? "NO" : "YES"), 1, index);
                } else ownerInformation.add(new Label(attributes.getValue()), 1, index);
            }
            ownerInformation.add(delete, 0, ownerInformation.getChildren().size());
            sortedList.add(util.getIndexInAlphabeticalOrder(sortedList, ownerInformation), ownerInformation);
        }

        int row = 0, col =0;
        for (int i=0; i<sortedList.size(); i++) {
            if (col == 4) {
                col = 0;
                row++;
            }
            listOfOwners.add(sortedList.get(i), col, row);
            col++;
        }
    }

    private void createAlert(String id) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Confirm Delete");
        stage.setMinWidth(300);

        Label label = new Label("Delete this Tenant?");
        label.setPadding(new Insets(20, 20, 20, 20));
        label.setFont(new Font("Arial", 16));
        Button deleteButton = new Button("Delete");
        deleteButton.setStyle("-fx-background-color: #b0e0e6; -fx-background-radius: 10;");
        deleteButton.setMinWidth(80);
        deleteButton.setOnAction(e -> {
            deleteOwner(id);
            stage.close();
        });
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> stage.close());
        cancelButton.setMinWidth(80);
        cancelButton.setStyle("-fx-background-color: #b0e0e6; -fx-background-radius: 10;");
        VBox vBox = new VBox();
        vBox.setMinHeight(80);
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(0, 20, 20, 20));
        hBox.setSpacing(100);
        hBox.getChildren().addAll(deleteButton, cancelButton);
        hBox.setAlignment(Pos.CENTER_LEFT);
        vBox.getChildren().addAll(label, hBox);

        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.showAndWait();
    }


    private Button configureButton(String title, String id) {
        Button button = new Button(title);
        button.setMinWidth(200);
        button.setMaxHeight(25);
        button.setStyle("-fx-background-color: #dcdcdc;");
        button.setId(id);
        GridPane.setColumnSpan(button, 2);
        return button;
    }

    private void configureVbox() {
        listOfOwners.setMinSize(width, height);
    }

    private GridPane createGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(20,20, 20, 20));
        gridPane.setHgap(20);
        return gridPane;
    }

    public Scene getScene() {
        listOfOwners.getChildren().clear();
        createLabels();
        return scene;
    }

    private void deleteOwner(String id) {
        data.getOwners().remove(id);
        DataSaver saver = new DataSaver(data);
        saver.saveData(Enum.dataPath);
        listOfOwners.getChildren().clear();
        createLabels();
    }

    public void update() {
        listOfOwners.getChildren().clear();
        createLabels();
    }

}
