package Scenes.OwnerScenes;

import DataTransfer.Data;
import DataTransfer.DataSaver;
import Display.Display;
import Enums.Enum;
import Objects.Owner;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

public class NewOwner extends Layer2OwnerScene {

    private Scene parentScene;
    private Data data;
    private Scene scene;

    public NewOwner(Stage stage, Display display, Scene parentScene, int width, int height, Data data) {
        super(stage, display, parentScene, width, height);
        this.parentScene = parentScene;
        this.data = data;
        this.scene = new Scene(frame);
        customizeConfirmButton();
    }

    private void customizeConfirmButton() {
        confirmButton.setText("Create");
        confirmButton.setOnAction(e -> {
            Owner owner = createOwner();
            if (owner != null) {
                data.getOwners().put(textFields[0].getText().trim() + " " + textFields[1].getText().trim(), owner);
                saveDataToDatabase();
                clearTextFields();
                stage.setScene(parentScene);
            }
        });
    }

    private void saveDataToDatabase() {
        DataSaver saver = new DataSaver(data);
        saver.saveData(Enum.dataPath);
    }

    public Scene getScene() {
        return scene;
    }

    private void clearTextFields() {
        for (TextField textField : textFields) {
            textField.clear();
        }
        receiveEmails.setSelected(false);
    }
}
