package Scenes.TenantScenes;

import Display.Display;
import Enums.Enum;
import Objects.Tenant;
import Scenes.BaseLayer;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public abstract class Layer2TenantScene extends BaseLayer {


    private TextField nameTF, lastNameTF, addressTF, phoneTF, mobileTF, emailTF,
            rtaIDTF, dateOfBirthTF, bondPaymentTF, startDateTF, endDateTF,
            accountNameTF, bsbTF, accountNumberTF;
    protected TextField rentalBondNumberTF;
    protected TextField[] textFields, textFields1;
    protected CheckBox receiveEmails, receivesSubsidy, newBond;
    private CheckBox[] checkBoxes;
    protected ComboBox paymentMethod;
    protected String[] attributes, officialAttributes;
    protected Button confirmButton;


    public Layer2TenantScene(Stage stage, Display display, Scene parentScene, int width, int height) {
        super(stage, display, parentScene, width, height);
        setUpScene();

    }

    private void setUpScene() {
        attributes = Enum.TENANT_LABEL_ATTRIBUTES;
        officialAttributes = Enum.TENANT_OFFICIAL_ATTRIBUTES;

        this.confirmButton = createConfirmButton(300, 4);
        setUpComboBox();
        setUpTextFields();
        setUpCheckBoxs();
        setUpLabels();
    }

    private void setUpComboBox() {
        paymentMethod = createComboBox(200, Enum.VALID_PAYMENT_METHODS);
    }

    private void setUpCheckBoxs() {
        checkBoxes = new CheckBox[]{receiveEmails = new CheckBox(), receivesSubsidy = new CheckBox(), newBond = new CheckBox()};
    }

    private void setUpTextFields() {
        textFields = new TextField[]{nameTF = new TextField(), lastNameTF = new TextField(), addressTF = new TextField(),
                phoneTF = new TextField(), mobileTF = new TextField(), emailTF = new TextField(),
                rtaIDTF = new TextField(), dateOfBirthTF = new TextField(), bondPaymentTF = new TextField(),
                startDateTF = new TextField(), endDateTF = new TextField(), accountNameTF = new TextField(),
                bsbTF = new TextField(), accountNumberTF = new TextField()};
        textFields1 = new TextField[] {rentalBondNumberTF = new TextField()};
    }

    private void setUpLabels() {
        int j= attributes.length/2;
        for (int i = 0; i < attributes.length; i++) {
            Label label = new Label(attributes[i]);
            information.add(label, i<j ? 0 : 2, i<j ? i : i-j);
        }
        addNodesToInformation();
        errorLabel = createErrorLabel(4);
        information.add(errorLabel, 0, (information.getChildren().size()/4)+2);
    }

    private void addNodesToInformation() {
        int j= attributes.length/2;
        int i = 0;
        for (TextField textField : textFields) {
            information.add(textField, i<j ? 1 : 3, i<j ? i : i-j); i++;
        }
        information.add(paymentMethod, i<j ? 1 : 3, i<j ? i : i-j); i++;
        for (CheckBox checkBox : checkBoxes) {
            information.add(checkBox, i<j ? 1 : 3, i<j ? i : i-j); i++;
        }
        for (TextField textField : textFields1) {
            information.add(textField, i<j ? 1 : 3, i<j ? i : i-j); i++;
        }
        information.add(confirmButton, 0, (information.getChildren().size()/4)+1);
    }

    private boolean dataIsInvalid() {
        if (nameTF.getText().isBlank() || lastNameTF.getText().isBlank()) {
            errorLabel.setText("Tenant must have a first and last name");
            return true;
        }

        if (utils.dataIsInvalid(textFields)) {
            errorLabel.setText("Invalid characters");
            return true;
        }
        errorLabel.setText("");
        return false;
    }

    protected Tenant createTenant() {
        return dataIsInvalid() ? null : new Tenant(nameTF.getText(), lastNameTF.getText(), addressTF.getText(), phoneTF.getText(),
                mobileTF.getText(), emailTF.getText(), rtaIDTF.getText(), receiveEmails.isSelected() ? "1" : "0",
                dateOfBirthTF.getText(), bondPaymentTF.getText(), rentalBondNumberTF.getText(), receivesSubsidy.isSelected() ? "1" : "0",
                paymentMethod.getSelectionModel().getSelectedItem().toString().equals("BPAY") ? "1" : "0",
                newBond.isSelected() ? "1" : "0", startDateTF.getText(), endDateTF.getText(), accountNameTF.getText(), bsbTF.getText(),
                accountNumberTF.getText());
    }
}
