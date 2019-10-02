package Controller;

import DataTransfer.Data;
import Display.Display;
import Enums.Enum;
import Forms.Form;
import ImageProcessing.ImageLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.*;

public class Controller {

    Display display;
    ImageLoader imgEditor;
    Data data;
    String startDate = "";
    String endDate = "";

    public Controller(Display display, Data data) {
        this.display = display;
        this.data = data;
        imgEditor = new ImageLoader();
        display.populateDropDowns(data.getOwners(), data.getTenants(), data.getHouses());

        display.getCreateFormButton().setOnMouseClicked(e -> createForm());
        display.newOwnerButton().setOnMouseClicked(e -> newOwnerButtonClicked());
        display.newTenantButton().setOnMouseClicked(e -> newTenantButtonClicked());
        display.newHouseButton().setOnMouseClicked(e -> newHouseButtonClicked());
        display.viewOwnersButton().setOnMouseClicked(e -> viewOwnersButtonClicked());
        display.viewTenantsButton().setOnMouseClicked(e -> viewTenantsButtonClicked());
        display.viewHousesButton().setOnMouseClicked(e -> viewHousesButtonClicked());

        display.createGridPaneListner();
        //createForm();
    }

    private ArrayList<Map<String,String>> mergeDictionaries(HashMap<String,String> ownerData, HashMap<String,String> tenantData,
                                                      HashMap<String,String> houseData, HashMap<String,String> tenant2Data) {
        ArrayList<Map<String,String>> list = new ArrayList<>();
        if (ownerData != null) {
            list.add(ownerData);
        }
        if (tenantData != null) {
            list.add(tenantData);
        }
        if (houseData != null) {
            list.add(houseData);
        }
        if (tenant2Data != null) {
            list.add(tenant2Data);
        }
        return list;
    }

    private void createForm() {
        data = new Data(Enum.dataPath);
        Image image = imgEditor.loadImage(display.getPathForSelectedForm());
        Form form = display.getSelectedForm();

        if (DropdownsAreEmpty()) {
            createAlert();
            return;
        }

        ArrayList<Map<String,String>> theMa = mergeDictionaries(data.getOwners().get(display.getSelectedOwner()).attributes(),
                data.getTenants().get(display.getTenant1Selection()).attributes(),
                data.getHouses().get(display.getSelectedHouse()).attributes(),
                display.getTenant2Selection().equals("None") ? null : data.getTenants().get(display.getTenant2Selection()).attributes());

        Canvas canvas = imgEditor.addInformation(image, form.xyDataSet(), theMa, form);
        imgEditor.previewImage(canvas);
    }

    private String askUserForInformation() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setMinWidth(300);

        GridPane pane = new GridPane();
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setMinSize(400, 200);
        pane.setAlignment(Pos.CENTER);
        Label title = new Label("You are missing some important information!");
        title.setAlignment(Pos.CENTER);
        title.setPadding(new Insets(0, 20, 20, 20));
        GridPane.setColumnSpan(title, 2);
        pane.add(title, 0, 0);

        TextField startLeaseTF = new TextField();
        TextField endLeaseTF = new TextField();

        pane.add(new Label("Start Lease Date"), 0, 1);
        pane.add(new Label("End Lease Date"), 0, 2);
        pane.add(startLeaseTF, 1, 1);
        pane.add(endLeaseTF, 1, 2);

        Button ok = new Button("Ok");
        ok.setMinWidth(100);
        ok.setOnAction(e -> {
            startDate = startLeaseTF.getText();
            endDate = endLeaseTF.getText();
            stage.close();
        });
        pane.add(ok, 0, 3);

        Button skip = new Button("Skip");
        skip.setMinWidth(100);
        skip.setOnAction(e -> stage.close());
        pane.add(skip, 1, 3);

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.showAndWait();
        return "";
    }

    private void criticalInformationIsIncomplete(Form form, ArrayList<Map<String,String>> data) {
        ArrayList<String> list = new ArrayList<>();
        list.addAll(form.getCriticalInformation());
        for (String s : form.getCriticalInformation()) {
            for (Map<String,String> map : data) {
                for(Map.Entry<String,String> e : map.entrySet()) {
                    if (e.getKey().equals(s)) {
                        list.remove(s);
                    }
                }
            }
        }

    }

    private boolean DropdownsAreEmpty() {
        return data.getTenants().values().isEmpty() || data.getOwners().values().isEmpty() ||
                data.getHouses().values().isEmpty();
    }

    private void createAlert() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setMinWidth(300);

        Label label = new Label("Error creating form. All dropBox's must have an object Selected");
        label.setPadding(new Insets(20, 20, 20, 20));
        label.setFont(new Font("Arial", 16));
        Button ok = new Button("Ok");
        ok.setStyle("-fx-background-color: #b0e0e6; -fx-background-radius: 10;");
        ok.setMinWidth(80);
        ok.setOnAction(e -> stage.close());

        HBox hBox = new HBox(ok);
        hBox.setPadding(new Insets(0, 20, 20, 20));
        hBox.setSpacing(100);
        hBox.setAlignment(Pos.CENTER_LEFT);
        VBox vBox = new VBox(label, hBox);
        vBox.setMinHeight(80);

        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.showAndWait();
    }


    private void newHouseButtonClicked() {
        display.createNewHouseScene();
    }

    private void newTenantButtonClicked() {
        display.createNewTenantScene();
    }

    private void newOwnerButtonClicked() {
        display.createNewOwnerScene();
    }

    private void viewHousesButtonClicked() {
        display.createListHouseScene();
    }

    private void viewTenantsButtonClicked() {
        display.createListTenantsScene();
    }

    private void viewOwnersButtonClicked() {
        display.createListOwnersScene();
    }




}
