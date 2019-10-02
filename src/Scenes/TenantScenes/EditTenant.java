package Scenes.TenantScenes;

import DataTransfer.Data;
import DataTransfer.DataSaver;
import Display.Display;
import Enums.Enum;
import Objects.Tenant;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EditTenant extends Layer2TenantScene {

    private ViewTenantsInDatabase parent;
    private Data data;
    private Scene scene;
    private String originalName, originalBondNumber;

    public EditTenant(Stage stage, Display display, ViewTenantsInDatabase parent, int width, int height, Data data) {
        super(stage, display, parent.getScene(), width, height);
        this.parent = parent;
        this.data = data;
        this.scene = new Scene(frame);
        customizeConfirmButton();
        monitorNewBond();
    }

    private void monitorNewBond() {
        newBond.selectedProperty().addListener((obs, v1, v2) -> {
            if (v2) {
                rentalBondNumberTF.setText("");
                rentalBondNumberTF.setDisable(true);
            } else {
                rentalBondNumberTF.setDisable(false);
                rentalBondNumberTF.setText(originalBondNumber);
            }
        });
    }

    private void customizeConfirmButton() {
        confirmButton.setText("Confirm");
        confirmButton.setOnAction(e -> {
            Tenant tenant = createTenant();
            if (tenant != null) {
                data.getTenants().remove(originalName);
                data.getTenants().put(textFields[0].getText().trim() + " " + textFields[1].getText().trim(), tenant);
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

    public Scene getScene(Tenant tenant) {
        loadData(tenant);
        return scene;
    }

    private void loadData(Tenant tenant) {
        originalName = tenant.attributes().get("tenantName") + " " + tenant.attributes().get("tenantLastName");
        System.out.println("ORIGIONAL NAME " + originalName);
        originalBondNumber = tenant.attributes().get("rentalBondNumber");
        for (int i=0; i<textFields.length; i++) {
            textFields[i].setText(tenant.attributes().get(officialAttributes[i]));
        }
        paymentMethod.getSelectionModel().select(tenant.attributes().get("tenantPaymentMethod").equals("0") ? "Cheque/Money Order" : "BPAY");
        receiveEmails.setSelected(tenant.attributes().get("tenantReceiveEmails").equals("1"));
        receivesSubsidy.setSelected(tenant.attributes().get("tenantReceivesSubsidy").equals("1"));
        newBond.setSelected(tenant.attributes().get("newBond").equals("1"));
    }
}
