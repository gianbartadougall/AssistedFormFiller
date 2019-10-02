package Scenes.HouseScenes;

import DataTransfer.Data;
import DataTransfer.DataSaver;
import Display.Display;
import Enums.Enum;
import Objects.House;
import Scenes.SceneUtilities;
import Scenes.Screen;
import Utilities.Utils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Map;

public class ViewHousesInDatabase extends Screen {

    private Scene scene;
    private EditHouse editHouse;
    private GridPane listOfHouses;
    private SceneUtilities utils;
    private Utils general;

    public ViewHousesInDatabase(Data data, Stage stage, Scene scene, int width, int height, Display display) {
        super(data, stage, scene, width, height, display);
    }

    @Override
    protected void createScene() {
        utils = new SceneUtilities();
        general = new Utils();
        listOfHouses = new GridPane();
        scene = new Scene(setUpScrollPane());

        setUpEditScene();
        configureBorderPane();
        configureVbox();
        createLabels();
    }

    private void setUpEditScene() {
        editHouse = new EditHouse(stage, width, height, display, data, this);
    }

    private ScrollPane setUpScrollPane() {
        ScrollPane scrollPane = new ScrollPane(frame);
        scrollPane.setMaxSize(width, height);
        scrollPane.setMinSize(width, height);
        return scrollPane;
    }

    private void configureBorderPane() {
        frame.setTop(menuBar);
        frame.setCenter(listOfHouses);
    }

    private void createLabels() {
        ArrayList<GridPane> sortedList = new ArrayList<>();

        for (Map.Entry<String, House> house : data.getHouses().entrySet()) {
            GridPane ownerInformation = createGridPane();
            Button edit = utils.configureButton("Edit", "#dcdcdc", 2, 200, 25);
            edit.setOnMouseClicked(e -> stage.setScene(editHouse.getScene(house.getValue())));

            ownerInformation.add(utils.newLabel(house.getKey(), house.getKey(), 25), 0, 0);
            ownerInformation.add(edit, 0, 1);
            ownerInformation.add(utils.newLabel("", "", 25), 0, 2);

            Button delete = utils.configureButton("Delete", "#f08080", 2, 200, 25);

            Stage stage = new Stage();
            Button deleteButton = general.createButton("Delete", 80, 25);
            deleteButton.setOnAction(e -> {
                deleteHouse(house.getKey());
                stage.close();
            });

            delete.setOnAction(e -> utils.confirmObjectDelete(stage, deleteButton));

            for (Map.Entry<String,String> attributes : house.getValue().attributes().entrySet()) {
                int index = ownerInformation.getChildren().size();
                ownerInformation.add(new Label(attributes.getKey()), 0, index);
                String title = checkForIncompleteValues(attributes.getKey(), attributes.getValue());
                ownerInformation.add(new Label(title), 1, index);
            }
            ownerInformation.add(delete, 0, ownerInformation.getChildren().size());
            sortedList.add(utils.getIndexInAlphabeticalOrder(sortedList, ownerInformation), ownerInformation);
        }

        utils.addSortedListToGridpane(listOfHouses, sortedList);
    }

    private String checkForIncompleteValues(String key, String val) {
        switch (key) {
            case "dwellingType": return utils.intToType(val);
            case "rent": return "$ " + val;
            default: return val;
        }
    }

    private void configureVbox() {
        listOfHouses.setMinSize(width, height);
    }

    private GridPane createGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(20,20, 20, 20));
        gridPane.setHgap(20);
        return gridPane;
    }

    public Scene getScene() {
        //update();
        return scene;
    }

    private void deleteHouse(String id) {
        data.getHouses().remove(id);
        DataSaver saver = new DataSaver(data);
        saver.saveData(Enum.dataPath);
        listOfHouses.getChildren().clear();
        createLabels();
    }

    public void update() {
        listOfHouses.getChildren().clear();
        createLabels();
    }

    @Override
    protected Boolean dataIsInvalid() {
        return null;
    }
}
