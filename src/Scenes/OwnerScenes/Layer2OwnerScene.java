package Scenes.OwnerScenes;

import Display.Display;
import Enums.Enum;
import Objects.Owner;
import Scenes.BaseLayer;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public abstract class Layer2OwnerScene extends BaseLayer {

    private TextField nameTF, lastNameTF, addressTF, phoneTF, mobileTF, emailTF,
            rtaIDTF, ABN, postCode, postalAddress, accountNameTF, bsbTF, accountNumberTF;
    protected TextField[] textFields;
    protected CheckBox receiveEmails;
    protected String[] attributes, officialAttributes;
    protected Button confirmButton;

    public Layer2OwnerScene(Stage stage, Display display, Scene parentScene, int width, int height) {
        super(stage, display, parentScene, width, height);
        setUpScene();
    }

    private void setUpScene() {
        attributes = Enum.OWNER_lABEL_ATTRIBUTES;
        officialAttributes = Enum.OWNER_OFFICIAL_ATTRIBUTES;

        this.confirmButton = createConfirmButton(300, 2);
        setUpTextFields();
        setUpLabels();
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
        receiveEmails = new CheckBox();
        receiveEmails.setSelected(false);
        information.add(receiveEmails, 1, (information.getChildren().size()/2));
        information.add(confirmButton, 0, information.getChildren().size()/2);
    }

    private void setUpTextFields() {
        textFields = new TextField[]{nameTF = new TextField(), lastNameTF = new TextField(), addressTF = new TextField(),
                phoneTF = new TextField(), mobileTF = new TextField(), emailTF = new TextField(),
                rtaIDTF = new TextField(), ABN = new TextField(), postCode = new TextField(),
                postalAddress = new TextField(), accountNameTF = new TextField(), bsbTF = new TextField(),
                accountNumberTF = new TextField()};
    }

    private boolean dataIsInvalid() {
        if (nameTF.getText().isBlank() || lastNameTF.getText().isBlank()) {
            errorLabel.setText("Owner must have a first and last name");
            return true;
        }

        if (utils.dataIsInvalid(textFields)) {
            errorLabel.setText("Invalid characters");
            return true;
        }
        errorLabel.setText("");
        return false;
    }

    protected Owner createOwner() {
        return dataIsInvalid() ? null : new Owner(nameTF.getText(), lastNameTF.getText(), addressTF.getText(), phoneTF.getText(),
                mobileTF.getText(), emailTF.getText(), rtaIDTF.getText(), receiveEmails.isSelected() ? "1" : "0",
                ABN.getText(), postCode.getText(), postalAddress.getText(), accountNameTF.getText(), bsbTF.getText(),
                accountNumberTF.getText());
    }
}
