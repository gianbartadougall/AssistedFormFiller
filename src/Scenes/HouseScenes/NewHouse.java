package Scenes.HouseScenes;


import DataTransfer.Data;
import DataTransfer.DataSaver;
import Display.Display;
import Enums.Enum;
import Objects.House;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

public class NewHouse extends Layer2HouseScene {

    private Display display;
    private Scene parentScene;
    private Data data;
    private Scene scene;

    public NewHouse(Stage stage, int width, int height, Display display, Data data, Scene parentScene) {
        super(stage, width, height, display, parentScene);
        this.parentScene = parentScene;
        this.data = data;
        this.display = display;
        scene = new Scene(frame);
        customizeButtons();
    }

    private void customizeButtons() {
        confirmButton.setText("Create house");
        confirmButton.setOnAction(e -> {
            House house = createHouse();
            if (house != null) {
                data.getHouses().put(textFields[0].getText(), house);
                saveDataToDatabase();
                clearTextFields();
                display.reloadOptionsBar();
                stage.setScene(parentScene);
            }
        });
    }

    private void clearTextFields() {
        for (TextField textField : textFields) {
            textField.clear();
        }
        dwellingType.getSelectionModel().select(0);
    }

    private void saveDataToDatabase() {
        DataSaver saver = new DataSaver(data);
        saver.saveData(Enum.dataPath);
    }

    public Scene getScene() {
        return scene;
    }
}
