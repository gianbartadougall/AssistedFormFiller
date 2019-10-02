package Scenes.TenantScenes;

import DataTransfer.Data;
import DataTransfer.DataSaver;
import Display.Display;
import Enums.Enum;
import Objects.Tenant;
import Scenes.SceneUtilities;
import Scenes.SceneUtils;
import Scenes.Screen;
import Utilities.Utils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Map;

public class ViewTenantsInDatabase extends Screen {

    private SceneUtils su = new SceneUtils();
    private Scene scene;
    private EditTenant editTenant;
    private GridPane listOfTenants;
    private Button backButton;
    private SceneUtilities utils;
    private Utils general;

    public ViewTenantsInDatabase(Data data, Stage stage, Scene scene, int width, int height, Display display) {
        super(data, stage, scene, width, height, display);
    }

    @Override
    protected void createScene() {
        su =new SceneUtils();
        utils = new SceneUtilities();
        general = new Utils();
        listOfTenants = new GridPane();
        scene = new Scene(createScrollPane());
        setUpEditScene();
        configureBorderPane();
        configureVbox();
        createLabels();
        configureMenuBar();
    }

    private void setUpEditScene() {
        editTenant = new EditTenant(stage, display,this, width, height, data);
    }

    private ScrollPane createScrollPane() {
        ScrollPane scrollPane = new ScrollPane(frame);
        scrollPane.setMaxSize(width, height);
        scrollPane.setMinSize(width, height);
        return scrollPane;
    }

    private void configureBorderPane() {
        frame.setTop(menuBar);
        frame.setCenter(listOfTenants);
    }

    private void configureMenuBar() {
        backButton = new Button("Back");
        su.configureMenuBar(stage, scene, backButton, menuBar, "Tenants in data base");
        backButton.setOnMouseClicked(e -> {
            display.reloadOptionsBar();
            stage.setScene(parentScene);
        });
    }

    private void createLabels() {
        ArrayList<GridPane> sortedList = new ArrayList<>();

        for (Map.Entry<String, Tenant> tenant : data.getTenants().entrySet()) {

            GridPane ownerInformation = createGridPane();
            Button edit = utils.configureButton("Edit", "#dcdcdc", 2, 300, 25);
            edit.setOnMouseClicked(e -> stage.setScene(editTenant.getScene(tenant.getValue())));

            ownerInformation.add(utils.newLabel(tenant.getKey(), tenant.getKey(), 25), 0, 0);
            ownerInformation.add(edit, 0, 1);
            ownerInformation.add(utils.newLabel("", "", 25), 0, 2);

            Button delete = utils.configureButton("Delete", "#f08080", 2, 300, 25);

            Stage stage = new Stage();
            Button deleteButton = general.createButton("Delete", 80, 25);
            deleteButton.setOnAction(e -> {
                deleteTenant(tenant.getKey());
                stage.close();
            });

            delete.setOnAction(e -> utils.confirmObjectDelete(stage, deleteButton));

            for (Map.Entry<String,String> attributes : tenant.getValue().attributes().entrySet()) {
                int index = ownerInformation.getChildren().size();
                ownerInformation.add(new Label(attributes.getKey()), 0, index);
                String title = checkForIncompleteValues(attributes.getKey(), attributes.getValue());
                ownerInformation.add(new Label(title), 1, index);
            }

            ownerInformation.add(delete, 0, ownerInformation.getChildren().size());

            sortedList.add(utils.getIndexInAlphabeticalOrder(sortedList, ownerInformation), ownerInformation);
        }
        utils.addSortedListToGridpane(listOfTenants, sortedList);
    }

    private String checkForIncompleteValues(String key, String val) {
        switch (key) {
            case "tenantReceiveEmails": return val.equals("0") ? "NO" : "YES";
            case "tenantReceivesSubsidy": return val.equals("0") ? "NO" : "YES";
            case "tenantPaymentMethod": return val.equals("0") ? "Cheque/Money Order" : "BPAY";
            case "newBond": return val.equals("0") ? "NO" : "YES";
            default: return val;
        }
    }

    private void deleteTenant(String id) {
        data.getTenants().remove(id);
        saveDataToDatabase();
        listOfTenants.getChildren().clear();
        createLabels();
    }

    private void configureVbox() {
        listOfTenants.setMinSize(width, height);
    }

    private GridPane createGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(20,20, 20, 20));
        gridPane.setHgap(20);
        return gridPane;
    }

    public Scene getScene() {
        listOfTenants.getChildren().clear();
        createLabels();
        return scene;
    }

    public void update() {
        listOfTenants.getChildren().clear();
        createLabels();
    }

    @Override
    protected Boolean dataIsInvalid() {
        return null;
    }
}