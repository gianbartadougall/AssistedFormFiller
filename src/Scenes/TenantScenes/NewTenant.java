package Scenes.TenantScenes;

import DataTransfer.Data;
import DataTransfer.DataSaver;
import Display.Display;
import Enums.Enum;
import Objects.Tenant;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewTenant extends Layer2TenantScene {

    private Display display;
    private Scene parentScene;
    private Data data;
    private Scene scene;

    public NewTenant(Stage stage, Display display, Scene parentScene, int width, int height, Data data) {
        super(stage, display, parentScene, width, height);
        this.parentScene = parentScene;
        this.data = data;
        this.display = display;
        this.scene = new Scene(frame);
        customizeConfirmButton();
        monitorNewBond();
        newBond.setSelected(true);
    }

    private void monitorNewBond() {
        newBond.selectedProperty().addListener((obs, v1, v2) -> {
            if (v2) {
                rentalBondNumberTF.setText("");
                rentalBondNumberTF.setDisable(true);
            } else {
                rentalBondNumberTF.setDisable(false);
            }
        });
    }

    private void customizeConfirmButton() {
        confirmButton.setText("Create");
        confirmButton.setOnAction(e -> {
            Tenant tenant = createTenant();
            if (tenant != null) {
                data.getTenants().put(textFields[0].getText().trim() + " " + textFields[1].getText().trim(), tenant);
                saveDataToDatabase();
                clearTextFields();
                display.reloadOptionsBar();
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
        receivesSubsidy.setSelected(false);
        newBond.setSelected(true);
    }
}
