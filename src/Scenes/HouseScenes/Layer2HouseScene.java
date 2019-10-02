package Scenes.HouseScenes;

import Display.Display;
import Enums.Enum;
import Objects.House;
import Scenes.BaseLayer;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public abstract class Layer2HouseScene extends BaseLayer {

    private TextField addressTF, postcodeTF, bedroomsTF, rentTF;
    protected TextField[] textFields;
    protected ComboBox dwellingType;
    private String[] attributes;
    protected Button confirmButton;

    public Layer2HouseScene(Stage stage, int width, int height, Display display, Scene parentScene) {
        super(stage, display, parentScene, width, height);
        setUpScene();
    }

    private void setUpScene() {
        attributes = Enum.HOUSE_LABEL_ATTRIBUTES;
        this.confirmButton = createConfirmButton(300, 2);
        setUpTextFields();
        setUpComboBox();
        setUpLabels();
    }

    private void setUpTextFields() {
        textFields = new TextField[]{addressTF = new TextField(), postcodeTF = new TextField(),
                bedroomsTF = new TextField(), rentTF = new TextField()};
    }

    private void setUpComboBox() {
        dwellingType = createComboBox(200, Enum.DWELLING_TYPES);
    }

    private void setUpLabels() {
        for (int i = 0; i < attributes.length; i++) {
            Label label = new Label(attributes[i]);
            information.add(label, 0, i);
        }
        addNodesToInformation();
        errorLabel = createErrorLabel(2);
        information.add(errorLabel, 0, (information.getChildren().size()/2)+1);
    }

    private void addNodesToInformation() {
        for (int i = 0; i< textFields.length; i++) {
            information.add(textFields[i], 1, i);
        }
        information.add(dwellingType, 1, information.getChildren().size()/2);
        information.add(confirmButton, 0, information.getChildren().size()/2);
    }

    private boolean dataIsInvalid() {
        if (textFields[0].getText().isBlank()) {
            errorLabel.setText("House must have an address");
            return true;
        }

        if (utils.dataIsInvalid(textFields)) {
            errorLabel.setText("Invalid characters");
            return true;
        }
        errorLabel.setText("");
        return false;
    }

    protected House createHouse() {
        return dataIsInvalid() ? null : new House(addressTF.getText(), postcodeTF.getText(),
                bedroomsTF.getText(), Integer.toString(utils.houseToInt(dwellingType.getSelectionModel().getSelectedItem().toString())),
                rentTF.getText());
    }
}