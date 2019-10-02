package Scenes.OwnerScenes;

import DataTransfer.Data;
import DataTransfer.DataSaver;
import Display.Display;
import Enums.Enum;
import Objects.Owner;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EditOwner extends Layer2OwnerScene {

    private Display display;
    private ViewOwnersInDatabase parent;
    private Data data;
    private Scene scene;
    private String originalName;

    public EditOwner(Stage stage, Display display, Scene parentScene, int width, int height, Data data, ViewOwnersInDatabase parent) {
        super(stage, display, parentScene, width, height);
        this.display = display;
        this.data = data;
        this.parent = parent;
        this.scene = new Scene(frame);
        customizeConfirmButton();
    }

    private void customizeConfirmButton() {
        confirmButton.setText("Confirm");
        confirmButton.setOnAction(e -> {
            Owner owner = createOwner();
            if (owner != null) {
                data.getOwners().remove(originalName);
                data.getOwners().put(textFields[0].getText().trim() + " " + textFields[1].getText().trim(), owner);
                saveDataToDatabase();
                parent.update();
                display.reloadOptionsBar();
                stage.setScene(parent.getScene());
            }
        });
    }

    private void saveDataToDatabase() {
        DataSaver saver = new DataSaver(data);
        saver.saveData(Enum.dataPath);
    }

    public Scene getScene(Owner owner) {
        loadData(owner);
        return scene;
    }

    private void loadData(Owner owner) {
        originalName = owner.attributes().get(officialAttributes[0]);
        for (int i=0; i<textFields.length; i++) {
            textFields[i].setText(owner.attributes().get(officialAttributes[i]));
        }
        receiveEmails.setSelected(owner.attributes().get("ownerReceiveEmails").equals("1"));
    }
}
