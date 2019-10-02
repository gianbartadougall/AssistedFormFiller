package Scenes.HouseScenes;

import DataTransfer.Data;
import DataTransfer.DataSaver;
import Display.Display;
import Enums.Enum;
import Objects.House;
import Scenes.OwnerScenes.ViewOwnersInDatabase;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EditHouse extends Layer2HouseScene {

    private ViewHousesInDatabase parent;
    private Data data;
    private Scene scene;
    private String originalAddress;
    private String[] officialAttributes;

    public EditHouse(Stage stage, int width, int height, Display display, Data data, ViewHousesInDatabase parent) {
        super(stage, width, height, display, parent.getScene());
        this.data = data;
        this.parent = parent;
        scene = new Scene(frame);
        officialAttributes = Enum.HOUSE_OFFICIAL_ATTRIBUTES;
        customizeButtons();
    }

    private void customizeButtons() {
        confirmButton.setText("Confirm");
        confirmButton.setOnAction(e -> {
            House house = createHouse();
            if (house != null) {
                data.getHouses().remove(originalAddress);
                data.getHouses().put(textFields[0].getText().trim(), house);
                saveDataToDatabase();
                parent.update();
                stage.setScene(parent.getScene());
            }
        });
    }

    private void saveDataToDatabase() {
        DataSaver saver = new DataSaver(data);
        saver.saveData(Enum.dataPath);
    }

    public Scene getScene(House house) {
        loadData(house);
        return scene;
    }

    private void loadData(House house) {
        originalAddress = house.attributes().get(officialAttributes[0]);
        for (int i=0; i<textFields.length; i++) {
            textFields[i].setText(house.attributes().get(officialAttributes[i]));
        }
        dwellingType.getSelectionModel().select(utils.intToType(house.attributes().get("dwellingType")));
    }
}
